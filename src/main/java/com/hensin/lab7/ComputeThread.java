package com.hensin.lab7;

public class ComputeThread implements Runnable {
    private final SharedData sharedData;

    public ComputeThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (sharedData) {
                // Ожидание данных или снятия флага паузы
                while (!sharedData.isDataReady() || sharedData.isPaused()) {
                    try {
                        // Обязательный вызов wait() для синхронизации
                        sharedData.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Вычисление произведения цифр четырехзначного числа
                int num = sharedData.getNumber();
                int temp = num;
                int product = 1;
                
                while (temp > 0) {
                    product *= (temp % 10);
                    temp /= 10;
                }

                // Передача результата управляющему потоку
                sharedData.setProduct(product);
                System.out.println("[Вычислительный поток] Обработано число: " + num + ", произведение: " + product);

                // Уведомление управляющего потока о готовности результата
                sharedData.notify();
            }
        }
    }
}