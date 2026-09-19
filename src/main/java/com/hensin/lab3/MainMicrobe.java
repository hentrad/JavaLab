package com.hensin.lab3;

import com.hensin.LabException;
import java.util.Scanner;

public class MainMicrobe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        try {
            Microbe ops = new Microbe();
            ops.inputTime(scanner);

            System.out.println("Количество бактерий: " + ops.countBacteria());

        } catch (LabException e) {
            System.out.println("Ошибка lab3: " + e.getMessage());
        }
    }
}