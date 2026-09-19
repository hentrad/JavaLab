package com.hensin.lab1;

import com.hensin.LabException;
import java.util.Scanner;

public class MainBox {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        Box p = new Box(0);

        try {
            p.inputBox(scanner);

            System.out.println("\n[ 9 операторов ]");
            System.out.println(p.getBall() + " + 5 = " + p.add(5));
            System.out.println(p.getBall() + " / 3 = " + p.add1(3));
            System.out.println(p.getBall() + " *= 5 = " + p.add2(5));
            System.out.println(p.getBall() + " >> 2 = " + p.add3(2));
            System.out.println("a >>>= ball: " + p.add4(3));
            System.out.println(p.getBall() + " >= 5 = " + p.add5(5));
            System.out.println(p.isAlive() + " | true = " + p.add6(true));
            System.out.println(p.isAlive() + " && false = " + p.add7(false));
            System.out.println(p.isAlive() + " != false = " + p.add8(false));

        } catch (LabException e) {
            System.out.println("Ошибка lab1: " + e.getMessage());
        }
    }
}