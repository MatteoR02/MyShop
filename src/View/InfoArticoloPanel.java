package View;


import Model.Recensione;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.RecensioneList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoArticoloPanel extends JPanel {

    public InfoArticoloPanel(MainPage frame, ComponenteCatalogo comp){
        this.setLayout(new GridLayout(2,1,5,5));
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,5,10,5);


        JPanel panelArticolo = new JPanel(new GridBagLayout());
        JPanel panelRecensioni = new JPanel(new BorderLayout());


        ImageIcon imageIcon = comp.getImmagini().get(0);

        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        imageIcon = new ImageIcon(newimg);
        JLabel immagine = new JLabel(imageIcon);

        //JLabel vuoto = new JLabel("");

        JLabel nomeArticolo = new JLabel(comp.getNomeArticolo());
        JLabel prezzo = new JLabel(comp.getPrezzo() +"€");
        JLabel nomeCategoria = new JLabel("Categoria: " + comp.getNomeCategoria());
        JLabel nomeProduttore = new JLabel("Produttore: " + comp.getNomeProduttore());

        JButton nextImageBtn = new JButton("Avanti");
        JButton backImageBtn = new JButton("Indietro");
        nextImageBtn.setFocusPainted(false);
        backImageBtn.setFocusPainted(false);
        nextImageBtn.setPreferredSize(new Dimension(80,30));
        backImageBtn.setPreferredSize(new Dimension(80,30));


        //GridBagCostraintsHorizontal gbcVuoto = new GridBagCostraintsHorizontal(4,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcImmagine = new GridBagCostraintsHorizontal(0,1,4,4,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcNomeArticolo = new GridBagCostraintsHorizontal(4,1,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcPrezzo = new GridBagCostraintsHorizontal(4,2,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcNomeCategoria = new GridBagCostraintsHorizontal(4,3,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcNomeProduttore = new GridBagCostraintsHorizontal(4,4,1,1,insets,1,0,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcNextImageBtn = new GridBagCostraintsHorizontal(3,5,1,1,insets,0,1,GridBagConstraints.FIRST_LINE_START);
        gbcNextImageBtn.ipadx = 60;

        GridBagCostraintsHorizontal gbcBackImageBtn = new GridBagCostraintsHorizontal(0,5,1,1,insets,0,1,GridBagConstraints.FIRST_LINE_START);
        gbcBackImageBtn.ipadx = 60;

        //GridBagCostraintsHorizontal gbcBtn = new GridBagCostraintsHorizontal(1,5,1,1,insets,0,1,GridBagConstraints.FIRST_LINE_START);

        GridBagCostraintsHorizontal gbcTitolo = new GridBagCostraintsHorizontal(0,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

        //panelArticolo.add(vuoto, gbcVuoto);
        panelArticolo.add(immagine,gbcImmagine);
        panelArticolo.add(nomeArticolo,gbcNomeArticolo);
        panelArticolo.add(prezzo,gbcPrezzo);
        panelArticolo.add(nomeCategoria,gbcNomeCategoria);
        panelArticolo.add(nomeProduttore,gbcNomeProduttore);
        //panelArticolo.add(new JLabel(""), gbcVuoto);
        panelArticolo.add(backImageBtn, gbcBackImageBtn);
        panelArticolo.add(nextImageBtn, gbcNextImageBtn);
       // panelArticolo.add(new JButton("Button"),gbcBtn);

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  panelArticolo.getComponents()){
            if(!(child instanceof JButton)){
                child.setFont(font);
            }
        }

        this.add(panelArticolo);

        JPanel recensioniScrollPanel = new JPanel(new GridLayout(0,1,10,10));

        ArrayList<Recensione> recensioni = (ArrayList<Recensione>) comp.getRecensioni();
        for (Recensione rec: recensioni   ) {
            RecensioneList recensioneList = new RecensioneList(rec,frame);
            recensioniScrollPanel.add(recensioneList);
        }

        for (int i = 0; i<4; i++) {
            recensioniScrollPanel.add(new JPanel());
        }

        JScrollPane scrollPane = new JScrollPane(recensioniScrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelRecensioni.add(scrollPane,BorderLayout.CENTER);

        JLabel textRecensioni = new JLabel("Recensioni");
        textRecensioni.setFont(new Font("Arial", Font.BOLD, 26));

        panelRecensioni.add(textRecensioni,BorderLayout.NORTH);

        this.add(panelRecensioni);

    }
}
