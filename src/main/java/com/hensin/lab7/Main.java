package com.hensin.lab7;

public class Main {
    public static void main(String[] args) {
        // Создание общего объекта для разделяемых данных
        SharedData sharedData = new SharedData();

        // Создание экземпляров Runnable
        ComputeThread computeRunnable = new ComputeThread(sharedData);
        ManagerThread managerRunnable = new ManagerThread(sharedData);

        // Создание и запуск потоков (подпроцессов)
        Thread computeThread = new Thread(computeRunnable, "ComputeThread");
        Thread managerThread = new Thread(managerRunnable, "ManagerThread");

        System.out.println("Запуск подпроцессов...");
        computeThread.start();
        managerThread.start();

        // Ожидание завершения управляющего потока
        try {
            managerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Завершение вычислительного потока
        computeThread.interrupt();
        System.out.println("Главный поток завершен.");
    }
}