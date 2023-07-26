package View;

import Model.ListaAcquisto;
import View.Listeners.CatalogoListener;
import View.Listeners.ClienteListener;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class AddToListaPanel extends JPanel {

    public AddToListaPanel(JDialog dialog, ArrayList<ListaAcquisto> liste, ComponenteCatalogo comp){

        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        //this.setBackground(sfondo);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(5,10,5,10);
        Insets btnInsets = new Insets(40,10,5,10);
        setBorder(new EmptyBorder(2,10,2,10));

        JLabel textListeMenu = new JLabel("Scegli la lista d'acquisto");
        JLabel textScegliQuantita = new JLabel("Scegli la quantita'");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
        JSpinner quantitaSpinner = new JSpinner(spinnerModel);

        JLabel textNuovaLista = new JLabel("Vuoi creare una nuova lista?");

        JButton addBtn = new JButton("Aggiungi");
        JButton annullaBtn = new JButton("Annulla");
        JTextField nuovaListaField = new JTextField(30);
        JButton creaNuovaListaBtn = new JButton("Crea una nuova lista");

        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });

        JComboBox listeDropDown = new JComboBox(liste.toArray());
        listeDropDown.setSelectedIndex(0);

        CatalogoListener catalogoListener = new CatalogoListener(dialog, comp, listeDropDown, quantitaSpinner);
        ClienteListener clienteListener = new ClienteListener(dialog, nuovaListaField, comp);

        addBtn.setActionCommand(CatalogoListener.ADD_TO_LISTA_BTN);
        addBtn.addActionListener(catalogoListener);
        creaNuovaListaBtn.setActionCommand(ClienteListener.CREATE_NEW_LISTA);
        creaNuovaListaBtn.addActionListener(clienteListener);


        this.add(textScegliQuantita,new GridBagCostraintsHorizontal(0,0,1,1,insets));

        this.add(quantitaSpinner, new GridBagCostraintsHorizontal(0,1,1,1,insets));

        this.add(textListeMenu,new GridBagCostraintsHorizontal(1,0,2,1,insets));

        this.add(listeDropDown, new GridBagCostraintsHorizontal(1,1,2,1,insets));

        this.add(textNuovaLista,new GridBagCostraintsHorizontal(0,2,2,1,insets));

        this.add(nuovaListaField, new GridBagCostraintsHorizontal(0,3,2,1,insets));

        this.add(creaNuovaListaBtn, new GridBagCostraintsHorizontal(2,3,1,1,insets));

        this.add(addBtn,new GridBagCostraintsHorizontal(0,4,2,1,btnInsets));

        this.add(annullaBtn, new GridBagCostraintsHorizontal(2,4,1,1,btnInsets));

    }

}
