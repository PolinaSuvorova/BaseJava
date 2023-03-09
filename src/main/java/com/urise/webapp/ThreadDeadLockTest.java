package com.urise.webapp;

public class ThreadDeadLockTest {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Поток 1 блокировка lock1 ");
                synchronized (lock2) {
                    System.out.println("Поток 1 блокировка lock1 lock2");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Поток 2 блокировка lock2");
                synchronized (lock1) {
                    System.out.println("Поток 2 блокировка lock2 lock1");
                }
            }
        });
        thread1.start();
        thread2.start();
    }

}
