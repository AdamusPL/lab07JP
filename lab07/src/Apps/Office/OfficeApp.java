package Apps.Office;

import interfaces.IClub;
import interfaces.IOffice;
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
    List<IClub> iClubs = new ArrayList<>();

    @Override
    public boolean register(IClub ic) throws RemoteException {
        System.out.println("Klub zarejestrowany");
        iClubs.add(ic); //dodanie klubu do listy
        return true;
    }

    @Override
    public boolean unregister(String clubName) throws RemoteException {
        System.out.println("Klub wyrejestrowany");
        for (IClub i: iClubs) {
            if(i.getName().equals(clubName)){ //usunięcie klubu z listy
                iClubs.remove(i);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean permissionRequest(String clubName, String sector) throws RemoteException {
        System.out.println("Zezwolono na przeszukanie sektora "+sector+" klubu "+clubName);
        return false;
    }

    @Override
    public boolean permissionEnd(String clubName, String sector) throws RemoteException {
        System.out.println("Zakończono przeszukiwanie sektora "+sector+" przez klub"+clubName);
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
        OfficeMap officeMap = new OfficeMap();
        OfficeApp officeApp= new OfficeApp(); //przekazanie parametrów do konstruktora ramki
        IOffice server = new OfficeApp();
        IOffice stub = (IOffice) UnicastRemoteObject.exportObject((IOffice)server,0); //rejestracja namiastki urzędu

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("OfficeApp", stub);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                officeApp.startT();
            }
        });
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void startT(){
        t.start();
    }


}
