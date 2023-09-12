package View.Main;

import View.Decorator.ClienteMenu;
import View.Decorator.ManagerMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;
import java.awt.*;

public class ManagerMainPanel extends JPanel {

    public ManagerMainPanel(MainPage frame){
        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,10,10,10);

        Menu managerMenu = new ManagerMenu(frame);

        int i=0;
        for (JButton btn : managerMenu.getPulsanti()) {
            btn.setFocusPainted(false);
            btn.setPreferredSize(buttonDimension);
            btn.setFont(buttonFont);
            this.add(btn, new GridBagCostraintsHorizontal(0,i,1,1,insets));
            i++;
        }
    }
}
