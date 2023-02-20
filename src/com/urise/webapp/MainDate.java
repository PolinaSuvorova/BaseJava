package com.urise.webapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(System.currentTimeMillis() - start);
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();

    }
}
