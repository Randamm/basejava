package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume r) {
        if (r == null || r.getUuid() == null) return;

        Integer index = getIndex(r);
        if (hasResume(index, r)) {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (r == null || r.getUuid() == null) return;

        if (getIndex(r) != null) {
            System.out.format("[Error] Resume with uuid: %s already exists \n", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;

        Integer index = getIndex(uuid);
        if (hasResume(index, uuid)) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        if (uuid == null) return;

        Integer index = getIndex(uuid);
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

    public int size() {
        return size;
    }

    private Integer getIndex(Resume r) {
        return getIndex(r.getUuid());
    }

    private Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return null;
    }

    private boolean hasResume(Integer index, Resume r) {
        return hasResume(index, r.getUuid());
    }

    private boolean hasResume(Integer index, String uuid) {
        if (index == null) {
            System.out.format("[Error] No resume with uuid: %s \n", uuid);
            return false;
        } else {
            return true;
        }
    }
}
