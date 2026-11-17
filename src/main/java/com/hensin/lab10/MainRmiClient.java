package com.hensin.lab10;

import com.hensin.LabException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainRmiClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        scanner.close();
    }

    public static void run(Scanner scanner) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            
            MicrobeService service = (MicrobeService) registry.lookup("MicrobeService");

            boolean valid = false;
            int time = 0;
            do {
                try {
                    System.out.print("момент времени (0..30): ");
                    time = scanner.nextInt();
                    valid = time >= 0 && time <= 30;
                } catch (InputMismatchException e) {
                    scanner.next();
                }
            } while (!valid);

            long result = service.countBacteria(time);
            System.out.println("Количество бактерий (получено с сервера): " + result);

        } catch (java.rmi.ConnectException e) {
            System.out.println("сервер не запущен");
        } catch (LabException e) {
            System.out.println("Ошибка lab3 (от сервера): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка сети: " + e.getMessage());
        }
    }
}