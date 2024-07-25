package Apps.Office;

import model.Field;
import model.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class OfficeMap extends JFrame {

    private JPanel simPanel;
    private Map map;
    private JPanel contentPane;

    public OfficeMap(Map map) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 800);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        map.makeMap();

        for (ArrayList<Field> rowArray : map.getFieldLabelsArray()) {
            for (Field f : rowArray) {
                JLabel label = f.getLabel();
                label.setBounds(f.getX(), f.getY(), 10, 10);
                simPanel.add(label);
            }
        }

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Office Map");
        setVisible(true);
    }
}
