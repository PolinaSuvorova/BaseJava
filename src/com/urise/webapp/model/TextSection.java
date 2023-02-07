package com.urise.webapp.model;

public class TextSection extends AbstractSection{
    protected String description;
    public TextSection( ) {
        super( );
    }

    @Override
    public void addElementSection(Object element) {
        description = (String)  element;
    }

    @Override
    public String toString() {
        return "----" + description;
    }
}
