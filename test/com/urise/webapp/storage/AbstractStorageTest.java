package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.exception.model.*;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    final protected Storage storage;
    protected static final String STORAGE_DIR_TXT = "C:\\Users\\ptatara\\Desktop\\TestDirectory";
    protected static final File STORAGE_DIR = new File(STORAGE_DIR_TXT);
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_4 = "uuid_4";
    private static final String UUID_DUMMY = "UUID_DUMMY";

    private static final String NAME_1 = "NAME_1";
    private static final String NAME_2 = "NAME_2";
    private static final String NAME_3 = "NAME_3";
    private static final String NAME_4 = "NAME_4";
    private static final String NAME_DUMMY = "NAME_DUMMY";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    private static final Resume RESUME_DUMMY;

    static {
        RESUME_1 = setCompletedResume(UUID_1, NAME_1);
        RESUME_2 = setCompletedResume(UUID_2, NAME_2);
        RESUME_3 = setCompletedResume(UUID_3, NAME_3);
        RESUME_4 = setCompletedResume(UUID_4, NAME_4);
        RESUME_DUMMY = setCompletedResume(UUID_DUMMY, NAME_DUMMY);
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        storage.update(UUID_1, NAME_4);
//        Assert.assertSame(RESUME_4, storage.get(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(UUID_DUMMY, NAME_DUMMY);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveStorageException() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(RESUME_DUMMY.getUuid());
    }

    @Test
    public void getAllSorted() throws Exception {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        List<Resume> current = storage.getAllSorted();
        Assert.assertArrayEquals(expected, current.toArray());
    }

    protected void assertSize(int sizeExpected) throws Exception {
        Assert.assertEquals(sizeExpected, storage.size());
    }

    private void assertGet(Resume resumeExpected) throws NotExistStorageException {
        Assert.assertEquals(resumeExpected, storage.get(resumeExpected.getUuid()));
    }

    protected static Resume setCompletedResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);

        //Личные качества
        TextSection personal = new TextSection("Личные качества (текст) ");
        resume.addSection(SectionType.PERSONAL, personal);
        //Контакты
        resume.addContact(ContactType.PHONE, new Contact("+7(921) 855-0482"));
        resume.addContact(ContactType.SKYPE, new Contact("skype:grigory.kislin"));
        resume.addContact(ContactType.EMAIL, new Contact("gkislin@yandex.ru"));
        resume.addContact(ContactType.LINKEDIN, new Contact("https://github.com/gkislin"));
        //Позиция
        resume.addSection(SectionType.POSITION, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная" + " логика, креативность, инициативность. Пурист кода и архитектуры."));

        //Достижения
        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA");
        resume.addSection(SectionType.ACHIEVEMENT, new ListTextSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " + "WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, " + "Perforce");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript," + " Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(SectionType.QUALIFICATIONS, new ListTextSection(qualifications));
        //Опыт
        List<Company> experience = new ArrayList<>();

        experience.add(new Company(
                Arrays.asList(
                        new Period(DateUtil.of(2013, Month.OCTOBER),
                                LocalDate.MAX,
                                "Автор проекта",
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                "Java Online Projects", "www.сайт.ru"));
        experience.add(new Company(
                Arrays.asList(new Period(DateUtil.of(2014,Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                                "Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                                "OAuth2, JWT SSO.")),
                "Wrike",
                "www.сайт.ru"));
        experience.add(new Company(
                Arrays.asList(
                        new Period(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: " +
                                        "реализная политика, версионирование, ведение CI " +
                                        "(Jenkins)")),
                "RIT Center",
                "www.сайт.ru"));
        resume.addSection(SectionType.EXPETIENCE, new CompanySection(experience));
        //Образование
        List<Company> education = new ArrayList<>();
        education.add(new Company(
                Arrays.asList(new Period(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),
                        "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky")),
                "Coursera", "www.сайт.ru"));
        education.add(new Company(
                Arrays.asList(new Period(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL),
                        "Luxoft",
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.")),
                "Luxoft",
                "www.сайт.ru"));
        education.add(new Company(
                Arrays.asList(new Period(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL),
                        "Siemens AG",
                        "3 месяца обучения мобильным IN сетям (Берлин)")),
                "Siemens AG", "www.сайт.ru"));
        education.add(new Company(
                Arrays.asList(new Period(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL),
                        "Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)")),
                "Alcatel",
                "www.сайт.ru"));
        education.add(new Company(
                Arrays.asList(new Period(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY),
                        "Санкт-Петербургский национальный исследовательский университет",
                        "Аспирантура (программист С, С++)"),
                        new Period(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY),
                                "Санкт-Петербургский национальный исследовательский университет",
                                "Инженер (программист Fortran, C)")), "Санкт-Петербургский национальный исследовательский университет",
                "www.сайт.ru"));
        resume.addSection(SectionType.EDUCATION, new CompanySection(education));

        return resume;
    }
}