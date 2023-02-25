package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    private final String text;

    public Contact(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return Objects.equals(text, contact.text);
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
