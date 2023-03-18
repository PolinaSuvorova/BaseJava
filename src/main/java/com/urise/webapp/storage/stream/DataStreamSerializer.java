package com.urise.webapp.storage.stream;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, Contact> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(),
                    entry -> {
                        dos.writeUTF(entry.getKey().name());
                        dos.writeUTF(String.valueOf(entry.getValue()));
                    }
            );

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(dos, sections.entrySet(),
                    entry -> {
                        dos.writeUTF(entry.getKey().name());
                        switch (entry.getKey()) {
                            case POSITION, PERSONAL -> {
                                dos.writeUTF(((TextSection) entry.getValue()).getDescription());
                            }
                            case ACHIEVEMENT, QUALIFICATIONS -> {
                                List<String> list = ((ListTextSection) entry.getValue()).getTextSections();
                                writeWithException(dos, list, value -> dos.writeUTF(value));
                            }
                            case EXPERIENCE, EDUCATION -> {
                                List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                                writeWithException(dos, companyList, company -> {
                                    dos.writeUTF(company.getWebsite());
                                    dos.writeUTF(company.getName());

                                    List<Period> periods = company.getPeriods();
                                    writeWithException(dos, periods, period -> {
                                        dos.writeUTF(period.getStartDate().toString());
                                        dos.writeUTF(period.getEndDate().toString());
                                        dos.writeUTF(period.getTitle());
                                        dos.writeUTF(period.getDescription());
                                    });
                                });
                            }
                        }
                    });
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection,
                                        WriteElementCollection<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElementCollection(element);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()),
                        new Contact(dis.readUTF()));
                return null;
            });

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                AbstractSection section = null;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case POSITION, PERSONAL -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        section = new ListTextSection(readListWithException(dis, dis::readUTF));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        section = new CompanySection(
                                readListWithException(dis, () -> {
                                    Company company = new Company(dis.readUTF(), dis.readUTF());
                                    company.setPeriods(
                                            readListWithException(dis, () -> new Period(
                                                    LocalDate.parse(dis.readUTF()),
                                                    LocalDate.parse(dis.readUTF()),
                                                    dis.readUTF(), dis.readUTF()
                                            )));
                                    return company;
                                }));

                    }
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }


    private <T> List<T> readListWithException(DataInputStream dos,
                                              ReadElementFromFile<T> reader) throws IOException {
        int size = dos.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readElementFromFile());
        }
        return list;
    }

    private void readWithException(DataInputStream dis, ReadElementFromFile reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readElementFromFile();
        }
    }
}


