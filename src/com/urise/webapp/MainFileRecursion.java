package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class MainFileRecursion {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\ptatara\\StartJava");
        print(file, 0);
    }

    public static void print(File file, int level) {

        System.out.println( "*".repeat(level + 1 ) +    file.getName());
        for (File currentFile : Objects.requireNonNull(file.listFiles())) {
            if (currentFile.isDirectory()) {
                print(currentFile, level + 1);
            } else {
                System.out.println(" ".repeat(level + 1)  + currentFile.getName());
            }
        }
    }

}
