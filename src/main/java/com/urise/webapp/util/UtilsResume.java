package com.urise.webapp.util;

import com.urise.webapp.model.Period;

public class UtilsResume {
    static public boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String formatDates(Period position) {
        return DateUtil.outputDate(position.getStartDate()) + " - " +
                DateUtil.outputDate(position.getEndDate());
    }

}
