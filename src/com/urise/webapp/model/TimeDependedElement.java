package com.urise.webapp.model;

public class TimeDependedElement {
    protected final String startDate;
    protected final String nameCompany;
    protected String endDate;
    protected String position;
    protected String description;

    public TimeDependedElement(String startDate, String endDate, String nameCompany,
                               String position, String description) {
        this.startDate = startDate;
        this.nameCompany = nameCompany;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }
    public TimeDependedElement(String startDate, String endDate, String nameCompany,
                               String description) {
        this.startDate = startDate;
        this.nameCompany = nameCompany;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }
    @Override
    public String toString() {
        return "*** " + startDate + "-" + endDate + " *** " +
                "    nameCompany='" + nameCompany + '\'' +
                "    position='" + position + '\'' +
                "    description='" + description + '\'';
    }
}
