package com.hensin.lab2;

import com.hensin.LabException;
import java.util.Scanner;

public class MainArrayOnJava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        try {
            ArrayOnJava ops = new ArrayOnJava();
            ops.inputArray(scanner);

            System.out.println("Исходный массив:");
            ops.printArray();

            System.out.println("числа % 3 == 0: " + ops.countNum());

            ops.sortArray();
            System.out.println("Отсортированный массив:");
            ops.printArray();

        } catch (LabException e) {
            System.out.println("Ошибка lab2: " + e.getMessage());
        }
    }
}