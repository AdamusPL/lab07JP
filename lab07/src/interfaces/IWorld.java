package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Artifact;

public interface IWorld extends Remote {

    // ask for content of specific field in sector
    Artifact explore(String seekerName, String sector, String field) throws RemoteException;
}