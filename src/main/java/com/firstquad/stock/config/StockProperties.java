package com.firstquad.stock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("stock")
public class StockProperties {
    private String token;
}
