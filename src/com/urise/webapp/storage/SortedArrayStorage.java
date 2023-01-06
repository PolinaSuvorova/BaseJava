package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int newIndex = Math.abs(index) - 1;
        System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex + 1);
        storage[newIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        deleteResume(index);
        int newIndex = getIndex(resume.getUuid());
        insertResume(newIndex,resume);
    }

}
