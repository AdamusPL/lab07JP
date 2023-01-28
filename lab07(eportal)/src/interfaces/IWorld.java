package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Artifact;

public interface IWorld extends Remote {

	// zapytanie o zawarto�� danego pola w danym sektorze 
	Artifact explore(String seekerName, String sector, String field) throws RemoteException;
}