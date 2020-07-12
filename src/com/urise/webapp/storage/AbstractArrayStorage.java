package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    public static final int STORAGE_LIMIT = 10000;

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

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (resume == null || resume.getUuid() == null) return;

        int index = getIndex(resume);
        if (hasResume(index, resume)) {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (resume == null || resume.getUuid() == null) return;

        int index = getIndex(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, index);
            size++;
        }
    }

    public void delete(String uuid) {
        if (uuid == null) return;

        int index = getIndex(uuid);
        if (hasResume(index, uuid)) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected boolean hasResume(int index, Resume resume) {
        return hasResume(index, resume.getUuid());
    }

    protected boolean hasResume(int index, String uuid) {
        boolean hasResume = index != -1;
        if (!hasResume) {
            throw new NotExistStorageException(uuid);
        }

        return true;
    }

    protected Integer getIndex(Resume resume) {
        return getIndex(resume.getUuid());
    }

    protected abstract int getIndex(String uuid);

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);
}