package com.hensin.lab10;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1100);
            
            MicrobeService service = new MicrobeServiceImpl();
            registry.rebind("MicrobeService", service);
            
            System.out.println("Ожидание клиента...");
        } catch (Exception e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


