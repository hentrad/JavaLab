package com.hensin.lab7;

public class ComputeThread implements Runnable {
    private final SharedData sharedData;

    public ComputeThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public static int productOfDigits(int num) {
        int temp = num;
        int product = 1;
        while (temp > 0) {
            product *= (temp % 10);
            temp /= 10;
        }
        return product;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (sharedData) {
                while (!sharedData.isDataReady() || sharedData.isPaused()) {
                    try {
                        sharedData.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                int num = sharedData.getNumber();
                int product = productOfDigits(num);

                sharedData.setProduct(product);
                System.out.println("B: Обработано число: " + num
                        + ", произведение: " + product);

                sharedData.notify();
            }
        }
    }
}