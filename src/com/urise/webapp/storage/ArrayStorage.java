package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    static private final int RESUME_LIMIT = 10000;
    Resume[] storage = new Resume[RESUME_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(String uuid, Resume resume) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid %s не найдено%n", uuid);
            return;
        }
        storage[index] = resume;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) < 0) {
            storage[size] = resume;
            size++;
        } else if (size >= RESUME_LIMIT) {
            System.out.println("Хранилище переполненно");
        } else {
            System.out.printf("Резюме с uuid %s уже существует%n", resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid %s не найдено%n", uuid);
            return;
        }
        storage[index] = storage[size];
        storage[size] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
