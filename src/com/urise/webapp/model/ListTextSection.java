package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListTextSection extends AbstractSection{
    private List<String> list = new ArrayList<>();

    public ListTextSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        Collection<String> collection = list;
        for (String str : collection) {
            text.append("\n");
            text.append(str);
        }
        return text.toString();
    }
}
