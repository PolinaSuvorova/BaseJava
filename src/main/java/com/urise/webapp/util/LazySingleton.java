package com.urise.webapp.util;

public class LazySingleton {
    // Замена кода ниже, который гарантирует что в последующих инициализациях
    // обращение к инстанции будет актуально для всех процессов
    // т.к. инстанция становиться доступна только после инициализации всех Final полей.
    // многопоточность будет обеспечена внутренним механизмом загрузки Java
    // которая также обеспечивает доступ к одному реcурсу всех потоков
    private static class LazySingletonHolder {
        private static final LazySingleton SINGLETON = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.SINGLETON;
    }


    // добавляем volatile для того, чтобы синхронизировать инициализацию атрибутов класса
    // если они были изменены в предыдущих синхронных процессах (что-то вроде глобальной
    // переменной в рамках нескольких потоков )
    // если убрать synchronized и оставить volatile получим актуальное значение на момент запуска
    // потока. (есть вероятность прочитать в один момент одно значение)
    //    volatile private static LazySingleton singleton = new LazySingleton();
    // добавляем synchronized чтобы в отдельных потоках не создавались новые инстанции
    // переносим synchronized в то место где должна соблюдаться очередь, чтобы оптимизировать
    // скорость выполнения (отношение happend before)
    //
    //public static LazySingleton getInstance(){
    // ленивая инициализация
    //   if ( singleton == null ) {
    //       synchronized (LazySingleton.class) {
    //           if ( singleton == null ) {
    //               singleton = new LazySingleton();
    //           }
    //       }
    //   }
    //   return singleton;
    // }
}
