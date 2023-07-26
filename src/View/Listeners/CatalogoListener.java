package View.Listeners;

import Business.*;
import Model.Articolo;
import Model.Cliente;
import Model.ListaAcquisto;
import View.MainPage;
import View.ViewModel.AddToListaDialog;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogoListener implements ActionListener {

    public final static String VIEW_ART_BTN = "view_art_btn";
    public final static String TO_ADD_BTN = "to_add_btn";
    public final static String ADD_TO_LISTA_BTN = "add_to_lista_btn";



    private MainPage frame;
    private ComponenteCatalogo comp;
    private JComboBox listeBox;
    private JSpinner quantitaSpinner;
    private JDialog dialog;


    public CatalogoListener(MainPage frame) {
        this.frame = frame;
    }

    public CatalogoListener(MainPage frame, ComponenteCatalogo comp) {
        this.frame = frame;
        this.comp = comp;
    }

    public CatalogoListener(JDialog dialog,ComponenteCatalogo comp, JComboBox listeBox, JSpinner quantitaSpinner) {
        this.dialog = dialog;
        this.comp = comp;
        this.listeBox = listeBox;
        this.quantitaSpinner = quantitaSpinner;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (VIEW_ART_BTN.equals(action)) {
            System.out.println("Hai visualizzato l'articolo");
            frame.mostraArticolo(comp);
        } else if (TO_ADD_BTN.equals(action)) {
            UtenteBusiness.getListeOfCliente((Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
            AddToListaDialog addToListaDialog = new AddToListaDialog(frame, "Aggiungi alla lista", comp);
        } else if (ADD_TO_LISTA_BTN.equals(action)){
            Articolo art = NotWorking_ArticoloBusiness.getArticolo(comp.getId()).getSingleObject();
            ListaAcquisto ls = (ListaAcquisto) listeBox.getSelectedItem();
            int quantita = (Integer) quantitaSpinner.getValue();
            assert ls != null;
            if (ls.getArticoli().containsKey(art)){
                JOptionPane.showMessageDialog(dialog,"Articolo già presente nella lista '"+ls.getNome()+"'!", "Errore lista", JOptionPane.ERROR_MESSAGE);
            } else {
                ExecuteResult<Boolean> result = ListaAcquistoBusiness.addOrRemoveToLista(art,ls, quantita,(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                JOptionPane.showMessageDialog(dialog,result.getMessage(), "Articolo aggiunto", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }

        }

    }
}
