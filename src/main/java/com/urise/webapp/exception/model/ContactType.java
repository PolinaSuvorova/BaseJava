package com.urise.webapp.exception.model;

public enum ContactType {
    SKYPE("Skype"),
    EMAIL("E-mail"),
    PHONE("Тел."),
    LINKEDIN("linked In"),
    GITHUB("GitHub");
    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
