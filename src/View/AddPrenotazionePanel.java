package View;

import Model.Articolo;
import Model.IProdotto;
import View.Listeners.Builders.ClienteListenerBuilder;
import View.Listeners.ClienteListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class AddPrenotazionePanel extends JPanel {

    public AddPrenotazionePanel(MainPage frame, JDialog dialog, ArrayList<IProdotto> articoli){

        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        //this.setBackground(sfondo);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(5,10,5,10);
        Insets btnInsets = new Insets(40,10,5,10);
        setBorder(new EmptyBorder(2,10,2,10));


        JButton annullaBtn = new JButton("Annulla");
        JButton prenotaBtn = new JButton("Prenota");

        ArrayList<JSpinner> spinners = new ArrayList<>();

        ClienteListener clienteListener = ClienteListenerBuilder.newBuilder(frame).dialog(dialog).arrayProdotti(articoli).arraySpinners(spinners).build();

        prenotaBtn.setActionCommand(ClienteListener.PRENOTA_ARTICOLI);
        prenotaBtn.addActionListener(clienteListener);

        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });

        int i = 0;
        for (IProdotto art : articoli  ) {
            JLabel nomeArt = new JLabel(art.getNome());
            JSpinner sp = new JSpinner();
            SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
            sp.setModel(spinnerModel);
            spinners.add(sp);
            this.add(nomeArt, new GridBagCostraintsHorizontal(0,i,1,1,insets));
            this.add(sp, new GridBagCostraintsHorizontal(2,i,1,1,insets));
            i++;
        }

        this.add(prenotaBtn, new GridBagCostraintsHorizontal(2,i,1,1,btnInsets));

        this.add(annullaBtn, new GridBagCostraintsHorizontal(0,i,1,1,btnInsets));

    }

}
