package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListTextSection extends AbstractSection{
    protected List<String> list = new ArrayList<>();
    public ListTextSection( ) {
        super( );
    }

    @Override
    public void addElementSection(Object element) {
        String description = (String) element;
        list.add(description);
    }

    @Override
    public String toString() {
        String text = "";
        Collection<String> collection = list;
        for (String str : collection) {
            text = text + "\n" + str;
        }
        return text;
    }
}
