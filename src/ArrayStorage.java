import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int RESUME_LIMIT = 10000;
    Resume[] storage = new Resume[RESUME_LIMIT];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (size >= RESUME_LIMIT) {
            System.out.println("Хранилище переполненно");
        }
        int index = findPosition(r.toString());
        if (index < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.printf("Резюме с uuid %s уже существует%n", r);
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = findPosition(uuid);
        if (index < 0) {
            System.out.printf("Резюме с uuid %s не найдено%n", uuid);
        }
        size--;
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    private int findPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
