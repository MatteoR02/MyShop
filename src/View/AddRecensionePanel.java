package View;

import Model.ListaAcquisto;
import Model.Recensione;
import View.Listeners.CatalogoListener;
import View.Listeners.ClienteListener;
import View.Listeners.ManagerListener;
import View.ViewModel.ComponenteCatalogo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class AddRecensionePanel extends JPanel {

    public AddRecensionePanel(JFrame frame, JDialog dialog, ComponenteCatalogo comp, boolean fromManager, Recensione recensione){

        this.setLayout(new MigLayout("insets 20, fillx", "[]push [] []", "10[]15 []5 []8 []5 [] []30 []"));

        JLabel labelArticolo = new JLabel(comp.getNomeArticolo());
        JLabel labelValutazione = new JLabel("Valutazione       ");
        JLabel labelTitolo = new JLabel("Titolo");
        JLabel labelTesto = new JLabel("Testo");

        JTextField fieldTitolo = new JTextField(15);
        JSlider sliderValutazione = new JSlider(1,5,1);
        JTextArea fieldTesto = new JTextArea("", 3, 100);

        ImageIcon starsIcon = getRecStars(sliderValutazione.getValue());

        Image imageStars = starsIcon.getImage(); // La trasformo in un Image
        Image starsNewImg = imageStars.getScaledInstance(100, 15,  java.awt.Image.SCALE_SMOOTH);
        starsIcon = new ImageIcon(starsNewImg);
        JLabel valutazioneImm = new JLabel(starsIcon);

        fieldTesto.setLineWrap(true);
        fieldTesto.setWrapStyleWord(true);

        JScrollPane scrollPaneTesto = new JScrollPane(fieldTesto, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton annullaBtn = new JButton("Annulla");
        JButton inviaBtn = new JButton("Invia recensione");

        annullaBtn.setFocusPainted(false);
        inviaBtn.setFocusPainted(false);

        CatalogoListener catalogoListener = new CatalogoListener(dialog,fieldTitolo,fieldTesto,sliderValutazione, comp);
        catalogoListener.setFrame((MainPage) frame);
        inviaBtn.setActionCommand(CatalogoListener.ADD_RECENSIONE);
        inviaBtn.addActionListener(catalogoListener);

        if (fromManager){
            ManagerListener managerListener = new ManagerListener((MainPage) frame, dialog, fieldTitolo, fieldTesto, comp, recensione);
            inviaBtn.setText("Rispondi alla recensione");
            inviaBtn.setActionCommand(ManagerListener.RISPONDI_RECENSIONE);
            inviaBtn.addActionListener(managerListener);
        }

        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });

        sliderValutazione.setPaintTrack(true);
        sliderValutazione.setPaintTicks(true);
        sliderValutazione.setPaintLabels(true);
        sliderValutazione.setMajorTickSpacing(1);
        sliderValutazione.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ImageIcon starsIcon = getRecStars(sliderValutazione.getValue());

                Image imageStars = starsIcon.getImage(); // La trasformo in un Image
                Image starsNewImg = imageStars.getScaledInstance(100, 15,  java.awt.Image.SCALE_SMOOTH);
                starsIcon = new ImageIcon(starsNewImg);
                valutazioneImm.setIcon(starsIcon);
            }
        });

        this.add(labelArticolo, "cell 0 0, wrap");
        this.add(labelTitolo, "cell 0 1");
        if (!fromManager){
            this.add(labelValutazione, "cell 2 1, split 2");
            this.add(valutazioneImm, "cell 2 1, wrap");
            this.add(sliderValutazione, "cell 2 2, wrap");
        }
        this.add(fieldTitolo, "cell 0 2");
        this.add(labelTesto, "cell 0 3");
        this.add(scrollPaneTesto, "cell 0 4 3 2");
        this.add(annullaBtn, "cell 0 6, grow");
        this.add(inviaBtn, "cell 2 6, grow");

    }

    public ImageIcon getRecStars(int valutazione){
        String punteggio = "";
        switch (valutazione){
            case 1 -> punteggio= "resources/star1.png";
            case 2 -> punteggio= "resources/star2.png";
            case 3 -> punteggio= "resources/star3.png";
            case 4 -> punteggio= "resources/star4.png";
            case 5 -> punteggio= "resources/star5.png";
        }
        return new ImageIcon(punteggio);
    }

}
