package com.hensin.lab7;

import java.util.Random;

public class ManagerThread implements Runnable {
    private final SharedData sharedData;

    public ManagerThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int number = 1000 + random.nextInt(9000);

            synchronized (sharedData) {
                while (sharedData.isDataReady() || sharedData.isResultReady()) {
                    try {
                        sharedData.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("\n[Управляющий поток] Передано число: " + number);
                sharedData.setNumber(number);
                sharedData.notify();

                while (!sharedData.isResultReady()) {
                    try {
                        sharedData.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("[Управляющий поток] Получен результат: " + sharedData.getProduct());

                // ВАЖНО: сбрасываем флаг готовности результата,
                // иначе на следующей итерации цикл ожидания заблокирует поток
                sharedData.consumeResult();
                sharedData.notify();
            }

            if (i == 2) {
                System.out.println("\n>>> [Управляющий поток] Приостановка вычислительного процесса на 2 секунды...");
                sharedData.pause();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(">>> [Управляющий поток] Возобновление вычислительного процесса.");
                sharedData.resume();
            }
        }
        System.out.println("\n[Управляющий поток] Работа завершена.");
    }
}