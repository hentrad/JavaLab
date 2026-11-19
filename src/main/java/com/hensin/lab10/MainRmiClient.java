package com.hensin.lab10;

import com.hensin.LabException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class MainRmiClient {

    private static volatile boolean isRunning = true;

    public static void main(String[] args) {
        String clientId = "Client-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        System.out.println("Запущен " + clientId + ". Подключение к серверу...");

        Thread inputListener = new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    isRunning = false;
                }
            }
        });
        inputListener.setDaemon(true); 
        inputListener.start();

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            MicrobeService service = (MicrobeService) registry.lookup("MicrobeService");

            Random random = new Random();
            int requestNumber = 1;

            while (isRunning) {
                int time = random.nextInt(31);
                long result = service.countBacteria(time, clientId);

                if (!isRunning) break;

                System.out.println("[" + clientId + "] Запрос #" + requestNumber + 
                        " | t=" + time + " | Бактерий: " + result);
                requestNumber++;

                for (int i = 0; i < 4 && isRunning; i++) {
                    Thread.sleep(50);
                }
            }

        } catch (java.rmi.ConnectException e) {
            System.out.println("[" + clientId + "] Ошибка: Сервер не запущен.");
        } catch (LabException e) {
            System.out.println("[" + clientId + "] Ошибка от сервера: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("[" + clientId + "] Клиент остановлен.");
        } catch (Exception e) {
            System.out.println("[" + clientId + "] Ошибка сети: " + e.getMessage());
        }
    }
}