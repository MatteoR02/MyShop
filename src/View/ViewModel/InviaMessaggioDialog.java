package View.ViewModel;

import View.AddRecensionePanel;
import View.InviaMessaggioPanel;

import javax.swing.*;
import java.awt.*;

public class InviaMessaggioDialog extends JDialog {

    public InviaMessaggioDialog(JFrame frame, String titolo, RigaCliente rigaCliente){
        super(frame, titolo, true);
        add(new InviaMessaggioPanel(frame,this, rigaCliente));
        setSize(new Dimension(500,300));
        setAlwaysOnTop(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
