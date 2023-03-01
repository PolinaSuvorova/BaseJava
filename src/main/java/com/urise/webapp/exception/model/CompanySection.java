package com.urise.webapp.exception.model;

import java.util.Collection;
import java.util.List;

public class CompanySection extends AbstractSection {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Collection<Company> collection = companies;
        for (Company company : collection) {
            str.append("\n");
            str.append(company);
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies != null ? companies.equals(that.companies) : that.companies == null;
    }

    @Override
    public int hashCode() {
        return companies != null ? companies.hashCode() : 0;
    }
}
