package com.urise.webapp.model;

public class SectionAsText extends AbstractSection{
    protected String description;
    public SectionAsText(String nameSection) {
        super(nameSection);
    }

    @Override
    public void addElementSection(Object element) {
        description = (String)  element;
    }

    @Override
    protected void doPrintSection() {
        System.out.println( "* " + description);
    }

}
