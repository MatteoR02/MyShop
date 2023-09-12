package View.Dialog;

import Model.Recensione;
import View.Catalogo.AddRecensionePanel;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.*;

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
