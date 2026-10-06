package com.hensin.lab7;

import java.util.Scanner;

public class MainThreads {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        int iterations = readIterations(scanner);

        SharedData sharedData = new SharedData();

        ComputeThread computeRunnable = new ComputeThread(sharedData);
        ManagerThread managerRunnable = new ManagerThread(sharedData, iterations);

        Thread computeThread = new Thread(computeRunnable, "ComputeThread");
        Thread managerThread = new Thread(managerRunnable, "ManagerThread");

        computeThread.start();
        managerThread.start();

        try {
            managerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        computeThread.interrupt();
    }

    private static int readIterations(Scanner scanner) {
    boolean valid;
    int value = 0;
    do {
        System.out.print("Количество итераций ["
                + ManagerThread.MIN_ITERATIONS + ".."
                + ManagerThread.MAX_ITERATIONS + "]: ");
        try {
            String line = scanner.nextLine().trim();
            value = Integer.parseInt(line);
            valid = value >= ManagerThread.MIN_ITERATIONS && value <= ManagerThread.MAX_ITERATIONS;
        } catch (NumberFormatException e) {
            valid = false;
        }
    } while (!valid);
    return value;
    }
}