package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    private String description;

    public TextSection() {
    }

    public TextSection(String description) {
        Objects.requireNonNull(description, " TextSection.description must not be null");
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(description, that.description);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    @Override
    public String toString() {
        return description;
    }
}
