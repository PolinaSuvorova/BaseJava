package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        Resume resumeOld = (Resume) searchKey;
        String uuidOld = resumeOld.getUuid();
        map.remove(uuidOld);
        map.put(resume.getUuid(),resume);
        //map.replace(resumeOld.getUuid(),resume);
    }

    @Override
    protected void doSave(Resume resume) {
        String uuid = resume.getUuid();
        map.put( uuid, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        map.remove(resume.getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[map.size()];
        int index = 0;
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            resumes[index] = entry.getValue();
            index++;
        }
        return resumes;
    }

    @Override
    public int size() {
        return map.size();
    }

    protected boolean isExistKey(Object searchKey) {
        return searchKey != null;
    }
}
