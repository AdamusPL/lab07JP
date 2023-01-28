package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Report;

public interface IClub extends Remote {
	// rejestracja namiastki Poszukiwacza w Klubie
	boolean register(ISeeker ic) throws RemoteException;

	// wyrejestrowanie namiastki Poszukiwacza z Klubu
	boolean unregister(String seekerName) throws RemoteException;
	
	String getName() throws RemoteException;
	// raportowanie znalezisk (ka¿dy Artefakt)
	boolean report(Report report, String seekerName) throws RemoteException;

}
