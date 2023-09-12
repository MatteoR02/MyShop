package View.Main;

import View.Decorator.GuestMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;
import java.awt.*;

public class GuestMainPanel extends JPanel {

    public GuestMainPanel(MainPage frame){
        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,10,10,10);

        Menu guestMenu = new GuestMenu(frame);

        int i=0;
        for (JButton btn : guestMenu.getPulsanti()) {
            btn.setFocusPainted(false);
            btn.setPreferredSize(buttonDimension);
            btn.setFont(buttonFont);
            this.add(btn, new GridBagCostraintsHorizontal(0,i,1,1,insets));
            i++;
        }
    }
}
