package View.Listeners;

import Business.*;
import Business.Strategy.IOrdinamentoRecensioneStrategy;
import Business.Strategy.OrdinamentoRecensioni;
import Business.Strategy.RecensioniMiglioriStrategy;
import Business.Strategy.RecensioniRecentiStrategy;
import Model.Articolo;
import Model.Cliente;
import Model.ListaAcquisto;
import Model.Recensione;
import View.MainPage;
import View.ViewModel.AddRecensioneDialog;
import View.ViewModel.AddToListaDialog;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class CatalogoListener implements ActionListener {

    public final static String VIEW_ART_BTN = "view_art_btn";
    public final static String TO_ADD_BTN = "to_add_btn";
    public final static String ADD_TO_LISTA_BTN = "add_to_lista_btn";
    public final static String TO_ADD_RECENSIONE = "to_add_recensione";
    public final static String ADD_RECENSIONE = "add_recensione";
    public final static String SORT_RECENSIONI = "sort_recensioni";



    private MainPage frame;
    private ComponenteCatalogo comp;
    private JComboBox listeBox;
    private JSpinner quantitaSpinner;
    private JDialog dialog;
    private JTextField fieldTitolo;
    private JTextArea fieldTesto;
    private JSlider sliderValutazione;
    private ArrayList<Recensione> recensioni;
    private JComboBox ordinamentoStrategy;

    public CatalogoListener(MainPage frame) {
        this.frame = frame;
    }

    public CatalogoListener(ArrayList<Recensione> recensioni, JComboBox ordinamentoStrategy, ComponenteCatalogo comp) {
        this.comp = comp;
        this.recensioni = recensioni;
        this.ordinamentoStrategy = ordinamentoStrategy;
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

    public CatalogoListener(JDialog dialog, JTextField fieldTitolo, JTextArea fieldTesto, JSlider sliderValutazione, ComponenteCatalogo comp) {
        this.dialog = dialog;
        this.fieldTitolo = fieldTitolo;
        this.fieldTesto = fieldTesto;
        this.sliderValutazione = sliderValutazione;
        this.comp = comp;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (VIEW_ART_BTN.equals(action)) {
            System.out.println("Hai visualizzato l'articolo");
            frame.mostraArticolo(comp, false, null);
        } else if (TO_ADD_BTN.equals(action)) {
            Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            if (c == null){
                JOptionPane.showMessageDialog(frame,"Devi prima effettuare l'accesso","Accesso non effettuato", JOptionPane.ERROR_MESSAGE);
            } else {
                UtenteBusiness.getListeOfCliente(c);
                AddToListaDialog addToListaDialog = new AddToListaDialog(frame, "Aggiungi alla lista", comp);
            }
        } else if (ADD_TO_LISTA_BTN.equals(action)){
            Articolo art = ArticoloBusiness.getArticolo(comp.getId()).getSingleObject();
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
        } else if (TO_ADD_RECENSIONE.equals(action)){
            Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            if (c == null) {
                JOptionPane.showMessageDialog(frame, "Devi prima effettuare l'accesso", "Accesso non effettuato", JOptionPane.ERROR_MESSAGE);
            } else {
                AddRecensioneDialog addRecensioneDialog = new AddRecensioneDialog(frame, "Invia recensione", comp);
            }
        } else if (ADD_RECENSIONE.equals(action)){
            Recensione.Punteggio valutazione = RecensioneBusiness.integerToPunteggio(sliderValutazione.getValue());
            Date dataAttuale = new java.sql.Date(System.currentTimeMillis());
            Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            Recensione newRec = new Recensione(fieldTitolo.getText(), fieldTesto.getText(), valutazione, dataAttuale, null, c.getId());
            ExecuteResult<Boolean> result = RecensioneBusiness.addRecensione(newRec, comp.getId());
            if (result.getResult() == ExecuteResult.ResultStatement.OK_WITH_WARNINGS){
                JOptionPane.showMessageDialog(dialog, "Hai già lasciato una recensione per questo articolo", "Recensione non disponibile", JOptionPane.ERROR_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(dialog, "Non puoi recensire un articolo che non hai acquistato", "Recensione non disponibile", JOptionPane.ERROR_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.OK){
                dialog.dispose();
                JOptionPane.showMessageDialog(dialog,result.getMessage(), "Recensione inviata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraArticolo(comp, false, null);
            }
        } else if (SORT_RECENSIONI.equals(action)){
            OrdinamentoRecensioni ordinamentoRecensioni = new OrdinamentoRecensioni(recensioni);
            IOrdinamentoRecensioneStrategy strategy = new RecensioniRecentiStrategy();
            OrdinamentoRecensioni.Ordinamento ordinamento = (OrdinamentoRecensioni.Ordinamento) ordinamentoStrategy.getSelectedItem();
            switch (ordinamento){
                case RECENTI -> strategy = new RecensioniRecentiStrategy();
                case MIGLIORI -> strategy = new RecensioniMiglioriStrategy();
            }
            ordinamentoRecensioni.setOrdinamentoRecensioneStrategy(strategy);
            ordinamentoRecensioni.ordina();

            frame.mostraArticolo(comp, true, recensioni);
        }

    }
}
