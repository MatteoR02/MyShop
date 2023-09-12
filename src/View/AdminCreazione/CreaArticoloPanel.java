package View.AdminCreazione;

import Business.ArticoloBusiness;
import Business.PuntoVenditaBusiness;
import Model.PuntoVendita;
import View.Decorator.CreaArticoloMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreaArticoloPanel extends JPanel {

    private ArticoloBusiness.TipoArticolo tipoArticolo;

    public CreaArticoloPanel(MainPage frame){
        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        Dimension buttonDimension = new Dimension(300, 70);
        Insets btnInsets = new Insets(10,10,10,10);
        Insets insets = new Insets(2,10,2,10);



        JLabel labelPV = new JLabel("Selezione Punto Vendita");
        labelPV.setFont(new Font("Arial", Font.PLAIN, 16));

        ArrayList<PuntoVendita> puntiVendita = PuntoVenditaBusiness.getAllPV().getObject();

        PuntoVendita[] selPuntoVendita = puntiVendita.toArray(new PuntoVendita[0]);
        JComboBox selPuntoVenditaBox = new JComboBox(selPuntoVendita);
        selPuntoVenditaBox.setFont(new Font("Arial", Font.PLAIN, 16));

        this.add(labelPV, new GridBagCostraintsHorizontal(0,0,1,1,btnInsets));
        this.add(selPuntoVenditaBox, new GridBagCostraintsHorizontal(0,1,1,1,btnInsets));

        Menu creaArticoloMenu = new CreaArticoloMenu(frame, selPuntoVenditaBox);

        int i=0;
        for (JButton btn : creaArticoloMenu.getPulsanti()) {
            btn.setFocusPainted(false);
            btn.setPreferredSize(buttonDimension);
            btn.setFont(buttonFont);
            this.add(btn, new GridBagCostraintsHorizontal(0,2+i,1,1,btnInsets));
            i++;
        }
    }
}
