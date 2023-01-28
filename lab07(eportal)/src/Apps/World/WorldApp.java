/**
 * @author Adam Czekalski
 * dir /b /s *.java > sources.txt <- przekopiowanie wszystkich ścieżek do pliku txt
 * javac -d bin --module-path (sciezka do modulu) @sources.txt <- kompilacja
 * jar cvf lab07pop.jar -C bin . <- stworzenie jara
 * java -p (ścieżka do modułu) -m (nazwa modułu)/(ścieżka do klasy głównej) <- odpalenie jara
 */

package Apps.World;
import interfaces.IWorld;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WorldApp implements IWorld {

    Map map = new Map(".");
    ArrayList<Artifact> artifacts = new ArrayList<>();

    @Override
    public Artifact explore(String seekerName, String sector, String field) throws RemoteException {
        for (ArrayList<Field> rowField: map.fieldLabelsArray){
            for (Field f: rowField) {
                if(f.mapSector.equals(sector) && f.fieldSector.equals(field)){
                    if(f.isOccupied){
                        for (Artifact a: artifacts) {
                            if(a.getFieldSector().equals(field) && a.getMapSector().equals(sector)){
                                System.out.println("Znaleziono "+ a +" skarb");
                                return a;
                            }
                        }
                    }
                    else{
                        System.out.println("Tu nic nie ma!");
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException{
        WorldApp worldApp = new WorldApp();
        worldApp.map = new Map(".");

        WorldMap worldMap = new WorldMap(worldApp.map,worldApp.artifacts);

        IWorld stub = (IWorld) UnicastRemoteObject.exportObject((IWorld)worldApp,0); //rejestracja namiastki świata

        Registry registry = LocateRegistry.createRegistry(1100);
        registry.rebind("WorldApp", stub);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                worldApp.startT();
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
