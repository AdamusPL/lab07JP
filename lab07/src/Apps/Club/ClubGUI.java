package Apps.Club;

import interfaces.IOffice;
import interfaces.ISeeker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClubGUI extends JFrame implements ActionListener {
    private ArrayList<ISeeker> seekers;
    private int working;
    private ClubApp clubApp;
    private IOffice iOffice;
    private JPanel simPanel;
    private JPanel contentPane;
    private JButton register;
    private JButton unregister;
    private JButton permissionRequest;
    private JButton permissionEnd;
    private JTextField clubName;
    private JTextField sectorStart;
    private JTextField sectorEnd;
    private JLabel assigned;
    private JLabel let;
    private JLabel let1;
    private JLabel let2;
    private JTextField sector;
    private JTextField field;
    private JButton exploreTask;

    private boolean registered;
    private boolean permission;

    public ClubGUI(ClubApp clubApp, IOffice iOffice, ArrayList<ISeeker> seekers) {
        this.clubApp = clubApp;
        this.iOffice = iOffice;
        this.seekers = seekers;
        this.registered = false;
        this.permission = false;
        this.working = 0;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 300, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        register = new JButton("register()");
        register.setBounds(5, 5, 180, 20);
        register.addActionListener(this);

        clubName = new JTextField("A");
        clubName.setBounds(185, 5, 50, 20);

        unregister = new JButton("unregister()");
        unregister.setBounds(5, 25, 180, 20);
        unregister.addActionListener(this);

        permissionRequest = new JButton("permissionRequest()");
        permissionRequest.setBounds(5, 45, 180, 20);
        permissionRequest.addActionListener(this);

        permissionEnd = new JButton("permissionEnd()");
        permissionEnd.setBounds(5, 65, 180, 20);
        permissionEnd.addActionListener(this);

        sectorStart = new JTextField("A2");
        sectorStart.setBounds(185, 45, 50, 20);

        sectorEnd = new JTextField("A2");
        sectorEnd.setBounds(185, 65, 50, 20);

        exploreTask = new JButton("exploreTask()");
        exploreTask.setBounds(5, 90, 180, 20);
        exploreTask.addActionListener(this);

        sector = new JTextField("A2");
        sector.setBounds(185, 90, 50, 20);

        field = new JTextField("A2");
        field.setBounds(235, 90, 50, 20);

        assigned = new JLabel("Club is not registered");
        assigned.setBounds(5, 130, 230, 20);

        let = new JLabel("Permissions:");
        let.setBounds(5, 150, 230, 20);

        let1 = new JLabel();
        let1.setBounds(5, 170, 230, 20);

        let2 = new JLabel();
        let2.setBounds(5, 190, 230, 20);


        simPanel.add(permissionRequest);
        simPanel.add(sectorStart);
        simPanel.add(clubName);
        simPanel.add(unregister);
        simPanel.add(register);
        simPanel.add(let1);
        simPanel.add(let2);
        simPanel.add(let);
        simPanel.add(assigned);
        simPanel.add(sector);
        simPanel.add(field);
        simPanel.add(exploreTask);
        simPanel.add(permissionEnd);
        simPanel.add(sectorEnd);
        simPanel.add(permissionRequest);
        simPanel.add(sectorStart);
        simPanel.add(clubName);
        simPanel.add(unregister);
        simPanel.add(register);

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Club");
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) { //register
            try {
                if (!registered) {
                    clubApp.setClubName(clubName.getText());
                    registered = iOffice.register(clubApp);
                    assigned.setText("Club " + clubName.getText() + " registered");
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == unregister) { //unregister
            try {
                if (registered) {
                    registered = iOffice.unregister(clubApp.getName());
                    assigned.setText("Club is not registered");
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == permissionRequest) { //permission for research
            try {
                if (registered) {
                    if (working < 2) {
                        working++;
                        iOffice.permissionRequest(clubApp.getName(), sectorStart.getText());
                        if (let1.getText().isEmpty()) {
                            let1.setText(sectorStart.getText());
                        } else let2.setText(sectorStart.getText());
                        permission = true;
                    }
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == permissionEnd) { //end research
            try {
                if (registered) {
                    if (working > 0) {
                        working--;
                        iOffice.permissionEnd(clubApp.getName(), sectorEnd.getText());
                        if (let1.getText().equals(sectorEnd.getText())) let1.setText("");
                        if (let2.getText().equals(sectorEnd.getText())) let2.setText("");
                    }
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == exploreTask) { //assign tasks
            try {
                if (permission) {
                    for (ISeeker i : seekers) {
                        i.exploreTask(sector.getText(), field.getText());
                    }
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
