package View;

import View.ViewModel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class CatalogoPanel extends JPanel {

    public CatalogoPanel() {

        this.setLayout(new BorderLayout());
        JPanel centro = new JPanel();

        centro.setLayout(new GridLayout(0,3, 10, 10));
        for (int i =0; i<20; i++){
            ArticoloList art = new ArticoloList();
            centro.add(art);
        }
        ArticoloList art1 = new ArticoloList();
        ArticoloList art2 = new ArticoloList();
        ArticoloList art3 = new ArticoloList();

        centro.add(art1);
        centro.add(art2);
        centro.add(art3);
        //this.add(centro, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(centro);
        this.add(scrollPane,BorderLayout.CENTER);


      /*  //JLabel benvenuto = new JLabel("Qui mostreremo la tabella dei prodotti");
        //add(benvenuto);

        setLayout(new BorderLayout());

        String[][] dati = new String[3][5];
        dati[0][0] = "A";
        dati[0][1] = "B";
        dati[0][2] = "c";
        dati[0][3] = "D";
        dati[0][4] = "E";
        dati[1][0] = "ff";
        dati[1][1] = "GGG";
        dati[1][2] = "H";
        dati[1][3] = "iiiii";
        dati[1][4] = "m";
        dati[2][0] = "n";
        dati[2][1] = "OOOO";
        dati[2][2] = "p";
        dati[2][3] = "q";
        dati[2][4] = "r";

        String[] nomiColonne = new String[] {"Colonna 1","Colonna 2","Colonna 3","Colonna 4","Colonna 5"};

        //JTable tabella = new JTable(dati, nomiColonne);

        List<RigaCatalogo> righe = new ArrayList<RigaCatalogo>();

        RigaCatalogo riga1 = new RigaCatalogo();
        riga1.setIdProdotto(759);
        riga1.setNomeProdotto("Tavolo Giardino");
        riga1.setNomeProduttore("Ikea");
        riga1.setNomeCategoria("Esterni");
        riga1.setPrezzo(99.9F);

        RigaCatalogo riga2 = new RigaCatalogo();
        riga2.setIdProdotto(35);
        riga2.setNomeProdotto("Sedia");
        riga2.setNomeProduttore("Mondo Convenienza");
        riga2.setNomeCategoria("Casa");
        riga2.setPrezzo(50.0F);

        RigaCatalogo riga3 = new RigaCatalogo();
        riga3.setIdProdotto(22);
        riga3.setNomeProdotto("Tosaerba");
        riga3.setNomeProduttore("Sony");
        riga3.setNomeCategoria("Elettronica");
        riga3.setPrezzo(350.0F);

        righe.add(riga1);
        righe.add(riga2);
        righe.add(riga3);

        CatalogoTableModel tableModel = new CatalogoTableModel(righe);
        JTable tabella = new JTable(tableModel);

        tabella.setRowHeight(200);
        JScrollPane scrollPane = new JScrollPane(tabella);
        add(scrollPane, BorderLayout.CENTER);

        JPanel pulsantiAzioneTabella = new JPanel();
        pulsantiAzioneTabella.setLayout(new FlowLayout());
        JButton mettiNelCarrello = new JButton("Metti nel carrello");

        mettiNelCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] righeSelezionate = tabella.getSelectedRows();
                System.out.println(righeSelezionate[0]);
                CatalogoTableModel tModel = (CatalogoTableModel) tabella.getModel();
                for (int i=0; i<righeSelezionate.length; i++){
                    RigaCatalogo rigaSelezionata = tModel.getRighe().get(righeSelezionate[i]);
                    System.out.println("ID prodotto selezionate: " + rigaSelezionata.getIdProdotto());
                }

            }
        });

        pulsantiAzioneTabella.add(mettiNelCarrello);
        add(pulsantiAzioneTabella, BorderLayout.SOUTH);*/
    }
}
