package com.urise.webapp.model;

public enum TypeContact {
    SKYPE("Skype"),
    EMAIL("E-mail"),
    PHONE("Тел."),
    LINKEDIN("linked In"),
    GITHUB("GitHub");
    private String title;

    TypeContact(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
