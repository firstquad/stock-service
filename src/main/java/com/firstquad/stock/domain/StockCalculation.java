package com.firstquad.stock.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockCalculation {
    private String name;
    private Long totalDiff;
    private Long totalPrice;
    private Long currTotalPrice;
    private Float percent;
    private Float currPercent;
    private Float simplePercent;
}
