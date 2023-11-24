package com.firstquad.stock.util;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StockUtils {
    public static LocalDateTime getDateTime(Timestamp date) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(date.getSeconds(), date.getNanos()),
                ZoneId.of("Europe/Moscow"));
    }


    public static Instant toInstant(String fromString) {
        Instant from = LocalDateTime.parse(fromString,
                        DateTimeFormatter.ofPattern("hh:mm a, EEE M/d/uuuu", Locale.US))
                .atZone(ZoneId.of("Europe/Moscow")).toInstant();
        return from;
    }
}
