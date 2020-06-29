package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
    }

    public void update(Resume r) {
        if (r == null || r.getUuid() == null) return;

        int index = getIndex(r);
        if (hasResume(index, r)) {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (r == null || r.getUuid() == null) return;

        int index = getIndex(r);
        if (index != -1) {
            System.out.format("[Error] Resume with uuid: %s already exists \n", r.getUuid());
            if (index >= STORAGE_LIMIT) {
                System.out.format("[Error] Reached a maximum size of %d\n", STORAGE_LIMIT);
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public void delete(String uuid) {
        if (uuid == null) return;

        int index = getIndex(uuid);
        if (hasResume(index, uuid)) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    private Integer getIndex(Resume r) {
        return getIndex(r.getUuid());
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
