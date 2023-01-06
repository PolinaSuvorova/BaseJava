package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("Резюме c " + uuid + " не существует", uuid);
    }
}
