package proeend.windows;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {
    public StartScreen() {

        setTitle("Eend.");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.YELLOW);
        JButton duckbtn = new JButton();
        duckbtn.setText("Badeend scene renderen");
        panel.add(duckbtn);
        add(panel);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



    }


};

