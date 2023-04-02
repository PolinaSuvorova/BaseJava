package com.urise.webapp.model;

public enum ContactType {
    SKYPE("Skype"),
    EMAIL("E-mail"),
    PHONE("Тел."),
    LINKEDIN("linked In"),
    GITHUB("GitHub");
    private final String title;
    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
