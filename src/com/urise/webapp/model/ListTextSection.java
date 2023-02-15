package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection{
    private List<String> textSections = new ArrayList<>();

    public ListTextSection(List<String> textSections) {
        this.textSections = textSections;
    }

    public List<String> getTextSections() {
        return textSections;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        Collection<String> collection = textSections;
        for (String str : collection) {
            text.append("\n");
            text.append(str);
        }
        return text.toString();
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
