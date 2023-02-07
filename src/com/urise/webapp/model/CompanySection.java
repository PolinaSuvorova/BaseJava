package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompanySection extends AbstractSection {
    protected List<Company> list = new ArrayList<>();

    public CompanySection( ) {
        super();
    }

    @Override
    public void addElementSection(Object element) {
    Company line = (Company) element;
        list.add(line);
    }

    @Override
    public String toString() {
        String text = "";
        Collection<Company> collection = list;
        for (Company company : collection) {
            text = text + "\n" + company;
        }
        return text;
    }
}
