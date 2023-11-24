package com.firstquad.stock.service;

import com.firstquad.stock.domain.StockCalculation;
import com.firstquad.stock.domain.StockOperation;
import com.google.protobuf.Timestamp;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
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

import static com.firstquad.stock.util.StockUtils.getDateTime;

@Service
@AllArgsConstructor
public class StockPrinterService {
    private InvestApi api;

    public void printPercents(StockCalculation stockCalc) {
        System.out.println();
        System.out.println("total diff: " + stockCalc.getTotalDiff());
        System.out.println("totalPrice: " + stockCalc.getTotalPrice());
        System.out.println("currTotalPrice: " + stockCalc.getCurrPercent());
        System.out.println();
        System.out.println("Percent: " + stockCalc.getPercent() + "%");
        System.out.println("currPercent: " + stockCalc.getCurrPercent() + "%");
        System.out.println("simplePercent: " + stockCalc.getSimplePercent() + "%");
    }

    public void printAllOperations(List<StockOperation> ops) {
        ops.forEach(op -> {
            System.out.println("-------------------------");
            System.out.println("Name: " + op.getName());
            System.out.println("Currency: " + op.getCurrency());
            System.out.println("Date: " + op.getDate());
            System.out.println("Type: " + op.getType());
            System.out.println("Price: " + op.getPrice());
            System.out.println("Quantity: " + op.getQuantity());
            System.out.println("-------------------------");
            System.out.println();
        });
    }

}
