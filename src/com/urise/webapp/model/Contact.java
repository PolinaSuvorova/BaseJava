package com.urise.webapp.model;

public class Contact {
    private final String typeContact;
    private final String text;

   public Contact( String typeContact, String text){
        this.typeContact = typeContact;
        this.text = text;
    }

    @Override
    public String toString() {
        return typeContact + ": " + text;
    }

}
