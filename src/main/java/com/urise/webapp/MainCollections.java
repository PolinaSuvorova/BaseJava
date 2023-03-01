package com.urise.webapp;

import com.urise.webapp.exception.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final Resume RESUME_1 = new Resume(UUID_1,"fullname1");
    private static final Resume RESUME_2 = new Resume(UUID_2,"fullname2");
    private static final Resume RESUME_3 = new Resume(UUID_3,"fullname3");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3 );

        for (Resume r : collection) {
            System.out.println(r);
        }

        System.out.println(collection);

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection);
        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);
        // неправильно (выбор ключа и поиск по ключу)
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        // правильно (сразу выбор по ключу)
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

    }
}