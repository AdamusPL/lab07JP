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
    List<IClub> iClubs = new ArrayList<>();
    Map map = new Map(".");

    @Override
    public boolean register(IClub ic) throws RemoteException {
        System.out.println("Klub "+ic.getName()+" zarejestrowany");
        iClubs.add(ic); //dodanie klubu do listy
        return true;
    }

    @Override
    public boolean unregister(String clubName) throws RemoteException {
        System.out.println("Klub "+clubName+" wyrejestrowany");
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
        boolean clubPut=false;
        for (ArrayList<Field> rowField: map.fieldLabelsArray) {
            for (Field f: rowField) {
                if(f.mapSector.equals(sector)){
                    if(f.occupiedByClubs==2){
                        System.out.println("Brak zezwolenia, tu juz pracuja 2 kluby");
                        return false;
                    }

                    if(!clubPut){
                        f.set(clubName);
                        clubPut=true;
                    }
                    f.occupiedByClubs++;
                }
            }
        }
        System.out.println("Zezwolono na przeszukanie sektora "+sector+" klubu "+clubName);
        return true;
    }

    @Override
    public boolean permissionEnd(String clubName, String sector) throws RemoteException {
        System.out.println("Zakończono przeszukiwanie sektora "+sector+" przez klub "+clubName);
        boolean clubPut=true;
        for (ArrayList<Field> rowField: map.fieldLabelsArray) {
            for (Field f: rowField) {
                if(f.mapSector.equals(sector)){
                    if(f.occupiedByClubs==0){
                        System.out.println("Tu nie ma żadnego klubu");
                        return true;
                    }

                    if(clubPut){
                        f.unSet();
                        clubPut=false;
                    }

                    f.occupiedByClubs--;
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
        OfficeApp officeApp= new OfficeApp(); //przekazanie parametrów do konstruktora ramki
        OfficeMap officeMap = new OfficeMap(officeApp.map);
        IOffice stub = (IOffice) UnicastRemoteObject.exportObject((IOffice)officeApp,0); //rejestracja namiastki urzędu

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
