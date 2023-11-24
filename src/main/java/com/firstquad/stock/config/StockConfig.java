package com.firstquad.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.tinkoff.piapi.core.InvestApi;

@EnableWebMvc
@Configuration
public class StockConfig {

    @Bean
    public InvestApi investApi(StockProperties prop) {
        return InvestApi.create(prop.getToken());
    }
}
