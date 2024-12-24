package com.sheff.kursovaya.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(formatter);
    }
}
