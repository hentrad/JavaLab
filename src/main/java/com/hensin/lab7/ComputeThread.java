package com.hensin.lab7;

public class ComputeThread implements Runnable {

    private final SharedData exchange;

    public ComputeThread(SharedData exchange) {
        this.exchange = exchange;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int number = exchange.takeTask();

                int product = 1;
                int temp = number;
                while (temp > 0) {
                    product *= (temp % 10);
                    temp /= 10;
                }

                System.out.println("B: Число " + number + "; результат: " + product);

                exchange.putResult(product);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("B: Завершён.");
    }
}