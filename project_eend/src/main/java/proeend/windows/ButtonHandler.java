package proeend.windows;
import proeend.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Badeend")) {
            Main.setButtonClicked(1);
            Main.renderDuck();
        }

        if (e.getActionCommand().equals("Button0")) {
            Main.setButtonClicked(0);
            new Main().caseButtonClicked();
        }

        if (e.getActionCommand().equals("Button1")) {
            Main.setButtonClicked(1);
            new Main().caseButtonClicked();
        }
    }
}
