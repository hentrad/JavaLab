package com.hensin.lab10;

import com.hensin.LabException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MicrobeService extends Remote {
    long countBacteria(int time) throws RemoteException, LabException;
}
