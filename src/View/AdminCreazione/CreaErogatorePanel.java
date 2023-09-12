package View.AdminCreazione;

import View.Listeners.AdminListener;
import View.Listeners.Builders.AdminListenerBuilder;
import View.MainPage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreaErogatorePanel extends JPanel {

    public CreaErogatorePanel(JFrame frame) {

        this.setLayout(new MigLayout("insets 80, fillx, align 50% 50%", "[] [] []", "[]30 [] [] [] [] [] [] [] []30 []"));

        JLabel labelCreazione = new JLabel("Creazione Erogatore");
        JLabel labelNome = new JLabel("Nome");
        JLabel labelSito = new JLabel("Sito Web");
        JLabel labelNazione = new JLabel("Nazione");
        JLabel labelCitta = new JLabel("Città");
        JLabel labelCap = new JLabel("Cap");
        JLabel labelVia = new JLabel("Via");
        JLabel labelCivico = new JLabel("Civico");


        JTextField fieldNome = new JTextField(30);
        JTextField fieldSito = new JTextField(30);
        JTextField fieldNazione = new JTextField(20);
        JTextField fieldCitta = new JTextField(20);
        JTextField fieldCap = new JTextField(10);
        JTextField fieldVia = new JTextField(30);
        JTextField fieldCivico = new JTextField(5);
        
        JButton creaErogatoreBtn = new JButton("Crea Erogatore");

        AdminListener adminListener = AdminListenerBuilder.newBuilder((MainPage) frame).fieldNome(fieldNome).fieldSito(fieldSito).
                fieldNazione(fieldNazione).fieldCitta(fieldCitta).fieldCap(fieldCap).fieldVia(fieldVia).fieldCivico(fieldCivico).build();

        creaErogatoreBtn.addActionListener(adminListener);
        creaErogatoreBtn.setActionCommand(AdminListener.CREA_EROGATORE);

        this.add(labelCreazione, "cell 0 0, wrap");
        this.add(labelNome, "cell 0 1");
        this.add(fieldNome, "cell 0 2");
        this.add(labelSito, "cell 0 3");
        this.add(fieldSito, "cell 0 4");
        this.add(labelNazione, "cell 0 5");
        this.add(fieldNazione, "cell 0 6");
        this.add(labelCitta, "cell 1 5");
        this.add(fieldCitta, "cell 1 6");
        this.add(labelCap, "cell 2 5");
        this.add(fieldCap, "cell 2 6");
        this.add(labelVia, "cell 0 7");
        this.add(fieldVia, "cell 0 8 2 1, grow");
        this.add(labelCivico, "cell 2 7");
        this.add(fieldCivico, "cell 2 8");
        this.add(creaErogatoreBtn, "cell 0 9, span, growx");

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }

}

