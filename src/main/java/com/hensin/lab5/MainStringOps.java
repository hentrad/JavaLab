package com.hensin.lab5;

import com.hensin.LabException;
import java.util.Scanner;

public class MainStringOps {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        StringOps ops = new StringOps();

        try {
            System.out.print("x: ");
            String x = scanner.nextLine();

            String y;
            boolean valid;
            do {
                System.out.print("y: ");
                y = scanner.nextLine();
                valid = y.length() == 1;
            } while (!valid);

            String z;
            do {
                System.out.print("z: ");
                z = scanner.nextLine();
                valid = z.length() == 1;
            } while (!valid);

            System.out.println("Результат: " + ops.insertAfter(x, y, z));

        } catch (LabException e) {
            System.out.println("Ошибка lab5: " + e.getMessage());
        }
    }
}