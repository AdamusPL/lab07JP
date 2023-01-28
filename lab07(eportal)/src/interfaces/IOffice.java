package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Report;

public interface IOffice extends Remote {
	
    // rejestracja namiastki Klubu w Urzêdzie
	boolean register(IClub ic) throws RemoteException;
	// wyrejestrowanie namiastki Klubu z Urzêdu
	boolean unregister(String clubName) throws RemoteException;
	// proœba o zezwolenie na prowadzenie prac badawczych w sektorze np. "A1"
	boolean permissionRequest(String clubName, String sector) throws RemoteException;
	// proœba o zamkniêcie prac badawczych w sektorze np. "A1"
	boolean permissionEnd(String clubName, String sector) throws RemoteException;
	
	// raportowanie znalezisk przez Klub (tylko Skarby)
	boolean report(List<Report> reports, String clubName) throws RemoteException;
	
	// pobieranie listy klubów przez poszukiwaczy chc¹cych siê do jakiegoœ klubu zapisaæ
	List<IClub> getClubs() throws RemoteException;
}
