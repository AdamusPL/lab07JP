package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISeeker extends Remote {

	// zlecenie poszukiwania w danym sektorze i na danym polu, np. "A1","B10".
	boolean exploreTask(String sector, String field) throws RemoteException;

	String getName() throws RemoteException;
}
