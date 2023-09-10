package View.ViewModel;

import Business.SessionManager;
import Model.ListaAcquisto;
import Model.Recensione;
import View.AddRecensionePanel;
import View.AddToListaPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddRecensioneDialog extends JDialog {

    public AddRecensioneDialog(JFrame frame, String titolo, ComponenteCatalogo comp, boolean fromManager, Recensione recensione){
        super(frame, titolo, true);
        add(new AddRecensionePanel(frame, this, comp, fromManager, recensione));
        setSize(new Dimension(500,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
