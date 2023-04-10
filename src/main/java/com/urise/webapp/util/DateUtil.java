package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(9999, LocalDate.MAX.getMonth(), LocalDate.MAX.getDayOfMonth() );
    public static final LocalDate MIN = LocalDate.of(1800, 1, 1 ) ;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
    public static String outputDate(LocalDate date) {
        if (date == null || date == MIN ) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static LocalDate inputDate(String date) {
        if ( UtilsResume.isEmpty( date )  ){
            return MIN;
        } else if ("Сейчас".equals(date) || "СЕЙЧАС".equals(date)) {
            return NOW;
        }else {
            YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
            return  LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        }
    }

}
