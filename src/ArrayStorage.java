import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        if (r.uuid == null) return;
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (r.uuid.equals(storage[i] == null ? null : storage[i].uuid)) return;
        }
        storage[size] = r;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;
        for (int i = 0; i < this.size(); i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        if (uuid == null) return;
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                for (int k = i; k < size; k++) {
                    storage[k] = storage[k + 1];
                }
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
        int size;
        for (size = 0; size < storage.length; size++) {
            if (storage[size] == null) break;
        }
        return size;
    }
}
