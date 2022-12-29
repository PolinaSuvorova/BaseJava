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

    public void update(String uuid) {
        int index = findPosition(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid %s не найдено%n", uuid);
           }
    }

    public void save(Resume r) {
        if (size >= RESUME_LIMIT) {
            System.out.println("Хранилище переполненно");
            return;
        }
        int index = findPosition(r.getUuid());
        if (index < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.printf("Резюме с uuid %s уже существует%n", r);
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findPosition(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid %s не найдено%n", uuid);
            return;
        }
        size--;
        System.arraycopy(storage, index + 1, storage, index, size - index);
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

    private int findPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
