package View.Dialog;

import View.ListeAcquisto.AddListaPanel;
import View.MainPage;

import javax.swing.*;
import java.awt.*;

public class AddListaDialog extends JDialog {

    public AddListaDialog(MainPage frame, String titolo ){
        super(frame, titolo, true);
        add(new AddListaPanel(frame, this));
        setSize(new Dimension(500,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
