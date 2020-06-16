package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int maxSize = 10000;
    private Resume[] storage = new Resume[maxSize];
    private int size = 0;

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
            if (index >= 10000) {
                System.out.format("[Error] Reached a maximum size of %d\n", maxSize);
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;

        int index = getIndex(uuid);
        if (hasResume(index, uuid)) {
            return storage[index];
        }

        return null;
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

    public int size() {
        return size;
    }

    private Integer getIndex(Resume r) {
        return getIndex(r.getUuid());
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasResume(int index, Resume r) {
        return hasResume(index, r.getUuid());
    }

    private boolean hasResume(int index, String uuid) {
        if (index == -1) {
            System.out.format("[Error] No resume with uuid: %s \n", uuid);
            return false;
        } else {
            return true;
        }
    }
}
