package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Resume implements Comparable<Resume>, Serializable {
    //Устанавливаем версию класса самостоятельно, чтобы не генерилась автоматически
    // При изменении класса самостоятельно ставим новую версию
    private static final long serialVersionID = 1L;
    // Unique identifier
    private String uuid;
    private String fullName;

    private final Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }
    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, " Resume.uuid must not be null");
        Objects.requireNonNull(fullName, " Resume.fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }
    public Resume(String fullName) {
        this( UUID.randomUUID().toString(), fullName);
    }

    public void addContact(ContactType contactType, Contact contact) {
        contacts.put(contactType, contact);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        if (sections.containsKey(sectionType)) {
            return sections.get(sectionType);
        }
        throw new RuntimeException();
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public String getContact(ContactType contactType) {
        if (contacts.containsKey(contactType)) {
            return contacts.get(contactType).getText();
        }
        return "";
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
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
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
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

    public void setFullName(String fullName) {
        Objects.requireNonNull(fullName, " Resume.fullName must not be null");
        this.fullName = fullName;
    }
}

