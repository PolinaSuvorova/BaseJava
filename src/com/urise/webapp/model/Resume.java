package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;

    protected List<Contact> contacts = new ArrayList<>();
    protected List<AbstractSection> sections = new ArrayList<>();

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<AbstractSection> getSections() {
        return sections;
    }

    public void addContact(Contact contact ){
        contacts.add(contact);
    };

    public void addSection( AbstractSection section ){
        sections.add(section);
    };
    public Resume(String uuid) {
        this(uuid, "Any_Full_Name" + UUID.randomUUID());
    }

    public String getFullName() {
        return fullName;
    }
    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid != null && fullName != null ? uuid.hashCode() + fullName.hashCode() : 0;
    }

   //@Override
   // public String toString() {
   //     return fullName + "(uuid=" + uuid + ')';
  //  }

    @Override
    public int compareTo(Resume o) {
        int resCompare = fullName.compareTo(o.fullName);
        return resCompare != 0 ? resCompare : uuid.compareTo(o.uuid);
    }
}

