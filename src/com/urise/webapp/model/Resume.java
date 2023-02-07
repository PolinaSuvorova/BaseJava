package com.urise.webapp.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;

    protected EnumMap<TypeContact, Contact> contacts = new EnumMap<>(TypeContact.class);
    protected EnumMap<TypeSection, AbstractSection> sections = new EnumMap<>(TypeSection.class);

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public List<Contact> getContacts() {
        Contact[] array = contacts.values().toArray(new Contact[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }

    public List<AbstractSection> getSections() {
        AbstractSection[] array = sections.values().toArray(new AbstractSection[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }

    public void addContact(TypeContact typeContact, Contact contact) {
        contacts.put(typeContact, contact);
    }

    public void addSection(TypeSection typeSection, AbstractSection section) {
        sections.put(typeSection, section);
    }

    public AbstractSection getSection(TypeSection typeSection) {
        if (sections.containsKey(typeSection)) {
            return sections.get(typeSection);
        }
        throw new RuntimeException();
    }

    public Contact getContact(TypeContact typeContact) {
        if (contacts.containsKey(typeContact)) {
            return contacts.get(typeContact);
        }
        throw new RuntimeException();
    }

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

