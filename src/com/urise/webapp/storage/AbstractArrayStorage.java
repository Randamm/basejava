package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;

        int index = getIndex(uuid);
        if (hasResume(index, uuid)) {
            return storage[index];
        }

        return null;
    }

    protected abstract int getIndex(String uuid);

    protected boolean hasResume(int index, Resume r) {
        return hasResume(index, r.getUuid());
    }

    protected boolean hasResume(int index, String uuid) {
        boolean hasResume = index == -1;
        if (hasResume) {
            System.out.format("[Error] No resume with uuid: %s \n", uuid);
        }

        return hasResume;
    }
}