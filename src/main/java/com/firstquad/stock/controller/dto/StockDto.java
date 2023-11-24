package com.firstquad.stock.controller.dto;

import lombok.Data;

@Data
public class StockDto {
    private String name;
    private Long currTotalPrice;
    private Float percent;

    @Override
    public String toString() {
        return "<tbody>" +
                "<td>" + name + "</td> <td>"
                + currTotalPrice + "</td> <td>"
                + (percent >= 0 ? percent : "<span style=\"color:red\">" + percent + "</span>")
                + "</td>" +
                "</tbody>";
    }
}
