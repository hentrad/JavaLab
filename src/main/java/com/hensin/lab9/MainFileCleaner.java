package com.hensin.lab9;

import com.hensin.LabException;
import java.util.Scanner;

public class MainFileCleaner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        FileCleaner cleaner = new FileCleaner();
        boolean valid = false;

        do {
            System.out.print("\nпуть к папке для очистки: ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty() || path.equalsIgnoreCase("exit")) {
                return;
            }

            System.out.print("Вы уверены, что хотите удалить ВСЁ содержимое папки '" + path + "'? [y/n]: ");
            String confirm = scanner.nextLine().trim();

            if (!confirm.equalsIgnoreCase("y")) {
                continue; 
            }

            try {
                cleaner.deleteAllFiles(path);
                System.out.println("Папка очищена: " + path);
                valid = true;
                
            } catch (LabException e) {
                System.out.println("\n[ОШИБКА] " + e.getMessage());
            }
        } while (!valid);
    }
}