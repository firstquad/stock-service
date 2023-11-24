package com.firstquad.stock;

import com.google.protobuf.Timestamp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Operation;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
//
//        var api = InvestApi.create(token);
//        List<Currency> allCurrenciesSync = api.getInstrumentsService().getAllCurrenciesSync();
//
//        String accountId = "2000571428";
//        String stockFigi = "BBG006L8G4H1";
//
//        List<Share> allSharesSync = api.getInstrumentsService().getAllSharesSync();
//        List<Position> positions = api.getOperationsService()
//                .getPortfolioSync(accountId).getPositions();
//
//        Map<String, String> names = allSharesSync
//                .stream()
//                .filter(s -> positions
//                        .stream()
//                        .anyMatch(p -> p.getFigi().equals(s.getFigi())))
//                .collect(Collectors.toMap(Share::getFigi, Share::getName));
//
//        Map<String, BigDecimal> currPrices =
//                positions.stream()
//                        .collect(Collectors.toMap(k -> k.getFigi(),
//                                v -> v.getCurrentPrice().getValue()));
//
//        Instant from = LocalDateTime.parse("04:30 PM, Sat 1/14/2023",
//                        DateTimeFormatter.ofPattern("hh:mm a, EEE M/d/uuuu", Locale.US))
//                .atZone(ZoneId.of("Europe/Moscow")).toInstant();
//
//        List<Operation> allOperationsSync = api.getOperationsService()
//                .getAllOperationsSync(accountId, from, Instant.now());
//
//        Instant from2 = LocalDateTime.parse("04:30 PM, Sat 1/21/2023",
//                        DateTimeFormatter.ofPattern("hh:mm a, EEE M/d/uuuu", Locale.US))
//                .atZone(ZoneId.of("Europe/Moscow")).toInstant();
//
//        long units1 = api.getMarketDataService()
//                .getCandlesSync(stockFigi, from, from2, CandleInterval.CANDLE_INTERVAL_4_HOUR)
//                .get(0).getHigh().getUnits();
//        System.out.println("old price: " + units1);
//
//
//        List<Operation> yndOps = allOperationsSync.stream()
//                .filter(op -> op.getFigi().equals(stockFigi))
//                .filter(op -> op.getQuantity() != 0)
//                .sorted((op1, op2) -> (int) (op1.getDate().getSeconds() - op2.getDate().getSeconds()))
//                .toList();
//
//
//        yndOps.forEach(op -> {
//            System.out.println("-------------------------");
//            System.out.println("Name: " + names.get(op.getFigi()));
//            System.out.println("Currency: " + op.getCurrency());
//            System.out.println("Date: " + getDateTime(op.getDate()));
//            System.out.println("Type: " + op.getOperationType());
//            System.out.println("Price: " + op.getPrice().getUnits());
//            System.out.println("Quantity: " + op.getQuantity());
////            System.out.println("State: " + op.getState());
//            System.out.println("-------------------------");
//            System.out.println();
//        });
//
//        final long[] diff = {0};
//        final long[] totalPrice = {0};
//        final long[] currTotalPrice = {0};
//        int count = yndOps.size();
//        yndOps.stream()
//                .forEach(op -> {
//                    long units = op.getPrice().getUnits();
//                    BigDecimal currPrice = currPrices.get(op.getFigi());
//                    long curDiff = op.getQuantity() * (currPrice.longValue() - units);
//                    diff[0] = diff[0] + curDiff;
//                    long amount = op.getQuantity() * units;
//                    totalPrice[0] = totalPrice[0] + amount;
//                    currTotalPrice[0] = currTotalPrice[0] + op.getQuantity() * currPrice.longValue();
//                    System.out.println("amount:" + amount + "  currDiff: " + curDiff);
//                });
//        System.out.println();
//        System.out.println("total diff: " + diff[0]);
//        System.out.println("totalPrice: " + totalPrice[0]);
//        System.out.println("currTotalPrice: " + currTotalPrice[0]);
//        System.out.println();
//        System.out.println("Percent: " + (float) (((float) diff[0] / (float) totalPrice[0]) * 100.0) + "%");
//        System.out.println("currPercent: " + (float) (((float) diff[0] / (float) currTotalPrice[0]) * 100.0) + "%");
//        System.out.println("simplePercent: " + (float) ((((float) currTotalPrice[0] - (float) totalPrice[0]) / (float) currTotalPrice[0]) * 100.0) + "%");
//
////        names.forEach((k, v) -> System.out.println(v + " " + k));
//
//    }
//
//    private static LocalDateTime getDateTime(Timestamp date) {
//        return LocalDateTime.ofInstant(
//                Instant.ofEpochSecond(date.getSeconds(), date.getNanos()),
//                ZoneId.of("Europe/Moscow"));
//    }
//2000571428
//BBG006L8G4H1 yndx

}
