package View.ViewModel;


import Business.UtenteBusiness;
import Model.Recensione;
import View.GridBagCostraintsHorizontal;
import View.MainPage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class RecensioneList extends JPanel {

    public RecensioneList(Recensione recensione, MainPage frame){
        //this.setLayout(new GridBagLayout());
        this.setLayout(new MigLayout("", "[]push []push []", "[] [] [grow]"));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Font titoloFont = new Font("Arial", Font.BOLD, 24);
        Font testoFont = new Font("Arial", Font.PLAIN, 18);
        Font clienteFont = new Font("Arial", Font.PLAIN, 17);

        String username = UtenteBusiness.getClienteByID(recensione.getIdCliente()).getSingleObject();

        JLabel cliente = new JLabel(username);
        JLabel titolo = new JLabel(recensione.getTitolo());
        JLabel testo = new JLabel(recensione.getTesto());

        titolo.setFont(titoloFont);
        testo.setFont(testoFont);
        cliente.setFont(clienteFont);

        ImageIcon starsIcon = getRecStars(recensione.getValutazione());

        Image imageStars = starsIcon.getImage(); // La trasformo in un Image
        Image starsNewImg = imageStars.getScaledInstance(100, 15,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        starsIcon = new ImageIcon(starsNewImg);
        JLabel valutazioneIMM = new JLabel(starsIcon);

        ImageIcon userIcon = new ImageIcon("resources/userIcon.png");

        Image imageUser = userIcon.getImage();
        Image userNewImg = imageUser.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(userNewImg);
        JLabel userImm = new JLabel(userIcon);

        JLabel data = new JLabel();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (recensione.getData() != null){
            data = new JLabel(sdf.format(recensione.getData()) + " ", SwingConstants.RIGHT);
            data.setFont(testoFont);

        }

        JButton modificaBtn = new JButton("Modifica");
        JButton eliminaBtn = new JButton("Elimina");

/*
        GridBagCostraintsHorizontal gbcUserImm = new GridBagCostraintsHorizontal(0,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);
        GridBagCostraintsHorizontal gbcCliente = new GridBagCostraintsHorizontal(1,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);
        GridBagCostraintsHorizontal gbcTitolo = new GridBagCostraintsHorizontal(0,2,1,1,insets,1,0,GridBagConstraints.FIRST_LINE_START);
        GridBagCostraintsHorizontal gbcValutazioneImm = new GridBagCostraintsHorizontal(3,2,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);
        GridBagCostraintsHorizontal gbcTesto = new GridBagCostraintsHorizontal(0,3,4,4,insets,1,1,GridBagConstraints.FIRST_LINE_START);

        this.add(userImm, gbcUserImm);
        this.add(cliente, gbcCliente);
        this.add(titolo, gbcTitolo);
        this.add(valutazioneIMM, gbcValutazioneImm);
        this.add(testo,gbcTesto);*/

        this.add(userImm, "cell 0 0, split 2");
        this.add(cliente, "cell 0 0, wrap");
        this.add(data, "cell 2 0, wrap");
        this.add(titolo, "cell 0 1");
        this.add(valutazioneIMM, "cell 2 1, gapleft push, wrap");
        this.add(testo, "span, growx");

    }

    public ImageIcon getRecStars(Recensione.Punteggio valutazione){
        String punteggio = "";
        switch (valutazione){
            case ORRIBILE -> punteggio= "resources/star1.png";
            case SCARSO -> punteggio= "resources/star2.png";
            case MEDIOCRE -> punteggio= "resources/star3.png";
            case BUONO -> punteggio= "resources/star4.png";
            case ECCELLENTE -> punteggio= "resources/star5.png";
        }
        return new ImageIcon(punteggio);
    }

}
