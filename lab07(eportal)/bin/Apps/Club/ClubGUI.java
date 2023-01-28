package Apps.Club;

import interfaces.IOffice;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class ClubGUI extends JFrame implements ActionListener {
    ClubApp clubApp;
    IOffice iOffice;
    JPanel simPanel;
    private JPanel contentPane;
    JButton register;
    JButton unregister;
    JButton permissionRequest;
    JButton permissionEnd;
    JTextField clubName;
    JTextField sectorStart;
    JTextField sectorEnd;

    boolean registered=false;

    public ClubGUI(ClubApp clubApp, IOffice iOffice) throws IOException {
        this.clubApp=clubApp;
        this.iOffice=iOffice;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 300, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); //zeby mozna bylo dowolnie ustawiac miejsca panelow w ramce

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        //tu dodaj rzeczy do okienka

        register = new JButton("register()");
        register.setBounds(5,5,180,20);
        register.addActionListener(this);

        clubName=new JTextField("A");
        clubName.setBounds(185,5,50,20);

        unregister = new JButton("unregister()");
        unregister.setBounds(5,25,180,20);
        unregister.addActionListener(this);

        permissionRequest = new JButton("permissionRequest()");
        permissionRequest.setBounds(5,45,180,20);
        permissionRequest.addActionListener(this);

        permissionEnd = new JButton("permissionEnd()");
        permissionEnd.setBounds(5,65,180,20);
        permissionEnd.addActionListener(this);

        sectorStart = new JTextField("A2");
        sectorStart.setBounds(185,45,50,20);

        sectorEnd = new JTextField("A2");
        sectorEnd.setBounds(185,65,50,20);

        simPanel.add(permissionEnd);
        simPanel.add(sectorEnd);
        simPanel.add(permissionRequest);
        simPanel.add(sectorStart);
        simPanel.add(clubName);
        simPanel.add(unregister);
        simPanel.add(register);

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
        setAlwaysOnTop(true);
        setTitle("Klub");
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== register){ //rejestracja
            try {
                if(!registered) {
                    clubApp.clubName = clubName.getText();
                    registered = iOffice.register(clubApp);
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()== unregister){ //wyrejestrowanie
            try {
                if(registered) {
                    registered = iOffice.unregister(clubApp.getName());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()== permissionRequest){ //zezwolenie na przeszukanie
            try {
                if(registered) {
                    iOffice.permissionRequest(clubApp.getName(), sectorStart.getText());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()== permissionEnd){ //zakończenie przeszukiwania
            try {
                if(registered) {
                    iOffice.permissionEnd(clubApp.getName(), sectorEnd.getText());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
