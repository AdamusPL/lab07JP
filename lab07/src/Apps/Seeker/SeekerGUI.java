package Apps.Seeker;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import interfaces.IWorld;
import model.Report;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SeekerGUI extends JFrame implements ActionListener {
    SeekerApp seekerApp;
    IOffice iOffice;
    IWorld iWorld;
    JPanel simPanel;
    private JPanel contentPane;
    JButton getClubs;
    JButton register;
    JButton unregister;
    JButton report;

    JTextField seekerName;
    JTextField clubName;

    public SeekerGUI(SeekerApp seekerApp, IOffice iOffice, IWorld iWorld) throws IOException {
        this.seekerApp=seekerApp;
        this.iOffice=iOffice;
        this.iWorld=iWorld;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 300, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); //zeby mozna bylo dowolnie ustawiac miejsca panelow w ramce

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        //tu dodaj rzeczy do okienka

        getClubs = new JButton("getClubs()");
        getClubs.setBounds(5,5,150,20);
        getClubs.addActionListener(this);

        register = new JButton("register()");
        register.setBounds(5,35,150,20);
        register.addActionListener(this);

        seekerName=new JTextField();
        seekerName.setBounds(155,35,50,20);

        clubName=new JTextField();
        clubName.setBounds(205,35,50,20);

        unregister = new JButton("unregister()");
        unregister.setBounds(5,55,150,20);
        unregister.addActionListener(this);

        report = new JButton("report()");
        report.setBounds(5,75,150,20);
        report.addActionListener(this);

        simPanel.add(clubName);
        simPanel.add(seekerName);
        simPanel.add(unregister);
        simPanel.add(register);
        simPanel.add(getClubs);

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
        setAlwaysOnTop(true);
        setTitle("Mapa świata");
        setVisible(true);
    }

    List<IClub> iClubs;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== getClubs){
            try {
                iClubs = iOffice.getClubs();
                for (IClub i: iClubs) {
                    System.out.println(i.getName());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()== register){ //jak skontaktować się z IClubem jeśli on nie ma rejestru?!?!
            try {
                seekerApp.seekerName=seekerName.getText();
                seekerApp.clubName=clubName.getText();
                for (IClub i: iClubs) {
                    if(i.getName().equals(seekerApp.clubName)){
                        i.register(seekerApp);
                        seekerApp.iClub=i;
                        break;
                    }
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()== unregister){
            try {
                seekerApp.iClub.unregister(seekerApp.seekerName);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
