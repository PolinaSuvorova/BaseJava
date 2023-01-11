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
        storage.update(UUID_1, RESUME_4);
        Assert.assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = StorageException.class)
    public void updateNotExist() throws Exception {
        try {
            storage.update(UUID_4, RESUME_DUMMY);
        } catch (StorageException errIO) {
            Assert.fail(errIO.getMessage());
        }
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        assertSize(0);
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("UUID_" + i));
            } catch (StorageException errIO) {
                Assert.fail("Переполнение до момента проверки");
            }
        }
        storage.save(new Resume(UUID_DUMMY));
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
    public void getAll() throws Exception {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    private void assertSize(int sizeExpected) throws Exception {
        Assert.assertEquals(sizeExpected, storage.size());
    }

    private void assertGet(Resume resumeExpected) throws NotExistStorageException {
        Assert.assertEquals(resumeExpected, storage.get(resumeExpected.getUuid()));
    }
}