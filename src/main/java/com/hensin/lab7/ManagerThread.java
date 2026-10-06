package com.hensin.lab7;

import java.util.Random;

public class ManagerThread implements Runnable {

    public static final int MIN_ITERATIONS = 1;
    public static final int MAX_ITERATIONS = 10000;

    private static final long PAUSE_MS = 2000L;

    private final SharedData sharedData;
    private final int iterations;

    public ManagerThread(SharedData sharedData, int iterations) {
        if (iterations < MIN_ITERATIONS || iterations > MAX_ITERATIONS) {
            throw new IllegalArgumentException(
                    "Количество итераций должно быть в ["
                            + MIN_ITERATIONS + ".." + MAX_ITERATIONS + "], "
                            + "получено: " + iterations);
        }
        this.sharedData = sharedData;
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    @Override
    public void run() {
        Random random = new Random();

        final int pauseAfter = iterations / 2;

        for (int i = 0; i < iterations; i++) {
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

                System.out.println("\nA: (" + (i + 1) + "/" + iterations 
                                            + ") Передано число: " + number);
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

                System.out.println("A: Получен результат: " + sharedData.getProduct());

                sharedData.consumeResult();
                sharedData.notify();
            }

            if (i == pauseAfter) {
                System.out.println("\nA: Приостановка вычислительного процесса на " 
                                    + (PAUSE_MS / 1000) + " секунды...");
                sharedData.pause();
                try {
                    Thread.sleep(PAUSE_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    sharedData.resume();
                    return;
                }
                System.out.println("A: Возобновление вычислительного процесса.");
                sharedData.resume();
            }
        }

        System.out.println("\nA: Работа завершена.");
    }
}