package View.Dialog;

import View.Catalogo.GestisciQuantitaPanel;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.*;

public class GestisciQuantitaDialog extends JDialog {

    public GestisciQuantitaDialog(MainPage frame, String titolo, ComponenteCatalogo comp){
        super(frame, titolo, true);
        add(new GestisciQuantitaPanel(frame, this, comp));
        setSize(new Dimension(350,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
