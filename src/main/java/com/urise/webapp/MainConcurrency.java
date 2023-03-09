package com.urise.webapp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        CountDownLatch latch = new CountDownLatch(1000);
        MainConcurrency main = new MainConcurrency();
          ExecutorService executorService = Executors.newCachedThreadPool();
        //List<Thread> threadList = new ArrayList<>();
        //Запуск процессов и сохранение ин-фы в список
        for (int i = 0; i < 1000; i++) {
            executorService.submit(()->
   /*         Thread threadX = new Thread(() -> { */
                // После выполнения средом своей задачи уменьшаем счётчик
                    {
                        for (int j = 0; j < 100; j++) {
                            main.intCounter();

                        }
                        // уменьшение на 1 замена ожидания через join
                        latch.countDown();
                    }
             );
  /*           threadX.start();
            //         threadList.add(threadX); */
        }
//Ожидание завершения всех открытых процессов 10 секунд
        latch.await(10, TimeUnit.SECONDS);
        //Завершение среда
        executorService.shutdown();
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
