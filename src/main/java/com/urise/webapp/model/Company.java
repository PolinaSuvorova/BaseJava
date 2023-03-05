package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    private String name;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Company() {
    }

    public Company(List<Period> periods, String name, String website) {
        Objects.requireNonNull(name, " name must not be null");
        Objects.requireNonNull(website, " website must not be null");
        this.periods = periods;
        this.name = name;
        this.website = website;
    }

    public Company(String website, String name) {
        Objects.requireNonNull(name, " name must not be null");
        Objects.requireNonNull(website, " url must not be null");
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
