package View;

import Business.ArticoloBusiness;
import Model.IProdotto;
import Model.Prodotto;
import View.Listeners.ClienteListener;
import View.Listeners.ManagerListener;
import View.ViewModel.ComponenteCatalogo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GestisciQuantitaPanel extends JPanel {

    public GestisciQuantitaPanel(MainPage frame, JDialog dialog, ComponenteCatalogo comp){

        this.setLayout(new MigLayout("align 50% 50%", "[]push []", "[]50 []50 []"));
        Color sfondo = Color.WHITE;
        Font testoFont = new Font("Arial", Font.PLAIN, 16);
        //this.setBackground(sfondo);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(5,10,5,10);
        Insets btnInsets = new Insets(40,10,5,10);
        setBorder(new EmptyBorder(2,10,2,10));

        Prodotto prodotto = (Prodotto) ArticoloBusiness.getArticolo(comp.getIdArticolo()).getSingleObject();

        JLabel disponibilita = new JLabel("Disponibilità in magazzino: " + prodotto.getCollocazione().getQuantita());
        disponibilita.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel nomeArt = new JLabel(comp.getNomeArticolo());
        JSpinner sp = new JSpinner();
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
        sp.setModel(spinnerModel);

        nomeArt.setFont(testoFont);
        sp.setFont(testoFont);


        JButton annullaBtn = new JButton("Annulla");
        JButton rifornisciBtn = new JButton("Rifornisci");

        ManagerListener managerListener = new ManagerListener(frame,dialog,prodotto,sp);

        rifornisciBtn.setActionCommand(ManagerListener.RIFORNISCI_PRODOTTO);
        rifornisciBtn.addActionListener(managerListener);


        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });


        this.add(disponibilita,"cell 0 0, span, wrap");

        this.add(nomeArt, "cell 0 1");
        this.add(sp, "cell 1 1, wrap");

        this.add(rifornisciBtn, "cell 1 2");

        this.add(annullaBtn, "cell 0 2");

    }

}
