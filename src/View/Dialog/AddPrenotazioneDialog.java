package View.Dialog;

import Model.IProdotto;
import View.ListeAcquisto.AddPrenotazionePanel;
import View.MainPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddPrenotazioneDialog extends JDialog {

    public AddPrenotazioneDialog(MainPage frame, String titolo, ArrayList<IProdotto> articoli){
        super(frame, titolo, true);
        add(new AddPrenotazionePanel(frame, this, articoli));
        setSize(new Dimension(500,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
