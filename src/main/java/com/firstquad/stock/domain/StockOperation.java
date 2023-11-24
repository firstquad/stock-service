package com.firstquad.stock.domain;

import lombok.Builder;
import lombok.Data;
import ru.tinkoff.piapi.contract.v1.OperationType;

import java.time.LocalDateTime;

@Data
@Builder
public class StockOperation {
    private String name;
    private String currency;
    private LocalDateTime date;
    private OperationType type;
    private Long price;
    private Long quantity;
}
