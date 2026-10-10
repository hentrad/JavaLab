package com.hensin.lab7;

import com.hensin.LabException;
import com.hensin.LabException.Code;

import java.util.Random;

public class ManagerThread implements Runnable {

    private final SharedData exchange;
    private final int iterations;
    private static final int SLEEP_TIME = 500;

    public ManagerThread(SharedData exchange, int iterations) throws LabException {
        if (iterations < 1 || iterations > 10000) {
            throw new LabException(Code.ITERATIONS_OUT_OF_RANGE, iterations);
        }
        this.exchange = exchange;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < iterations; i++) {
                int number = 1000 + random.nextInt(9000);

                System.out.println("\n[" + (i + 1) + "/" + iterations + "]");
                System.out.println("A передано: " + number);
                exchange.putTask(number);

                int result = exchange.takeResult();
                System.out.println("A получено: " + result);
                if (SLEEP_TIME > 0) {
                    exchange.pause();
                    Thread.sleep(SLEEP_TIME);
                    exchange.resume();
                }
            }
            System.out.println("\nA Работа завершена");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}