package View;

import View.Decorator.GuestMenu;
import View.Decorator.Menu;

import javax.swing.*;
import java.awt.*;

public class GuestMainPanel extends JPanel {

    public GuestMainPanel(MainPage frame){
        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);

        Menu guestMenu = new GuestMenu(frame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        int i=0;
        for (JButton btn : guestMenu.getPulsantiCentro()) {
            btn.setFocusPainted(false);
            btn.setPreferredSize(buttonDimension);
            btn.setFont(buttonFont);
            gbc.gridx = 0;
            gbc.gridy = i;
            this.add(btn, gbc);
            i++;
        }
    }
}
