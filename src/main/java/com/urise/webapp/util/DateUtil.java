package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.mm.yyyy");
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
    public static String outputDate(LocalDate date) {
        if (LocalDate.MIN == date) {
            return "";
        }
        return date.equals(LocalDate.MAX) ? "Сейчас" : date.format(FORMAT);
    }

    public static LocalDate inputDate(String date) {
        if ( !UtilsResume.isEmpty( date ) ){
            return LocalDate.MIN;
        } else if ("Сейчас".equals(date)) {
            return LocalDate.MAX;
        }else {
            return  LocalDate.parse(date, FORMAT);
        }
    }
}
