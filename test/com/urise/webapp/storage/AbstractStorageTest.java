package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Contact;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.urise.webapp.TestData.*;

public abstract class AbstractStorageTest {
    final protected Storage storage;
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.addContact(ContactType.EMAIL, new Contact("New@google.com"));
        newResume.addContact(ContactType.SKYPE, new Contact("NewSkype"));
        newResume.addContact(ContactType.PHONE, new Contact("+7 921 222-22-22"));
        storage.update(newResume.getUuid(), newResume);
        Assert.assertTrue(newResume.equals(storage.get(R1.getUuid())));
    }

    @Test(expected = StorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(R4.getUuid(), R4);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveStorageException() {
        storage.save(R1);
    }

    @Test
    public void get() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(R1.getUuid());
        assertSize(2);
        assertGet(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(R4.getUuid());
    }

    @Test
    public void getAllSorted() throws Exception {
        Resume[] expected = {R1, R2, R3};
        List<Resume> current = storage.getAllSorted();
        Assert.assertArrayEquals(expected, current.toArray());
    }

    protected void assertSize(int sizeExpected) throws Exception {
        Assert.assertEquals(sizeExpected, storage.size());
    }

    private void assertGet(Resume resumeExpected) throws NotExistStorageException {
        Assert.assertEquals(resumeExpected, storage.get(resumeExpected.getUuid()));
    }
}