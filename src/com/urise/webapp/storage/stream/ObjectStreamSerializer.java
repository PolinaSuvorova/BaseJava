package com.urise.webapp.storage.stream;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        // Оборачиваем в try чтобы все открытые буферизированные данные были закрыты автоматически
        // при выходе из try (см. интерфейс у родителей AutoCloseable)
        try(ObjectOutputStream oos = new ObjectOutputStream( outputStream )) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream( inputStream )) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume",null,e);
        }
    }
}
