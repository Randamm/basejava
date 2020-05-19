import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        if (uuid == null) return;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                for (int k = i; k < size; k++) {
                    storage[k] = storage[k + 1];
                }
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, this.size());
    }

    int size() {
        return size;
    }
}
