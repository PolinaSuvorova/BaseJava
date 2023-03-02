package com.urise.webapp.storage.stream;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, Contact> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(String.valueOf(entry.getValue()));
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL ->
                            dos.writeUTF(((TextSection) entry.getValue()).getDescription());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListTextSection) entry.getValue()).getTextSections();
                        dos.writeInt(list.size());
                        for (String value : list) {
                            dos.writeUTF(value);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                        dos.writeInt(companyList.size());
                        for (Company company : companyList) {

                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());

                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), new Contact(dis.readUTF()));
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                AbstractSection section = null;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = new ArrayList<>();
                        int sizeList = dis.readInt();
                        for (int j = 0; j < sizeList; j++) {
                            list.add(dis.readUTF());
                        }
                        section = new ListTextSection(list);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = new ArrayList<>();
                        int sizeList = dis.readInt();

                        for (int j = 0; j < sizeList; j++) {

                            Company company = new Company(
                                    dis.readUTF(),
                                    dis.readUTF());

                            int periodCount = dis.readInt();
                            List<Period> periods = new ArrayList<>();
                            for (int k = 0; k < periodCount; k++) {
                                periods.add(new Period(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF()));
                            }

                            company.setPeriods(periods);
                            companies.add(company);
                        }
                        section = new CompanySection(companies);
                    }
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }
}
