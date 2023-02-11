package com.urise.webapp.model;

import java.util.Collection;
import java.util.List;

public class CompanySection extends AbstractSection {
    private List<Company> list;

    public CompanySection( List<Company> list ) {
       this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Collection<Company> collection = list;
        for (Company company : collection) {
            str.append("\n");
            str.append(company);
        }
        return str.toString();
    }
}
