package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Report;

public interface IClub extends Remote {
	// register substitute of Seeker to Club
	boolean register(ISeeker ic) throws RemoteException;

	// unregister substitute of Seeker from Club
	boolean unregister(String seekerName) throws RemoteException;
	
	String getName() throws RemoteException;
	// report discovery (every Artifact)
	boolean report(Report report, String seekerName) throws RemoteException;

}
