package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private static final String UUID_DUMMY = "uuid_DUMMY";

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        assertSize(0);
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("UUID_" + i, "Name" + i));
            } catch (StorageException errIO) {
                Assert.fail("Переполнение до момента проверки");
            }
        }
        storage.save(new Resume(UUID_DUMMY, "Name dummy"));
    }
}
