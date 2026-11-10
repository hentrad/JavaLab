package com.hensin.lab10;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            // Создаем реестр RMI на стандартном порту 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Создаем экземпляр сервиса и привязываем его к имени
            MicrobeService service = new MicrobeServiceImpl();
            registry.rebind("MicrobeService", service);
            
            System.out.println("RMI-сервер запущен. Ожидание клиентов...");
        } catch (Exception e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
