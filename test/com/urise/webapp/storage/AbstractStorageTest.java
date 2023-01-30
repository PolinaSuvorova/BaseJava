package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public abstract class AbstractStorageTest {
    final protected Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_4 = "uuid_4";
    private static final String UUID_DUMMY = "UUID_DUMMY";

    private static final String NAME_1 = "NAME_1";
    private static final String NAME_2 = "NAME_2";
    private static final String NAME_3 = "NAME_3";
    private static final String NAME_4 = "NAME_4";
    private static final String NAME_DUMMY = "NAME_DUMMY";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    private static final Resume RESUME_DUMMY;

    static {
        RESUME_1 = new Resume(UUID_1,NAME_1);
        RESUME_2 = new Resume(UUID_2,NAME_2);
        RESUME_3 = new Resume(UUID_3,NAME_3);
        RESUME_4 = new Resume(UUID_4,NAME_4);
        RESUME_DUMMY = new Resume(UUID_DUMMY,NAME_DUMMY);
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        storage.update(UUID_1, NAME_4);
//        Assert.assertSame(RESUME_4, storage.get(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void updateNotExist() throws Exception {
         storage.update(UUID_DUMMY, NAME_DUMMY);
     }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveStorageException() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(RESUME_DUMMY.getUuid());
    }

    @Test
    public void getAllSorted() throws Exception {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        List<Resume> current = storage.getAllSorted();
        Assert.assertArrayEquals(expected,current.toArray());
    }

    protected void assertSize(int sizeExpected) throws Exception {
        Assert.assertEquals(sizeExpected, storage.size());
    }

    private void assertGet(Resume resumeExpected) throws NotExistStorageException {
        Assert.assertEquals(resumeExpected, storage.get(resumeExpected.getUuid()));
    }
}