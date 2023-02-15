package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Company {

    private final String name;
    private final String website;
    private List<Period> periods = new ArrayList<>();

    public Company(List<Period> periods, String name, String website) {
        this.periods = periods;
        this.name = name;
        this.website = website;
    }
    public Company(String website, String name) {
          this.name = name;
          this.website = website;

    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(name);
        if (website != null) {
            text.append("(");
            text.append(website);
            text.append(") \n");
        }
        Collection<Period> collection = periods;
        for (Period period : collection) {
            text.append("\n");
            text.append(period);
         }
        return text.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }
}