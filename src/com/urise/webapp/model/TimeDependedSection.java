package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TimeDependedSection extends AbstractSection {
    protected List<TimeDependedElement> list = new ArrayList<>();

    public TimeDependedSection(String nameSection) {
        super(nameSection);
    }

    @Override
    public void addElementSection(Object element) {
    TimeDependedElement line = (TimeDependedElement) element;
        list.add(line);
    }

    @Override
    protected void doPrintSection() {
        Collection<TimeDependedElement> collection = list;
        for (TimeDependedElement line : collection) {
            System.out.println( line );
        }
    }
}
