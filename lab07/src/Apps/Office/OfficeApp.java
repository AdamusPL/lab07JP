package Apps.Office;

import interfaces.IClub;
import interfaces.IOffice;
import model.Field;
import model.Map;
import model.Report;

import javax.swing.*;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class OfficeApp implements IOffice {
    private List<IClub> iClubs;
    private Map map;

    @Override
    public boolean register(IClub ic) throws RemoteException {
        System.out.println("Club registered");
        iClubs.add(ic); //add club to list
        return true;
    }

    @Override
    public boolean unregister(String clubName) throws RemoteException {
        System.out.println("Club unregistered");
        for (IClub i : iClubs) {
            if (i.getName().equals(clubName)) { //remove club from list
                iClubs.remove(i);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean permissionRequest(String clubName, String sector) throws RemoteException {
        boolean clubPut = false;
        for (ArrayList<Field> rowField : map.getFieldLabelsArray()) {
            for (Field f : rowField) {
                if (f.getMapSector().equals(sector)) {
                    if (f.getOccupiedByClubs() == 2) {
                        System.out.println("No permission, 2 clubs already work here.");
                        return false;
                    }

                    if (!clubPut) {
                        f.set(clubName);
                        clubPut = true;
                    }
                    f.setOccupiedByClubs(f.getOccupiedByClubs() + 1);
                }
            }
        }
        System.out.println("Gained permission for research in sector: " + sector + " by club: " + clubName);
        return true;
    }

    @Override
    public boolean permissionEnd(String clubName, String sector) throws RemoteException {
        System.out.println("Ended research in sector: " + sector + " by club: " + clubName);
        boolean clubPut = true;
        for (ArrayList<Field> rowField : map.getFieldLabelsArray()) {
            for (Field f : rowField) {
                if (f.getMapSector().equals(sector)) {
                    if (f.getOccupiedByClubs() == 0) {
                        System.out.println("No club researches here.");
                        return true;
                    }

                    if (clubPut) {
                        f.unSet();
                        clubPut = false;
                    }

                    f.setOccupiedByClubs(f.getOccupiedByClubs() - 1);
                }
            }
        }
        return false;
    }

    @Override
    public boolean report(List<Report> reports, String clubName) throws RemoteException {
        return false;
    }

    @Override
    public List<IClub> getClubs() throws RemoteException {
        return iClubs;
    }

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        OfficeApp officeApp = new OfficeApp();
        officeApp.map = new Map(".");
        officeApp.iClubs = new ArrayList<>();

        OfficeMap officeMap = new OfficeMap(officeApp.map);
        IOffice stub = (IOffice) UnicastRemoteObject.exportObject((IOffice) officeApp, 0); //office substitute registration

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("OfficeApp", stub);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                officeApp.startT();
            }
        });
    }

    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    private void startT() {
        t.start();
    }


}
