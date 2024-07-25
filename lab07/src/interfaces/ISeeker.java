package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISeeker extends Remote {

    // give task to search in specific sector and field f.e. "A1","B10".
    boolean exploreTask(String sector, String field) throws RemoteException;

    String getName() throws RemoteException;
}
