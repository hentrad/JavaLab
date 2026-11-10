package com.hensin.lab10;

import com.hensin.LabException;
import com.hensin.lab3.Microbe;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MicrobeServiceImpl extends UnicastRemoteObject implements MicrobeService {

    public MicrobeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public long countBacteria(int time) throws RemoteException, LabException {
        // Переиспользуем логику из lab3 (там уже есть валидация времени)
        Microbe microbe = new Microbe(time);
        return microbe.countBacteria();
    }
}
