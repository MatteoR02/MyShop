package View;

import View.Decorator.AdminMenu;
import View.Decorator.ClienteMenu;
import View.Decorator.Menu;

import javax.swing.*;
import java.awt.*;

public class AdminMainPanel extends JPanel {

    public AdminMainPanel(MainPage frame){
        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,10,10,10);

        Menu adminMenu = new AdminMenu(frame);

        int i=0;
        for (JButton btn : adminMenu.getPulsanti()) {
            btn.setFocusPainted(false);
            btn.setPreferredSize(buttonDimension);
            btn.setFont(buttonFont);
            this.add(btn, new GridBagCostraintsHorizontal(0,i,1,1,insets));
            i++;
        }
    }
}
