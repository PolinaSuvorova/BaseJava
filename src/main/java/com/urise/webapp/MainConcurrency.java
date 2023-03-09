package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    // Счётчик потоков
    private static int counter;

    public static void main(String[] args) throws InterruptedException {


        // Вывод Имени основного потока
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread() {
            // Ввод запущенного потока
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                System.out.println(getName());
            }
        };
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState()
                );
            }
        });
        thread1.start();
        System.out.println(thread.getState());
        MainConcurrency main = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>();
        //Запуск процессов и сохранение ин-фы в список
        for (int i = 0; i < 1000; i++) {
            Thread threadX = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    main.intCounter();
                }
            });
            threadX.start();
            threadList.add(threadX);
        }
        //Ожидание завершения всех открытых процессов
        threadList.forEach(thread2 -> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counter);

    }

    private void intCounter() {
        double a = Math.sin(13.);
        // Установка синхронизации потоков. Установка в очередь
        synchronized (this) {
            counter++;
        }
    }
}
