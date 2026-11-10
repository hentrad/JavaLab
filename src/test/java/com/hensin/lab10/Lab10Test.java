package com.hensin.lab10;

import com.hensin.LabException;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.*;

public class Lab10Test {

    private static final int TEST_PORT = 2099;
    private static final String SERVICE_NAME = "MicrobeService";

    // Вспомогательный метод для запуска сервера в рамках теста
    private Registry startServer() throws Exception {
        Registry registry;
        try {
            // Если реестр уже создан другим тестом, просто подключаемся
            registry = LocateRegistry.getRegistry(TEST_PORT);
            registry.list(); 
        } catch (RemoteException e) {
            // Если не запущен, создаем
            registry = LocateRegistry.createRegistry(TEST_PORT);
        }
        registry.rebind(SERVICE_NAME, new MicrobeServiceImpl());
        return registry;
    }

    @Test
    public void testRemoteValidData() throws Exception {
        Registry registry = startServer();
        MicrobeService service = (MicrobeService) registry.lookup(SERVICE_NAME);

        // Тестируем старую логику через сеть
        assertEquals(1L, service.countBacteria(0));
        assertEquals(2L, service.countBacteria(1));
        assertEquals(32768L, service.countBacteria(15));
        assertEquals(1073741824L, service.countBacteria(30));
    }

    @Test
    public void testRemoteInvalidData() throws Exception {
        Registry registry = startServer();
        MicrobeService service = (MicrobeService) registry.lookup(SERVICE_NAME);

        // Сервер должен пробросить LabException обратно клиенту по сети
        Exception ex1 = assertThrows(Exception.class, () -> service.countBacteria(-1));
        assertTrue(ex1 instanceof LabException);
        
        Exception ex2 = assertThrows(Exception.class, () -> service.countBacteria(31));
        assertTrue(ex2 instanceof LabException);
    }

    @Test
    public void testRemoteLookupWrongName() throws Exception {
        Registry registry = startServer();
        // Попытка получить сервис с несуществующим именем
        assertThrows(Exception.class, () -> {
            registry.lookup("WrongName");
        });
    }

    @Test
    public void testRemoteConnectionFailure() {
        // Попытка подключиться к порту, где точно нет сервера
        assertThrows(Exception.class, () -> {
            Registry badRegistry = LocateRegistry.getRegistry("localhost", 9999);
            badRegistry.lookup(SERVICE_NAME);
        });
    }
}
