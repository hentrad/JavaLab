package com.hensin.lab10;

import com.hensin.LabException;
import com.hensin.lab3.Microbe;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class MicrobeServiceImpl extends UnicastRemoteObject implements MicrobeService {

    public MicrobeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public long countBacteria(int time) throws RemoteException, LabException {

        try {
            String clientIp = RemoteServer.getClientHost();
            System.out.println("[Сервер] Запрос от клиента с IP: " + clientIp);
        } catch (ServerNotActiveException e) {
            System.out.println("[Сервер] Не удалось получить IP клиента");
        }

        Microbe microbe = new Microbe(time);
        return microbe.countBacteria();
    }
}