package View.CreaArticolo;

import View.Listeners.AdminListener;
import View.MainPage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreaPuntoVenditaPanel extends JPanel {

    public CreaPuntoVenditaPanel(JFrame frame) {

        this.setLayout(new MigLayout("insets 80, fillx, align 50% 50%", "[]push []push []", "[]30 [] [] [] [] [] []30 []"));

        JLabel labelCreazione = new JLabel("Creazione Punto Vendita");
        JLabel labelNome = new JLabel("Nome");
        JLabel labelNazione = new JLabel("Nazione");
        JLabel labelCitta = new JLabel("Città");
        JLabel labelCap = new JLabel("Cap");
        JLabel labelVia = new JLabel("Via");
        JLabel labelCivico = new JLabel("Civico");

        JTextField fieldNome = new JTextField(30);
        JTextField fieldNazione = new JTextField(20);
        JTextField fieldCitta = new JTextField(20);
        JTextField fieldCap = new JTextField(10);
        JTextField fieldVia = new JTextField(30);
        JTextField fieldCivico = new JTextField(5);
        
        JButton creaPuntoVenditaBtn = new JButton("Crea Punto Vendita");

        AdminListener adminListener = new AdminListener((MainPage) frame, fieldNome, fieldNazione, fieldCitta, fieldCap, fieldVia, fieldCivico);
        creaPuntoVenditaBtn.addActionListener(adminListener);
        creaPuntoVenditaBtn.setActionCommand(AdminListener.CREA_PV);

        this.add(labelCreazione, "cell 0 0, wrap");
        this.add(labelNome, "cell 0 1");
        this.add(fieldNome, "cell 0 2");
        this.add(labelNazione, "cell 0 3");
        this.add(fieldNazione, "cell 0 4");
        this.add(labelCitta, "cell 1 3");
        this.add(fieldCitta, "cell 1 4");
        this.add(labelCap, "cell 2 3");
        this.add(fieldCap, "cell 2 4");
        this.add(labelVia, "cell 0 5");
        this.add(fieldVia, "cell 0 6 2 1, grow");
        this.add(labelCivico, "cell 2 5");
        this.add(fieldCivico, "cell 2 6");
        this.add(creaPuntoVenditaBtn, "cell 0 7, span, growx");

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }

}

