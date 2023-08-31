package View.ViewModel;

import Model.IProdotto;
import View.AddPrenotazionePanel;
import View.GestisciQuantitaPanel;
import View.MainPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
