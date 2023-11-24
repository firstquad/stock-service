package com.firstquad.stock.service;

import com.firstquad.stock.domain.StockCalculation;
import com.firstquad.stock.domain.StockOperation;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Position;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.firstquad.stock.util.StockUtils.toInstant;


@Service
@AllArgsConstructor
public class StockService {
    private InvestApi api;
    private StockCalcService calcService;
    private StockHistoryService historyService;
    private StockPrinterService printerService;

    @PostConstruct
    public void test2() {
        try {
            String s = new String("тест".getBytes("Windows-1251"), "Windows-1251");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostConstruct
    public void test() {

        String accountId = "2000571428";
        String stockFigi = "BBG006L8G4H1";
        Instant from = toInstant("04:30 PM, Sat 1/14/2023");
        Instant to = toInstant("04:30 PM, Sat 1/21/2023");

//        long historyPrice = historyService.getFirstHistoryPrice(stockFigi,
//                from,
//                to);

        List<Position> positions = api.getOperationsService()
                .getPortfolioSync(accountId).getPositions();

        List<Share> allSharesSync = api.getInstrumentsService().getAllSharesSync();
        Map<String, String> names = allSharesSync
                .stream()
                .filter(s -> positions
                        .stream()
                        .anyMatch(p -> p.getFigi().equals(s.getFigi())))
                .collect(Collectors.toMap(Share::getFigi, Share::getName));

        List<Operation> yndOps = getOperations(accountId, stockFigi, from);

        Map<String, BigDecimal> currPrices =
                positions.stream()
                        .collect(Collectors.toMap(k -> k.getFigi(),
                                v -> v.getCurrentPrice().getValue()));

        StockCalculation calculate = calcService.calculate(currPrices, yndOps, "yandex");
        List<StockOperation> operations = calcService.getOperations(names, yndOps);

        printerService.printPercents(calculate);
        printerService.printAllOperations(operations);
    }

    private List<Operation> getOperations(String accountId, String stockFigi, Instant from) {
        List<Operation> allOperationsSync = api.getOperationsService()
                .getAllOperationsSync(accountId, from, Instant.now());

        List<Operation> yndOps = allOperationsSync.stream()
                .filter(op -> op.getFigi().equals(stockFigi))
                .filter(op -> op.getQuantity() != 0)
                .sorted((op1, op2) -> (int) (op1.getDate().getSeconds() - op2.getDate().getSeconds()))
                .toList();
        return yndOps;
    }

}
