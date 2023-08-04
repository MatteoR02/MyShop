package View.ViewModel;


import Model.Recensione;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;
import java.awt.*;

public class RecensioneList extends JPanel {

    public RecensioneList(Recensione recensione, MainPage frame){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Font titoloFont = new Font("Arial", Font.BOLD, 24);
        Font testoFont = new Font("Arial", Font.PLAIN, 18);



        Insets insets = new Insets(5,10,5,10);
        JLabel cliente = new JLabel(recensione.getIdCliente() +"");
        JLabel titolo = new JLabel(recensione.getTitolo());
        JLabel testo = new JLabel(recensione.getTesto());
        JLabel valutazione = new JLabel(recensione.getValutazione().toString());


        this.add(cliente,new GridBagCostraintsHorizontal(0,0,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START));
        this.add(titolo,new GridBagCostraintsHorizontal(0,1,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START));
        this.add(valutazione,new GridBagCostraintsHorizontal(3,1,1,1,insets,0,0,GridBagConstraints.FIRST_LINE_START));
        this.add(testo,new GridBagCostraintsHorizontal(0,2,4,4,insets,1,1,GridBagConstraints.FIRST_LINE_START));

    }
}
