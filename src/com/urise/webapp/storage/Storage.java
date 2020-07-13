package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {
    int STORAGE_LIMIT = 10000;

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
