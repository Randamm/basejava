package storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractArrayStorage;
import com.urise.webapp.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private static final String TEST_UUID = "tst";
    private static final String INITIAL_UUID_1 = "uuid1";

    private static final Resume INITIAL_RESUME_1 = new Resume(INITIAL_UUID_1);
    private static final Resume INITIAL_RESUME_2 = new Resume("uuid2");
    private static final Resume INITIAL_RESUME_3 = new Resume("uuid3");
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(INITIAL_RESUME_1);
        storage.save(INITIAL_RESUME_2);
        storage.save(INITIAL_RESUME_3);
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
        Resume newResume = new Resume(INITIAL_UUID_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(newResume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get(TEST_UUID);
    }

    @Test
    public void getAll() throws Exception {
        assertArrayEquals(new Resume[]{INITIAL_RESUME_1, INITIAL_RESUME_2, INITIAL_RESUME_3}, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume();
        storage.save(r);
        assertSize(4);
        assertGet(r);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(INITIAL_RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Exception was thrown before size limit reached");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(INITIAL_UUID_1);
        assertSize(2);
        storage.get(INITIAL_UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(TEST_UUID);
    }

    @Test
    public void get() throws Exception {
        assertGet(INITIAL_RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(TEST_UUID);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
