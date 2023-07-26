package View.ViewModel;

import Business.SessionManager;
import Model.ListaAcquisto;
import View.AddToListaPanel;
import View.MainPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddToListaDialog extends JDialog {

    public AddToListaDialog(JFrame frame, String titolo, ComponenteCatalogo comp){
        super(frame, titolo, true);
        add(new AddToListaPanel(this, (ArrayList<ListaAcquisto>) SessionManager.getSession().get(SessionManager.LISTE_CLIENTE), comp));
        setSize(new Dimension(500,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
