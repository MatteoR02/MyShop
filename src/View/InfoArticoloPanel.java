package View;


import Business.ExecuteResult;
import Business.RecensioneBusiness;
import Business.Strategy.OrdinamentoRecensioni;
import Model.Recensione;
import View.Listeners.CatalogoListener;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.RecensioneList;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoArticoloPanel extends JPanel {

    public InfoArticoloPanel(MainPage frame, ComponenteCatalogo comp, boolean ordinato, ArrayList<Recensione> listaRecensioni){
        this.setLayout(new GridLayout(2,1,5,5));
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,5,10,5);


        JPanel panelArticolo = new JPanel(new BorderLayout());
        JPanel panelInfo = new JPanel(new MigLayout("insets 15","[] [] [] []20 []","[]10 []10 []10 []5 []"));
        JPanel panelRecensioni = new JPanel(new BorderLayout());


        ImageIcon imageIcon = comp.getImmagini().get(0);

        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        imageIcon = new ImageIcon(newimg);
        JLabel immagine = new JLabel(imageIcon);

        //JLabel vuoto = new JLabel("");

        JLabel nomeArticolo = new JLabel(comp.getNomeArticolo());
        JLabel prezzo = new JLabel(comp.getPrezzo() +"€");
        JLabel nomeCategoria = new JLabel("Categoria: " + comp.getNomeCategoria());
        JLabel nomeProduttore = new JLabel("Produttore: " + comp.getNomeProduttore());

        //JLabel infoArticolo = new JLabel("<html>Qua puoi trovare maggiori informazioni sull'articolo. <br> <em>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut enim nisi. Nunc sit amet dolor odio. Nunc at nibh luctus, vulputate ante id, auctor enim. Vivamus ultricies volutpat massa, id sollicitudin tellus.</em> </html>");

        JButton nextImageBtn = new JButton("Avanti");
        JButton backImageBtn = new JButton("Indietro");
        nextImageBtn.setFocusPainted(false);
        backImageBtn.setFocusPainted(false);


        //GridBagCostraintsHorizontal gbcVuoto = new GridBagCostraintsHorizontal(4,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START);

       /* GridBagCostraintsHorizontal gbcImmagine = new GridBagCostraintsHorizontal(0,1,4,4,insets,0,0,GridBagConstraints.FIRST_LINE_START);

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

        //panelInfo.add(vuoto, gbcVuoto);
        panelInfo.add(immagine,gbcImmagine);
        panelInfo.add(nomeArticolo,gbcNomeArticolo);
        panelInfo.add(prezzo,gbcPrezzo);
        panelInfo.add(nomeCategoria,gbcNomeCategoria);
        panelInfo.add(nomeProduttore,gbcNomeProduttore);
        //panelInfo.add(new JLabel(""), gbcVuoto);
        panelInfo.add(backImageBtn, gbcBackImageBtn);
        panelInfo.add(nextImageBtn, gbcNextImageBtn);
       // panelInfo.add(new JButton("Button"),gbcBtn);*/

        panelInfo.add(immagine, "cell 0 0 4 5");
        panelInfo.add(nomeArticolo, "cell 4 0, wrap");
        panelInfo.add(prezzo, " cell 4 1, wrap");
        panelInfo.add(nomeCategoria, "cell 4 2, wrap");
        panelInfo.add(nomeProduttore, "cell 4 3, wrap");
        //panelInfo.add(infoArticolo, "cell 4 4, wrap");
        panelInfo.add(backImageBtn, "cell 0 5");
        panelInfo.add(nextImageBtn, "cell 3 5, gapleft push");

        Font font = new Font("Arial", Font.PLAIN, 22);
        this.setFont(font);
        for ( Component child :  panelInfo.getComponents()){
            if(!(child instanceof JButton)){
                child.setFont(font);
            }
        }

        panelArticolo.add(panelInfo, BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoni.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton addToListaBtn = new JButton("Aggiungi alla lista");
        JButton recensisciBtn = new JButton("Lascia una recensione");

        addToListaBtn.setFocusPainted(false);
        recensisciBtn.setFocusPainted(false);

        panelBottoni.add(addToListaBtn);
        panelBottoni.add(recensisciBtn);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);

        addToListaBtn.setActionCommand(CatalogoListener.TO_ADD_BTN);
        addToListaBtn.addActionListener(catalogoListener);

        recensisciBtn.setActionCommand(CatalogoListener.TO_ADD_RECENSIONE);
        recensisciBtn.addActionListener(catalogoListener);

        panelArticolo.add(panelBottoni, BorderLayout.SOUTH);

        this.add(panelArticolo);

        JPanel recensioniScrollPanel = new JPanel(new GridLayout(0,1,10,10));

        //ArrayList<Recensione> recensioni = (ArrayList<Recensione>) comp.getRecensioni();
        ExecuteResult<Recensione> resultRec = RecensioneBusiness.loadRecensioni(comp.getId());
        if (!ordinato){
            ArrayList<Recensione> recensioni = resultRec.getObject();
            for (Recensione rec: recensioni) {
                RecensioneList recensioneList = new RecensioneList(rec,frame);
                recensioniScrollPanel.add(recensioneList);
            }
        } else {
            for (Recensione rec : listaRecensioni) {
                RecensioneList recensioneList = new RecensioneList(rec, frame);
                recensioniScrollPanel.add(recensioneList);
            }
        }



        for (int i = 0; i<4; i++) {
            recensioniScrollPanel.add(new JPanel());
        }

        JScrollPane scrollPane = new JScrollPane(recensioniScrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelRecensioni.add(scrollPane,BorderLayout.CENTER);

        JPanel panelNordRecensioni = new JPanel(new MigLayout("", "[]push [] []", "[]"));

        JLabel textRecensioni = new JLabel("Recensioni");
        textRecensioni.setFont(new Font("Arial", Font.BOLD, 30));

        OrdinamentoRecensioni.Ordinamento[] ordinamentoStrategies = {OrdinamentoRecensioni.Ordinamento.RECENTI, OrdinamentoRecensioni.Ordinamento.MIGLIORI};
        JComboBox ordinamento = new JComboBox(ordinamentoStrategies);

        CatalogoListener catalogoListenerSort = new CatalogoListener(resultRec.getObject(), ordinamento, comp);
        catalogoListenerSort.setFrame(frame);

        JButton ordinaBtn = new JButton("Ordina recensioni");
        ordinaBtn.setActionCommand(CatalogoListener.SORT_RECENSIONI);
        ordinaBtn.addActionListener(catalogoListenerSort);

        //ordinaBtn.setActionCommand();

        panelNordRecensioni.add(textRecensioni,"cell 0 0");
        panelNordRecensioni.add(ordinamento, "cell 1 0");
        panelNordRecensioni.add(ordinaBtn, "cell 2 0");

        panelRecensioni.add(panelNordRecensioni, BorderLayout.NORTH);

        this.add(panelRecensioni);

    }
}
