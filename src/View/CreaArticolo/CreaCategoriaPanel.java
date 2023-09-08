package View.CreaArticolo;

import Business.ArticoloBusiness;
import Model.Categoria;
import Model.Erogatore;
import View.Listeners.AdminListener;
import View.MainPage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CreaCategoriaPanel extends JPanel {

    public CreaCategoriaPanel(JFrame frame) {

        this.setLayout(new MigLayout("insets 80, fillx, align 50% 50%", "[]push [] []", "[]30 [] []30 []"));

        JLabel labelCreazione = new JLabel("Creazione Categoria");
        JLabel labelNome = new JLabel("Nome");
        JLabel labelTipo = new JLabel("Tipo");

        JTextField fieldNome = new JTextField(20);

        Categoria.TipoCategoria[] selTipo = {Categoria.TipoCategoria.PRODOTTO, Categoria.TipoCategoria.SERVIZIO};
        JComboBox selTipoBox = new JComboBox(selTipo);

        JButton creaCategoriaBtn = new JButton("Crea Categoria");

        AdminListener adminListener = new AdminListener((MainPage) frame, fieldNome,selTipoBox);
        creaCategoriaBtn.addActionListener(adminListener);
        creaCategoriaBtn.setActionCommand(AdminListener.CREA_CATEGORIA);

        this.add(labelCreazione, "cell 0 0, wrap");
        this.add(labelNome, "cell 0 1");
        this.add(fieldNome, "cell 0 2");
        this.add(labelTipo, "cell 2 1");
        this.add(selTipoBox, "cell 2 2");
        this.add(creaCategoriaBtn, "cell 0 3, span, growx");


        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }

}

