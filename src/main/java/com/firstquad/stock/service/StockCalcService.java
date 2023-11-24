package com.firstquad.stock.service;

import com.firstquad.stock.domain.StockCalculation;
import com.firstquad.stock.domain.StockOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.firstquad.stock.util.StockUtils.getDateTime;
import static com.firstquad.stock.util.StockUtils.toInstant;

@Service
@AllArgsConstructor
public class StockCalcService {
    private InvestApi api;

    public List<StockCalculation> calculate() {
        String accountId = "2000571428";
//        String stockFigi = "BBG006L8G4H1";
        Instant from = toInstant("04:30 PM, Sat 1/14/2023");
        Instant to = toInstant("04:30 PM, Sat 1/21/2023");

        List<Position> positions = api.getOperationsService()
                .getPortfolioSync(accountId).getPositions();

        List<Share> allSharesSync = api.getInstrumentsService().getAllSharesSync();
        Map<String, String> names = allSharesSync
                .stream()
                .filter(s -> positions
                        .stream()
                        .anyMatch(p -> p.getFigi().equals(s.getFigi())))
                .collect(Collectors.toMap(Share::getFigi, Share::getTicker));

        Map<String, BigDecimal> currPrices =
                positions.stream()
//                        .filter(c -> c.)
                        .collect(Collectors.toMap(k -> k.getFigi(),
                                v -> v.getCurrentPrice().getValue()));

        List<StockCalculation> calcs = new ArrayList<>();
        List<Operation> allOperationsSync = api.getOperationsService()
                .getAllOperationsSync(accountId, from, Instant.now());

        for (Map.Entry<String, String> entry: names.entrySet()) {
            List<Operation> yndOps = getOperations(allOperationsSync, accountId, entry.getKey(), from);
            calcs.add(calculate(currPrices, yndOps, entry.getValue()));
        }
        return calcs;
    }

    public StockCalculation calculate(Map<String, BigDecimal> currPrices, List<Operation> yndOps, String name) {
        final long[] diff = {0};
        final long[] totalPrice = {0};
        final long[] currTotalPrice = {0};

        yndOps.stream()
                .forEach(op -> {
                    long units = op.getPrice().getUnits();
                    BigDecimal currPrice = currPrices.get(op.getFigi());
                    long curDiff = op.getQuantity() * (currPrice.longValue() - units);
                    diff[0] = diff[0] + curDiff;
                    long amount = op.getQuantity() * units;
                    totalPrice[0] = totalPrice[0] + amount;
                    currTotalPrice[0] = currTotalPrice[0] + op.getQuantity() * currPrice.longValue();
                    System.out.println("amount:" + amount + "  currDiff: " + curDiff);
                });

        float percent = (float) (((float) diff[0] / (float) totalPrice[0]) * 100.0);
        float currPercent = (float) (((float) diff[0] / (float) currTotalPrice[0]) * 100.0);
        float simplePercent = (float) ((((float) currTotalPrice[0] - (float) totalPrice[0]) / (float) currTotalPrice[0]) * 100.0);

        return StockCalculation.builder()
                .name(name)
                .totalDiff(diff[0])
                .totalPrice(totalPrice[0])
                .currTotalPrice(currTotalPrice[0])
                .percent(percent)
                .currPercent(currPercent)
                .simplePercent(simplePercent).build();
    }

    public List<StockOperation> getOperations(Map<String, String> names, List<Operation> yndOps) {
        List<StockOperation> ops = new ArrayList<>();
        yndOps
                .stream().filter(s -> s.getPrice().getUnits() != 0)
                .forEach(op -> {
            StockOperation operation = StockOperation.builder()
                    .name(names.get(op.getFigi()))
                    .currency(op.getCurrency())
                    .date(getDateTime(op.getDate()))
                    .type(op.getOperationType())
                    .price(op.getPrice().getUnits())
                    .quantity(op.getQuantity())
                    .build();
            ops.add(operation);
        });
        return ops;
    }

    private List<Operation> getOperations(List<Operation> allOperationsSync, String accountId, String stockFigi, Instant from) {
        return allOperationsSync.stream()
                .filter(op -> op.getFigi().equals(stockFigi))
                .filter(op -> op.getQuantity() != 0)
                .sorted((op1, op2) -> (int) (op1.getDate().getSeconds() - op2.getDate().getSeconds()))
                .toList();
    }
}
