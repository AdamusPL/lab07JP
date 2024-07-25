package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Report;

public interface IOffice extends Remote {

    // register substitute of Club to Office
    boolean register(IClub ic) throws RemoteException;

    // unregister substitute of Club from Office
    boolean unregister(String clubName) throws RemoteException;

    // request to make research work in sector f.e. "A1"
    boolean permissionRequest(String clubName, String sector) throws RemoteException;

    // request to end research work in sector f.e. "A1"
    boolean permissionEnd(String clubName, String sector) throws RemoteException;

    // reporting discoveries found by Club (only Treasures)
    boolean report(List<Report> reports, String clubName) throws RemoteException;

    // download club list by seekers who want to sign up to club
    List<IClub> getClubs() throws RemoteException;
}
