package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void fillDeletedElement(int index) {
        index++;
        while (index < STORAGE_LIMIT && storage[index] != null){
            storage[index-1] = storage[index];
            storage[index] = null;
            index++;
        }
    }

    @Override
    protected void insertElement(Resume r, int index) {
        index = -index;
        Resume buffer = storage[index-1];
        storage[index-1] = r;
        while (index < STORAGE_LIMIT && buffer != null){
            storage[index-1] = buffer;
            buffer = storage[index];
            index++;
        }
    }
}
