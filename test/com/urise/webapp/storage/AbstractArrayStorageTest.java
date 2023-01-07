package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        String uuidTo = "uuid_update";
        String uuidUpd = "uuid_1";
        Resume resumeTo = storage.get(uuidTo);
        if (resumeTo == null) {
            resumeTo = new Resume(uuidTo);
            storage.update(uuidUpd, resumeTo);
        }
    }

    @Test
    public void updateError() throws Exception {
        String uuidTo = "uuid_to";
        String uuidUpd = "uuid_error";
        Resume resumeTo = storage.get(uuidTo);
        if (resumeTo == null) {
            resumeTo = new Resume(uuidTo);
            try {
                storage.update(uuidUpd, resumeTo);
            } catch (StorageException errIO) {
                Assert.fail(errIO.getMessage());
            }
        }
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("UUID_4");
        storage.save(r);
    }

    @Test(expected = StorageException.class)
    public void saveExistError() throws Exception {
        int i = 5;
        boolean exit = false;
        while (!exit) {
            i += 1;
            Resume r = new Resume("UUID_" + i);
            try {
                storage.save(r);
            } catch (StorageException errIO) {
                exit = true;
                Assert.fail(errIO.getMessage());
            }
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveStorageException() throws Exception {
        Resume r = new Resume("UUID_1");
        storage.save(r);
    }

    @Test
    public void get() throws Exception {
        Assert.assertNotEquals(null, storage.get("uuid_1"));
    }

    @Test
    public void getNotExist() throws Exception {
        Assert.assertNull(storage.get("uuid_ttt"));
    }

    @Test
    public void delete() throws Exception {
        storage.delete("uuid_1");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid_dummy");
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertNotEquals(0, storage.getAll().length);
    }
}