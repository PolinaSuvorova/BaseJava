package com.urise.webapp.util.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.List;

public class SQLStorage implements Storage {
    // Получаем соединение с БД
    public final ConnectionFactory connectionFactory;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        this.connectionFactory = new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                // DriverManager подходит для создания соединения с БД при работе
                // с тестовыми приложениями. для реальных приложений делается пул соединений
                // с БД, т.к. операция подключения к БД трудозатратна (подробно на курсе Enterprise)
                return DriverManager.getConnection(dbURL, dbUser, dbPassword);
            }
        };
    }

    @Override
    public void clear() {
      try(Connection conn = connectionFactory.getConnection() ){
       PreparedStatement ps = conn.prepareStatement("DELETE FROM resume");
       ps.execute();
      } catch (SQLException e) {
          throw new StorageException(e);
      }
    }

    @Override
    public void update(String uuid, String fullName) {

    }

    @Override
    public void save(Resume resume) {
        try(Connection conn = connectionFactory.getConnection() ){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid,full_name) " +
                            "VALUES (?,?)");
            ps.setString(1,resume.getUuid());
            ps.setString(2,resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try(Connection conn = connectionFactory.getConnection() ){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume where uuid=?");
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid,rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
