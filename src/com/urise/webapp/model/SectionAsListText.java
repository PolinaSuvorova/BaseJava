package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SectionAsListText extends AbstractSection{
    protected List<String> list = new ArrayList<>();

    public SectionAsListText(String nameSection) {
        super(nameSection);
    }

    @Override
    public void addElementSection(Object element) {
        String description = (String) element;
        list.add(description);
    }

    @Override
    public void doPrintSection() {
        Collection<String> collection = list;
        for (String description : collection) {
            System.out.println( "* " + description);
        }

    }
}
