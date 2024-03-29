package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    private List<String> textSections;

    public ListTextSection() {
    }

    public ListTextSection(String... items) {
        this.textSections = Arrays.asList(items);
    }

    public ListTextSection(List<String> textSections) {
        Objects.requireNonNull(textSections, "items must not be null");
        this.textSections = textSections;
    }

    public List<String> getTextSections() {
        return textSections;
    }

    @Override
    public String toString() {
        return textSections.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTextSection that = (ListTextSection) o;

        return Objects.equals(textSections, that.textSections);
    }

    @Override
    public int hashCode() {
        return textSections != null ? textSections.hashCode() : 0;
    }
}
