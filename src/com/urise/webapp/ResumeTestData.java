package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("uuid", "Григорий Кислин");

        resume.addContact(ContactType.PHONE, new Contact("+7(921) 855-0482"));
        resume.addContact(ContactType.SKYPE, new Contact("skype:grigory.kislin"));
        resume.addContact(ContactType.EMAIL, new Contact("gkislin@yandex.ru"));
        resume.addContact(ContactType.LINKEDIN, new Contact("https://github.com/gkislin"));

        resume.addSection(SectionType.POSITION, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная" +
                " логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java " +
                "Enterprise\", \"Многомодульный maven");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке " +
                "технологий JPA");

        resume.addSection(SectionType.ACHIEVEMENT, new ListTextSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
                "WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, " +
                "Perforce");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript," +
                " Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(SectionType.QUALIFICATIONS, new ListTextSection(qualifications));
        List<Company> experience = new ArrayList<>();
        experience.add(
                new Company(
                        Arrays.asList(new Period("10/2013", "Сейчас",
                                "Автор проекта",
                                "Создание, организация и проведение Java онлайн проектов " +
                                        "и стажировок.")),
                        "Java Online Projects",
                        "www.сайт.ru"));
        experience.add(
                new Company(Arrays.asList(
                        new Period(
                                "10/2014", "10/2014",
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                                        "Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                                        "OAuth2, JWT SSO.")),
                        "Wrike",
                        "www.сайт.ru"));
        experience.add(
                new Company(Arrays.asList(
                        new Period("04/2012", "10/2014",
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: "
                                        + "релизная политика, версионирование, ведение CI " +
                                        "(Jenkins)")),
                        "RIT Center",
                        "www.сайт.ru"));
        resume.addSection(SectionType.EXPETIENCE, new CompanySection(experience));

        List<Company> education = new ArrayList<>();
        education.add(
                new Company(Arrays.asList(
                        new Period("03/2013", "05/2013",
                                "Coursera",
                                "'Functional Programming Principles in Scala' by Martin Odersky"
                        )),
                        "Coursera",
                        "www.сайт.ru"));
        education.add(
                new Company(
                        Arrays.asList(new Period("03/2011", "04/2011",
                                "Luxoft",
                                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."
                        )),
                        "Luxoft",
                        "www.сайт.ru"));
        education.add(
                new Company(
                        Arrays.asList(new Period("01/2005", "04/2005",
                                "Siemens AG",
                                "3 месяца обучения мобильным IN сетям (Берлин)"
                        )),
                        "Siemens AG",
                        "www.сайт.ru"));
        education.add(
                new Company(
                        Arrays.asList(new Period("01/2005", "04/2005",
                                "Alcatel",
                                "6 месяцев обучения цифровым телефонным сетям (Москва)"
                        )),
                         "Alcatel",
                        "www.сайт.ru"));

        education.add(
                new Company(
                        Arrays.asList(new Period("09/1993", "07/1996",
                                "Санкт-Петербургский национальный исследовательский университет",
                                "Аспирантура (программист С, С++)"
                        ), new Period("09/1987", "07/1993",
                                "Санкт-Петербургский национальный исследовательский университет",
                                "Инженер (программист Fortran, C)"
                        )),
                        "Санкт-Петербургский национальный исследовательский университет",
                        "www.сайт.ru"));
        resume.addSection(SectionType.EDUCATION, new CompanySection(education));

        System.out.println("Резюме \n" + resume.getFullName());
        System.out.println("Контакты");
        for (ContactType infoContact : EnumSet.allOf(ContactType.class)) {
            try {
                Contact contact = resume.getContact(infoContact);
                System.out.println(infoContact.getTitle() + ": " + contact);
            } catch (RuntimeException reex) {
            }
        }

        for (SectionType info : EnumSet.allOf(SectionType.class)) {
            try {
                AbstractSection section = resume.getSection(info);
                System.out.println(info.getTitle());
                System.out.println(section);
            } catch (RuntimeException reex) {
            }
        }
    }
}
