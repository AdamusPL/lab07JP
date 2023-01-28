package Apps.World;
import interfaces.IWorld;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WorldApp implements IWorld {
    @Override
    public Artifact explore(String seekerName, String sector, String field) throws RemoteException {
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WorldMap worldMap = new WorldMap();
        worldMap.initialize();
        WorldApp worldApp = new WorldApp();
        IWorld server = new WorldApp();
        IWorld stub = (IWorld) UnicastRemoteObject.exportObject((IWorld)server,0); //rejestracja namiastki świata

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
