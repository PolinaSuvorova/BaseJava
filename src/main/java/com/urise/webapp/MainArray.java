package com.urise.webapp;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) throws StorageException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullName | delete" +
                    " uuid | get uuid | clear | exit | update uuid fullname): ");
            try {
                String[] params = reader.readLine().trim().toLowerCase().split(" ");
                if (params.length < 1 || params.length > 3) {
                    System.out.println("Неверная команда.");
                    continue;
                } else if (!params[0].intern().equals("update") && params.length > 2) {
                    System.out.println("Неверная команда.");
                    continue;
                }

                String uuid = null;
                String fullName = null;

                if (params.length != 1) {
                    uuid = params[1].intern();
                }
                if (params.length == 3) {
                    fullName = params[2].intern();
                }
                switch (params[0]) {
                    case "list":
                        printAll();
                        break;
                    case "size":
                        System.out.println(ARRAY_STORAGE.size());
                        break;
                    case "save":
                        r = new Resume(uuid, fullName);
                        ARRAY_STORAGE.save(r);
                        printAll();
                        break;
                    case "delete":
                        ARRAY_STORAGE.delete(uuid);
                        printAll();
                        break;
                    case "update":
                        if (fullName != null) {
                            Resume resume = new Resume(uuid, fullName);
                            ARRAY_STORAGE.update(uuid, resume);
                        }
                        printAll();
                        break;
                    case "get":
                        System.out.println(ARRAY_STORAGE.get(uuid));
                        break;
                    case "clear":
                        ARRAY_STORAGE.clear();
                        printAll();
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Неверная команда.");
                        break;
                }
            } catch (IOException errIO) {
                System.out.println(errIO.getMessage());
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAllSorted().toArray(new Resume[0]);
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r.getUuid());
            }
        }
        System.out.println("----------------------------");
    }
}
