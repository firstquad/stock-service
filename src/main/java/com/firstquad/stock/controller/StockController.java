package com.firstquad.stock.controller;

import com.firstquad.stock.controller.dto.StockDto;
import com.firstquad.stock.controller.mapper.StockMapper;
import com.firstquad.stock.controller.ui.StockPrinter;
import com.firstquad.stock.domain.StockCalculation;
import com.firstquad.stock.service.StockCalcService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping(path = "/stocks")
@AllArgsConstructor
public class StockController {
    private StockCalcService calcService;
    private StockMapper stockMapper;

    @GetMapping
    public ResponseEntity<List<StockCalculation>> calc() {
        return ResponseEntity.ok(calcService.calculate());
    }

    @GetMapping("/view")
    public ResponseEntity<String> calcView() {
        List<StockCalculation> calculated = calcService.calculate()
                .stream().filter(c -> c.getTotalPrice() != 0).toList();

        List<StockDto> stockDtos = stockMapper.toDto(calculated);
        return ResponseEntity.ok(StockPrinter.print(stockDtos));
    }

    @GetMapping("/sample")
    public String showForm() {
        return "sample";
    }
}
