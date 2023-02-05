package com.urise.webapp.model;

public abstract class AbstractSection {
    protected String nameSection;

    public AbstractSection(String nameSection) {
    this.nameSection = nameSection;
    }
    public void printSection(){
        System.out.println("-------- " + nameSection);
        doPrintSection( );
        System.out.println("---------------------------------------------");
    }
    public abstract void addElementSection( Object element);
    protected abstract void doPrintSection( );
}
