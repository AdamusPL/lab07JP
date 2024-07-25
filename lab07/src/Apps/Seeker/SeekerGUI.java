package Apps.Seeker;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.IWorld;
import model.Artifact;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class SeekerGUI extends JFrame implements ActionListener {
    private SeekerApp seekerApp;
    private IOffice iOffice;
    private IWorld iWorld;
    private JPanel simPanel;
    private JPanel contentPane;
    private JButton getClubs;
    private JButton register;
    private JButton unregister;
    private JButton report;

    private JButton explore;

    private JTextField seekerName;
    private JTextField clubName;
    private JTextField sector;
    private JTextField field;

    private JLabel assigned;
    private JLabel availableClubs;
    private JLabel foundArtifact;
    private JLabel availableTasks;
    private boolean registered;

    public SeekerGUI(SeekerApp seekerApp, IOffice iOffice, IWorld iWorld) {
        this.seekerApp = seekerApp;
        this.iOffice = iOffice;
        this.iWorld = iWorld;
        this.registered = false;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 300, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        getClubs = new JButton("getClubs()");
        getClubs.setBounds(5, 5, 150, 20);
        getClubs.addActionListener(this);

        register = new JButton("register()");
        register.setBounds(5, 35, 150, 20);
        register.addActionListener(this);

        seekerName = new JTextField("Mark");
        seekerName.setBounds(155, 35, 50, 20);

        clubName = new JTextField("A");
        clubName.setBounds(205, 35, 50, 20);

        sector = new JTextField("A2");
        sector.setBounds(155, 105, 50, 20);

        field = new JTextField("A2");
        field.setBounds(205, 105, 50, 20);

        unregister = new JButton("unregister()");
        unregister.setBounds(5, 55, 150, 20);
        unregister.addActionListener(this);

        report = new JButton("report()");
        report.setBounds(5, 75, 150, 20);
        report.addActionListener(this);

        explore = new JButton("explore()");
        explore.setBounds(5, 105, 150, 20);
        explore.addActionListener(this);

        assigned = new JLabel("Currently, you are not member of any club");
        assigned.setBounds(5, 155, 250, 20);

        availableClubs = new JLabel();
        availableClubs.setBounds(5, 175, 250, 20);

        availableTasks = new JLabel("Available tasks: ");
        availableTasks.setBounds(5, 175, 250, 20);

        foundArtifact = new JLabel();
        foundArtifact.setBounds(5, 195, 250, 20);

        simPanel.add(foundArtifact);
        simPanel.add(availableClubs);
        simPanel.add(assigned);
        simPanel.add(report);
        simPanel.add(field);
        simPanel.add(sector);
        simPanel.add(explore);
        simPanel.add(clubName);
        simPanel.add(seekerName);
        simPanel.add(unregister);
        simPanel.add(register);
        simPanel.add(getClubs);

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Seeker");
        setVisible(true);
    }

    private List<IClub> iClubs;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getClubs) {
            try {
                String clubs = "";
                clubs += "List of available clubs: ";
                iClubs = iOffice.getClubs();
                for (IClub i : iClubs) {
                    clubs += i.getName() + ", ";
                }
                availableClubs.setText(clubs);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == register) {
            if (!registered) {
                try {
                    seekerApp.setSeekerName(seekerName.getText());
                    seekerApp.setClubName(clubName.getText());
                    for (IClub i : iClubs) {
                        if (i.getName().equals(seekerApp.getClubName())) {
                            registered = i.register(seekerApp);
                            seekerApp.setiClub(i);
                            assigned.setText("You are a member of: " + clubName.getText());
                            break;
                        }
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (e.getSource() == unregister) {
            if (registered) {
                try {
                    registered = seekerApp.getiClub().unregister(seekerApp.getName());
                    assigned.setText("Currently you are not a member of any club");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (e.getSource() == explore) {
            Artifact artifact;
            if (registered) {
                try {
                    artifact = iWorld.explore(seekerApp.getName(), sector.getText(), field.getText());
                    if (artifact != null) {
                        foundArtifact.setText("Artifact found: " + artifact.getCategory());
                    } else foundArtifact.setText("Artifact found: Blank");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
