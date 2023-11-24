package com.firstquad.stock.controller.ui;

import com.firstquad.stock.controller.dto.StockDto;

import java.util.List;

public class StockPrinter {
    public static String print(List<StockDto> stockDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>" +
                "<thead>\n" +
                "    <tr>\n" +
                "      <th>Name</th>\n" +
                "      <th>Total</th>\n" +
                "      <th>Percent</th>\n" +
                "    </tr>\n" +
                "  </thead>");
        for (StockDto s: stockDto) {
            sb.append(s.toString());
        }
        sb.append("</table>");
        return sb.toString();
    }
}
