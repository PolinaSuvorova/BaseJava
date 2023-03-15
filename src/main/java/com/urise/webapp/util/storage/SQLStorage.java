package com.urise.webapp.util.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    // Получаем соединение с БД
    public SqlHelper sqlHelper;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
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
    public void update(String uuid, String fullName) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, fullName);
                    ps.executeUpdate();
                    return null;
                }); }

    @Override
    public void save(Resume resume) {
        try {
            sqlHelper.execute("INSERT INTO resume (uuid,full_name) VALUES (?,?)",
                    ps -> {
                        ps.setString(1,resume.getUuid());
                        ps.setString(2,resume.getFullName());
                        ps.execute();
                        return null;
                    });
        }catch (StorageException e) {
            throw new ExistStorageException(resume.getUuid());
        }

    }

    @Override
    public Resume get(String uuid) {
     return sqlHelper.execute("SELECT * FROM resume where uuid =?",
                ps -> {
                    ps.setString(1,uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()){
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid,rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume where uuid =?",
                ps -> {
                    ps.setString(1,uuid);
                    ps.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"),
                                       rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(uuid) from resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()){
                        throw new NotExistStorageException("");
                    }
                    return rs.getInt(1);
                });
    }
}
