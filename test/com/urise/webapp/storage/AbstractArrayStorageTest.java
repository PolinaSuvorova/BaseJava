package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    final protected Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";

    private static final String UUID_4 = "uuid_4";
    private static final String UUID_DUMMY = "uuid_dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final Resume RESUME_4 = new Resume(UUID_4);

    private static final Resume RESUME_DUMMY = new Resume(UUID_DUMMY);

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public AbstractArrayStorageTest(Storage storage) {
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
        assertGetNull(RESUME_4,false);
        storage.update(UUID_1, RESUME_4);
        assertGet(RESUME_4);
    }

    @Test
    public void updateNotExist() throws Exception {
        try {
            storage.update(UUID_4, RESUME_DUMMY);
        } catch (StorageException errIO) {
            Assert.fail(errIO.getMessage());
        }
    }

    @Test
    public void save() throws Exception {
        storage.clear();
        assertSize(0);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        assertSize(0);
        boolean exit = false;
        int index = 0;
        while (!exit) {
            index += 1;
            try {
                storage.save(new Resume("UUID_" + index));
            } catch (StorageException errIO) {
                exit = true;
                Assert.fail(errIO.getMessage());
            }
        }
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

    @Test
    public void getNotExist() throws Exception {
        assertGetNull(RESUME_DUMMY, true);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(RESUME_1.getUuid());
        assertGetNull(RESUME_1, true);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(RESUME_DUMMY.getUuid());
    }

    @Test
    public void getAll() throws Exception {
        assertSize(storage.getAll().length);
    }

    private void assertSize(int sizeExpected) throws Exception {
        Assert.assertEquals(sizeExpected, storage.size());
    }

    private void assertGet(Resume resumeExpected) throws Exception {
        storage.get(resumeExpected.getUuid());
    }

    private void assertGetNull(Resume resumeExpected , boolean withMessage) throws Exception {
        try {
            storage.get(resumeExpected.getUuid());
            Assert.assertNull(resumeExpected);
        }catch (StorageException errIO) {
            if ( withMessage ) {
                Assert.fail(errIO.getMessage());
            }
        }
    }
}