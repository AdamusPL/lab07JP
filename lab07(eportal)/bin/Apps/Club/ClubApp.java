package Apps.Club;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import model.Report;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ClubApp implements IClub, Serializable {

//    ArrayList<Report> reports = new ArrayList<>();
    String clubName;

    @Override
    public boolean register(ISeeker ic) throws RemoteException {
        System.out.println("Poszukiwacz "+ic.getName()+" zarejestrowany");
        return true;
    }

    @Override
    public boolean unregister(String clubName) throws RemoteException {
        System.out.println("Poszukiwacz wyrejestrowany");
        return false;
    }

    @Override
    public String getName() throws RemoteException {
        return clubName;
    }

    @Override
    public boolean report(Report report, String seekerName) throws RemoteException {
        return false;
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        ClubApp clubApp = new ClubApp();
        Registry reg = LocateRegistry.getRegistry("localhost",1099);
        IOffice iOffice = (IOffice) reg.lookup("OfficeApp");
        ClubGUI clubGUI = new ClubGUI(clubApp, iOffice);
    }
}
