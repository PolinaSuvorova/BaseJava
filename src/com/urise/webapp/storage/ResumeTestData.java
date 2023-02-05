package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.util.Collection;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid", "Григорий Кислин");
        resume.addContact(new Contact("Тел.", "+7(921) 855-0482"));
        resume.addContact(new Contact("Skype", "skype:grigory.kislin"));
        resume.addContact(new Contact("Почта", "gkislin@yandex.ru"));
        resume.addContact(new Contact("Профиль LinkedIn", "https://github.com/gkislin"));

        SectionAsText position = new SectionAsText("Позиция");
        position.addElementSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(position);

        SectionAsText personal = new SectionAsText("Личные качества");
        position.addElementSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(personal);

        SectionAsListText achievement = new SectionAsListText("Достижения");
        achievement.addElementSection("Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков");
        achievement.addElementSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven");
        achievement.addElementSection("Реализация двухфакторной аутентификации для онлайн платформы");
        achievement.addElementSection("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA");
        resume.addSection(achievement);

        SectionAsListText qualifiations = new SectionAsListText("Квалификация");
        qualifiations.addElementSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
                "WebLogic, WSO2");
        qualifiations.addElementSection("Version control: Subversion, Git, Mercury, ClearCase, " +
                "Perforce");
        qualifiations.addElementSection("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript," +
                " Groovy");
        qualifiations.addElementSection("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(qualifiations);

        TimeDependedSection experience = new TimeDependedSection("Опыт работы");
        experience.addElementSection(
                new TimeDependedElement(
                        "10/2013", "Сейчас",
                        "Java Online Projects",
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.addElementSection(
                new TimeDependedElement(
                        "10/2014", "10/2014",
                        "Wrike",
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                                "Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                                "OAuth2, JWT SSO."));
        experience.addElementSection(
                new TimeDependedElement(
                        "04/2012", "10/2014",
                        "RIT Center",
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: "
                         + "релизная политика, версионирование, ведение CI (Jenkins)"));
        TimeDependedSection education = new TimeDependedSection("Образование");
        education.addElementSection(
                new TimeDependedElement(
                        "03/2013", "05/2013",
                        "Coursera",
                        "'Functional Programming Principles in Scala' by Martin Odersky"
                                ));
        education.addElementSection(
                new TimeDependedElement(
                        "03/2011", "04/2011",
                        "Luxoft",
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."
                ));
        education.addElementSection(
                new TimeDependedElement(
                        "01/2005", "04/2005",
                        "Siemens AG",
                        "3 месяца обучения мобильным IN сетям (Берлин)"
                ));
        education.addElementSection(
                new TimeDependedElement(
                        "01/2005", "04/2005",
                        "Alcatel",
                        "6 месяцев обучения цифровым телефонным сетям (Москва)"
                ));
        education.addElementSection(
                new TimeDependedElement(
                        "09/1993", "07/1996",
                        "Санкт-Петербургский национальный исследовательский университет",
                        "Аспирантура (программист С, С++)"
                ));
        education.addElementSection(
                new TimeDependedElement(
                        "09/1987", "07/1993",
                        "Санкт-Петербургский национальный исследовательский университет",
                        "Инженер (программист Fortran, C)"
                ));


        Collection<Contact> contacts = resume.getContacts();
        for (Contact contact : contacts) {
            contact.printSection();
        }
        Collection<AbstractSection> sections = resume.getSections();
        for (AbstractSection section : sections) {
            section.printSection();
        }
    }
}
