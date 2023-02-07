package com.urise.webapp.model;

public class Contact {
    private final String text;

   public Contact( String text){
          this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
