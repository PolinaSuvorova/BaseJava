package com.urise.webapp.model;

public class Period {
    private String startDate;
    private String endDate;
    private String title;
    private String description;

    public Period(String startDate, String endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }
    public String toString() {
     return "*** " + startDate + "-" + endDate + " *** " +
             title + '\'' +
            "description='" + description + '\'';
    }
}
