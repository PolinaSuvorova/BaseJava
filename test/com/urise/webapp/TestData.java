package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");
        R2.addContact(ContactType.SKYPE, new Contact( "mail2@ya.ru"));
        R2.addContact(ContactType.PHONE, new Contact( "222222"));

        R1.addContact(ContactType.EMAIL, new Contact( "mail1@ya.ru") );
        R1.addContact(ContactType.PHONE, new Contact("11111"));
        R4.addContact(ContactType.PHONE, new Contact("44444"));
        R4.addContact(ContactType.SKYPE, new Contact("Skype4"));
        R1.addSection(SectionType.PERSONAL,    new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListTextSection("Achivment11", "Achivment12", "Achivment13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListTextSection("Java", "SQL", "JavaScript"));

        List<Company> experience = new ArrayList<>();
        experience.add(new Company(
                List.of(
                        new Period(
                                DateUtil.of(2013, Month.OCTOBER),
                                LocalDate.MAX,
                                "experience1",
                                "описание 1")),
                "Java Online Projects",
                "www.сайт.ru"));

        experience.add(
                new Company(List.of(
                        new Period(
                                DateUtil.of(2014, Month.OCTOBER),
                                DateUtil.of(2016, Month.JANUARY),
                                "experience2",
                                "описание 2")),
                          "Wrike",
                        "www.сайт.ru")
        );
        R1.addSection(SectionType.EXPERIENCE, new CompanySection(experience));

        List<Company> education = new ArrayList<>();
        education.add(
                new Company(List.of(
                        new Period(
                                DateUtil.of(2013, Month.MARCH),
                                DateUtil.of(2013, Month.MAY),
                                "Обучение 1",
                                "Обучение 1 описание"
                        )),
                        "Coursera",
                        "www.сайт.ru"));
        new Company(List.of(
                new Period(
                        DateUtil.of(2013, Month.MARCH),
                        DateUtil.of(2013, Month.MAY),
                        "Обучение 2",
                        "Обучение 2 описание"
                )),
                        "Luxoft",
                        "www.сайт.ru");
        education.add(
                new Company(
                        Arrays.asList(new Period(
                                DateUtil.of(1993, Month.SEPTEMBER),
                                DateUtil.of(1996, Month.JULY),
                                "Санкт-Петербургский национальный исследовательский университет",
                                "период 1"
                        ), new Period(
                                DateUtil.of(1987, Month.SEPTEMBER),
                                DateUtil.of(1993, Month.JULY),
                                "Санкт-Петербургский национальный исследовательский университет",
                                "период 2)"
                        )),
                         "Обучение 3",
                        "www.сайт.ru"));
        R1.addSection(SectionType.EDUCATION, new CompanySection(education));
    }
}

