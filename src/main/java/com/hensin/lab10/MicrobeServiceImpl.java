package com.hensin.lab10;

import com.hensin.LabException;
import com.hensin.lab3.Microbe;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MicrobeServiceImpl extends UnicastRemoteObject implements MicrobeService {

    // Храним статистику: ID клиента -> Количество запросов
    private final Map<String, Integer> clientStats = new ConcurrentHashMap<>();
    private final Map<String, String> clientIps = new ConcurrentHashMap<>();

    private long lastRequestTime = 0;

    public MicrobeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public long countBacteria(int time, String clientId) throws RemoteException, LabException {
        // 1. Получаем IP клиента
        String ip = "Unknown";
        try {
            ip = RemoteServer.getClientHost();
        } catch (ServerNotActiveException ignored) {}

        // 2. Ограничение 10 запросов в секунду (100 мс на запрос)
        applyRateLimit();

        // 3. Обновляем статистику
        clientStats.merge(clientId, 1, Integer::sum);
        clientIps.putIfAbsent(clientId, ip);

        // 4. Выводим статистику в консоль
        printStats();

        // 5. Вычисляем результат
        Microbe microbe = new Microbe(time);
        return microbe.countBacteria();
    }

    private synchronized void applyRateLimit() {
        long now = System.currentTimeMillis();
        long timeSinceLast = now - lastRequestTime;
        
        // Если прошло меньше 100 мс, ждем
        if (timeSinceLast < 100) {
            try {
                Thread.sleep(100 - timeSinceLast);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }

        private synchronized void printStats() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        clientStats.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String id = entry.getKey();
                    int count = entry.getValue();
                    String ip = clientIps.get(id);
                    System.out.printf("[%s] (IP: %s) -> Запросов: %d%n", id, ip, count);
                });
    }
}