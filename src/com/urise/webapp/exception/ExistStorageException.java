package com.urise.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("Резюме с " + uuid + " уже существует", uuid);
    }
}
