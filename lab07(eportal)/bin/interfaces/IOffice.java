package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Report;

public interface IOffice extends Remote {
	
    // rejestracja namiastki Klubu w Urz�dzie
	boolean register(IClub ic) throws RemoteException;
	// wyrejestrowanie namiastki Klubu z Urz�du
	boolean unregister(String clubName) throws RemoteException;
	// pro�ba o zezwolenie na prowadzenie prac badawczych w sektorze np. "A1"
	boolean permissionRequest(String clubName, String sector) throws RemoteException;
	// pro�ba o zamkni�cie prac badawczych w sektorze np. "A1"
	boolean permissionEnd(String clubName, String sector) throws RemoteException;
	
	// raportowanie znalezisk przez Klub (tylko Skarby)
	boolean report(List<Report> reports, String clubName) throws RemoteException;
	
	// pobieranie listy klub�w przez poszukiwaczy chc�cych si� do jakiego� klubu zapisa�
	List<IClub> getClubs() throws RemoteException;
}
