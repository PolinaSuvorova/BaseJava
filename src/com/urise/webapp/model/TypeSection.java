package com.urise.webapp.model;

public enum TypeSection {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPETIENCE("Опыт работы"),
    EDUCATION("Образование"),
    POSITION("Позиция");
    private String title;

    TypeSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
