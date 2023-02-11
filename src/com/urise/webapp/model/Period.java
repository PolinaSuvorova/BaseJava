package com.urise.webapp.model;

import java.util.Objects;

public class Period {
    private final String startDate;
    private final String endDate;
    private final String title;
    private final String description;

    public Period(String startDate, String endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!Objects.equals(startDate, period.startDate))
            return false;
        return Objects.equals(title, period.title);
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    public String toString() {
     return "*** " + startDate + "-" + endDate + " *** " +
             title + '\'' +
            "description='" + description + '\'';
    }
}
