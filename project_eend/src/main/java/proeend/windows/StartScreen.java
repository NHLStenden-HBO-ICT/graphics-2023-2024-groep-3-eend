package proeend.windows;
import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {
    private JLabel infoLabel;
    private JButton badeend;

    public StartScreen() {
        setTitle("Project Eend");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(180, 245,250));

        panel.add(title());

        ButtonHandler buttonHandler = new ButtonHandler();

        badeend = createButton("Badeend scene renderen");
        badeend.setActionCommand("Badeend");
        badeend.addActionListener(buttonHandler);
        panel.add(badeend);

        for (int i = 0; i < 8; i++) {
            JButton button = createButton("Case " + i + " live bekijken");
            button.setActionCommand("Button" + i);
            button.addActionListener(buttonHandler);
            panel.add(button);
        }

        infoLabel = print();
        panel.add(infoLabel);


        add(panel);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JLabel title(){
        JLabel title = new JLabel("Project Graphics");
        title.setFont(new Font("Segoe UI", Font.ITALIC, 50));
        title.setForeground(Color.BLUE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        return title;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setMaximumSize(new Dimension(300, 50));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLUE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 245, 250), 5, false));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        return button;
    }


    private JLabel print(){
        JLabel infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        infoLabel.setForeground(Color.BLUE);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return infoLabel;
    }

    public void setInfoLabel(String info){
        infoLabel.setText(info);
    }
};

