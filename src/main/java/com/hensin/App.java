package com.hensin;

import com.hensin.lab1.MainBox;
import com.hensin.lab2.MainArrayOnJava;
import com.hensin.lab3.MainMicrobe;
import com.hensin.lab5.MainStringOps;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public interface LabWork {
        String getTitle();
        void run(Scanner scanner);
    }

    public static class BoxLab implements LabWork {
        @Override public String getTitle() { return "lab1 (Box)"; }
        @Override public void run(Scanner scanner) { MainBox.run(scanner); }
    }

    public static class ArrayOnJavaLab implements LabWork {
        @Override public String getTitle() { return "lab2 (ArrayOnJava)"; }
        @Override public void run(Scanner scanner) { MainArrayOnJava.run(scanner); }
    }

    public static class MicrobeLab implements LabWork {
        @Override public String getTitle() { return "lab3 (Microbe)"; }
        @Override public void run(Scanner scanner) { MainMicrobe.run(scanner); }
    }

    public static class StringOpsLab implements LabWork {
        @Override public String getTitle() { return "lab5 (StringOps)"; }
        @Override public void run(Scanner scanner) { MainStringOps.run(scanner); }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        List<LabWork> labs = new ArrayList<>();
        labs.add(new BoxLab());
        labs.add(new ArrayOnJavaLab());
        labs.add(new MicrobeLab());
        labs.add(new StringOpsLab());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            System.out.println("Пакет:");
            for (int i = 0; i < labs.size(); i++) {
                System.out.println((i + 1) + ". " + labs.get(i).getTitle());
            }
            System.out.println("0. Выход");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
                continue;
            }

            if (choice == 0) break;
            if (choice < 1 || choice > labs.size()) {
                continue;
            }

            clearConsole();
            labs.get(choice - 1).run(scanner);
            
            System.out.println("\nEnter...");
            scanner.nextLine();
            scanner.nextLine();
        }

        scanner.close();
        System.out.println("Выход");
    }
}