package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    //    Внутренний статический класс
//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }
// private static final ResumeComparator RESUME_COMPARATOR = new ResumeComparator() {

    // Анонимный класс
    //    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
    //    @Override
    //    public int compare(Resume o1, Resume o2) {
    //        return o1.getUuid().compareTo(o2.getUuid());
    //    }
    //};

    //Лямда выражение
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int newIndex = Math.abs(index) - 1;
        System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        storage[newIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void updateResume(int index, Resume resume) {

    }
}
