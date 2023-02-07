package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Company {

    private String name;
    private List<Period> periods = new ArrayList<>();

    public Company(Period period, String name, String website) {
        this.periods.add(period);
        this.name = name;
    }
    public Company(Period period, String name) {
        this.periods.add(period);
        this.name = name;
    }
    @Override
    public String toString() {
        String text = name;
        Collection<Period> collection = periods;
        for (Period period : collection) {
            text = text + "\n" + period;
        }
        return text;
    }
}
