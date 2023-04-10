package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SQLStorage implements Storage {
    // Получаем соединение с БД
    public SqlHelper sqlHelper;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.sqlHelper = new SqlHelper(() -> {
            // DriverManager подходит для создания соединения с БД при работе
            // с тестовыми приложениями. для реальных приложений делается пул соединений
            // с БД, т.к. операция подключения к БД трудозатратна (подробно на курсе Enterprise)
            return DriverManager.getConnection(dbURL, dbUser, dbPassword);
        });
    }


    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(String uuid, Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(uuid);
            deleteSections(uuid);
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume " +
                            "WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact " +
                            "WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM section " +
                            "WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume where uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, Contact> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue().getText());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, text) VALUES (?,?,?)")) {
            String uuid = r.getUuid();
            for (SectionType sectionType : EnumSet.allOf(SectionType.class)) {
                String sectTy = String.valueOf(sectionType);
                try {
                    AbstractSection section = r.getSection(sectionType);
                    String sectionAsString = null;
                    switch (sectionType) {
                        case POSITION, PERSONAL -> {
                            TextSection textSection = (TextSection) section;
                            sectionAsString = textSection.getDescription();
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            ListTextSection textListSection = (ListTextSection) section;
                            sectionAsString = String.join("\n",
                                    textListSection.getTextSections());
                        }
                        case EXPERIENCE, EDUCATION -> {
                            CompanySection companySection = (CompanySection) section;
                            sectionAsString = JsonParser.write(companySection, CompanySection.class);
                        }
                    }
                    if (sectionAsString != null && !sectionAsString.isEmpty()) {
                        ps.setString(1, uuid);
                        ps.setString(2, sectTy);
                        ps.setString(3, sectionAsString);
                        ps.addBatch();
                    }
                } catch (RuntimeException err) {

                }
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(String uuid) {
        sqlHelper.execute("DELETE FROM contact where resume_uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        return null;
                    }
                    //throw new NotExistStorageException(uuid);
                    return null;
                });
    }

    private void deleteSections(String uuid) {
        sqlHelper.execute("DELETE FROM section where resume_uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        return null;
                    }
                    //throw new NotExistStorageException(uuid);
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume " +
                            "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    resumes.put(resume.getUuid(), resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                String resumeUuidOld = "";
                Resume r = null;
                while (rs.next()) {
                    String resume_uuid_current = rs.getString("resume_uuid");
                    if (!resume_uuid_current.equals(resumeUuidOld)) {
                        r = resumes.get(resume_uuid_current);
                    }
                    addSection(rs, r);
                    resumeUuidOld = resume_uuid_current;
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(uuid) from resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException("");
                    }
                    return rs.getInt(1);
                });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), new Contact(value));
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("type"));
        AbstractSection aSection;
        String text = rs.getString("text");
        if (text != null) {
            switch (sectionType) {
                case POSITION, PERSONAL -> {
                    aSection = new TextSection(text);
                    r.addSection(sectionType, aSection);
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    ArrayList<String> listString = new ArrayList<>();
                    String[] lines = text.split("\n", 0);
                    Collections.addAll(listString, lines);
                    aSection = new ListTextSection(listString);
                    r.addSection(sectionType, aSection);
                }
                case EXPERIENCE, EDUCATION -> {
                    CompanySection sect = JsonParser.read(text, CompanySection.class);
                    r.addSection(sectionType, sect);
                }
            }
        }
    }
}


