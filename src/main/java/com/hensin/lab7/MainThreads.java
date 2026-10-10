package com.hensin.lab7;

import com.hensin.LabException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainThreads {

    private static final int MIN_ITER = 1;
    private static final int MAX_ITER = 10000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        try {
            int iterations = inputIterations(scanner);

            SharedData exchange = new SharedData();
            ComputeThread compute = new ComputeThread(exchange);
            ManagerThread manager = new ManagerThread(exchange, iterations);

            Thread computeT = new Thread(compute, "ComputeThread");
            Thread managerT = new Thread(manager, "ManagerThread");

            computeT.start();
            managerT.start();

            managerT.join();
            computeT.interrupt();
            computeT.join(2000);

            System.out.println("\nГлавный поток завершён.");

        } catch (LabException e) {
            System.out.println("Ошибка lab7: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int inputIterations(Scanner scanner) {
        int iterations = MIN_ITER;
        boolean valid;
        do {
            try {
                System.out.print("итераций [" + MIN_ITER + ".." + MAX_ITER + "]: ");
                iterations = scanner.nextInt();
                valid = iterations >= MIN_ITER && iterations <= MAX_ITER;
            } catch (InputMismatchException e) {
                scanner.next();
                valid = false;
            }
        } while (!valid);
        return iterations;
    }
}