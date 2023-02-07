package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.EnumSet;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("uuid", "Григорий Кислин");

        resume.addContact(TypeContact.PHONE, new Contact("+7(921) 855-0482"));
        resume.addContact(TypeContact.SKYPE, new Contact("skype:grigory.kislin"));
        resume.addContact(TypeContact.EMAIL, new Contact("gkislin@yandex.ru"));
        resume.addContact(TypeContact.LINKEDIN, new Contact("https://github.com/gkislin"));

        TextSection position = new TextSection();
        position.addElementSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(TypeSection.POSITION, position);

        TextSection personal = new TextSection();
        personal.addElementSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(TypeSection.PERSONAL, personal);

        ListTextSection achievement = new ListTextSection();
        achievement.addElementSection("Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков");
        achievement.addElementSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven");
        achievement.addElementSection("Реализация двухфакторной аутентификации для онлайн платформы");
        achievement.addElementSection("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA");
        resume.addSection(TypeSection.ACHIEVEMENT, achievement);

        ListTextSection qualifications = new ListTextSection();
        qualifications.addElementSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
                "WebLogic, WSO2");
        qualifications.addElementSection("Version control: Subversion, Git, Mercury, ClearCase, " +
                "Perforce");
        qualifications.addElementSection("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript," +
                " Groovy");
        qualifications.addElementSection("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(TypeSection.QUALIFICATIONS, qualifications);

        CompanySection experience = new CompanySection();
        experience.addElementSection(
                new Company(
                        new Period("10/2013", "Сейчас",
                                "Автор проекта",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."),
                        "Java Online Projects"));
        experience.addElementSection(
                new Company(new Period(
                        "10/2014", "10/2014",
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                                "Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                                "OAuth2, JWT SSO."), "Wrike"));
        experience.addElementSection(
                new Company(
                        new Period("04/2012", "10/2014",
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: "
                                        + "релизная политика, версионирование, ведение CI (Jenkins)"), "RIT " +
                        "Center"));
        resume.addSection(TypeSection.EXPETIENCE, experience);

        CompanySection education = new CompanySection();
        education.addElementSection(
                new Company(
                        new Period("03/2013", "05/2013",
                                "Coursera",
                                "'Functional Programming Principles in Scala' by Martin Odersky"
                        ), "Coursera"));
        education.addElementSection(
                new Company(
                        new Period("03/2011", "04/2011",
                                "Luxoft",
                                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."
                        ), "Luxoft"));
        education.addElementSection(
                new Company(
                        new Period("01/2005", "04/2005",
                                "Siemens AG",
                                "3 месяца обучения мобильным IN сетям (Берлин)"
                        ), "Siemens AG"));
        education.addElementSection(
                new Company(
                        new Period("01/2005", "04/2005",
                                "Alcatel",
                                "6 месяцев обучения цифровым телефонным сетям (Москва)"
                        ), "Alcatel"));
        education.addElementSection(
                new Company(
                        new Period("09/1993", "07/1996",
                                "Санкт-Петербургский национальный исследовательский университет",
                                "Аспирантура (программист С, С++)"
                        ), "Санкт-Петербургский национальный исследовательский университет"));
        education.addElementSection(
                new Company(
                        new Period("09/1987", "07/1993",
                                "Санкт-Петербургский национальный исследовательский университет",
                                "Инженер (программист Fortran, C)"
                        ), "Санкт-Петербургский национальный исследовательский университет"));
        resume.addSection(TypeSection.EDUCATION, education);

        System.out.println("Резюме \n" + resume.getFullName());
        System.out.println("Контакты");
        for (TypeContact infoContact : EnumSet.allOf(TypeContact.class)) {
            try {
                Contact contact = resume.getContact(infoContact);
                System.out.println(infoContact.getTitle() + ": "+ contact);
            } catch (RuntimeException reex) {
            }
        }

        for (TypeSection info : EnumSet.allOf(TypeSection.class)) {
            try {
                AbstractSection section = resume.getSection(info);
                System.out.println(info.getTitle());
                System.out.println(section);
            } catch (RuntimeException reex) {
            }
        }
    }
}
