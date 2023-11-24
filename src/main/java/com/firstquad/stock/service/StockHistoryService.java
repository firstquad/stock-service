package com.firstquad.stock.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;

@Service
@AllArgsConstructor
public class StockHistoryService {
    private InvestApi api;

    public long getFirstHistoryPrice(String stockFigi, Instant from, Instant to) {
        return api.getMarketDataService()
                .getCandlesSync(stockFigi, from, to, CandleInterval.CANDLE_INTERVAL_4_HOUR)
                .get(0).getHigh().getUnits();
    }

}
