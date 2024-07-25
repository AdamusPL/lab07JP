package Apps.Seeker;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import interfaces.IWorld;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SeekerApp implements ISeeker {

    private String seekerName;
    private String clubName;
    private IClub iClub;

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setiClub(IClub iClub) {
        this.iClub = iClub;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public String getClubName() {
        return clubName;
    }

    public IClub getiClub() {
        return iClub;
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        SeekerApp seekerApp = new SeekerApp();
        Registry reg = LocateRegistry.getRegistry("localhost", 1099);
        IOffice iOffice = (IOffice) reg.lookup("OfficeApp");

        Registry regW = LocateRegistry.getRegistry("localhost", 1100);
        IWorld iWorld = (IWorld) regW.lookup("WorldApp");

        SeekerGUI seekerGUI = new SeekerGUI(seekerApp, iOffice, iWorld);
    }

    @Override
    public boolean exploreTask(String sector, String field) throws RemoteException {
        return false;
    }

    @Override
    public String getName() throws RemoteException {
        return seekerName;
    }
}
