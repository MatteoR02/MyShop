package View.Listeners;

import Business.*;
import Business.Strategy.OrdinamentoArticoli.*;
import Business.Strategy.OrdinamentoRecensioni.IOrdinamentoRecensioneStrategy;
import Business.Strategy.OrdinamentoRecensioni.OrdinamentoRecensioni;
import Business.Strategy.OrdinamentoRecensioni.RecensioniMiglioriStrategy;
import Business.Strategy.OrdinamentoRecensioni.RecensioniRecentiStrategy;
import Model.*;
import View.MainPage;
import View.Dialog.AddRecensioneDialog;
import View.Dialog.AddToListaDialog;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CatalogoListener implements ActionListener {

    public final static String VIEW_ART_BTN = "view_art_btn";
    public final static String VIEW_SOTTOPRODOTTI = "view_sottoprodotti";
    public final static String TO_ADD_BTN = "to_add_btn";
    public final static String ADD_TO_LISTA_BTN = "add_to_lista_btn";
    public final static String TO_ADD_RECENSIONE = "to_add_recensione";
    public final static String ADD_RECENSIONE = "add_recensione";
    public final static String DELETE_RECENSIONE = "delete_recensione";
    public final static String MODIFY_RECENSIONE = "modify_recensione";
    public final static String SORT_RECENSIONI = "sort_recensioni";
    public final static String SORT_CATALOGO = "sort_catalogo";
    public final static String SELECT_CATEGORIA = "select_categoria";
    public final static String NEXT_FOTO = "next_foto";
    public final static String BACK_FOTO = "back_foto";


    private MainPage frame;
    private ComponenteCatalogo comp;
    private JComboBox listeBox;
    private JSpinner quantitaSpinner;
    private JDialog dialog;
    private JTextField fieldTitolo;
    private JTextArea fieldTesto;
    private JSlider sliderValutazione;
    private ArrayList<Recensione> listaRecensioni;
    private JComboBox categoriaBox;
    private JComboBox ordinamentoBox;
    private Recensione recensione;
    private JLabel labelImm;
    private JLabel labelIndex;


    public CatalogoListener(MainPage frame, ComponenteCatalogo comp, JComboBox listeBox, JSpinner quantitaSpinner, JDialog dialog, JTextField fieldTitolo, JTextArea fieldTesto, JSlider sliderValutazione, ArrayList<Recensione> listaRecensioni, JComboBox ordinamentoBox, JComboBox categoriaBox, Recensione recensione, JLabel labelImm, JLabel labelIndex) {
        this.frame = frame;
        this.comp = comp;
        this.listeBox = listeBox;
        this.quantitaSpinner = quantitaSpinner;
        this.dialog = dialog;
        this.fieldTitolo = fieldTitolo;
        this.fieldTesto = fieldTesto;
        this.sliderValutazione = sliderValutazione;
        this.listaRecensioni = listaRecensioni;
        this.ordinamentoBox = ordinamentoBox;
        this.categoriaBox = categoriaBox;
        this.recensione = recensione;
        this.labelImm = labelImm;
        this.labelIndex = labelIndex;
    }

    public CatalogoListener(MainPage frame, ComponenteCatalogo comp) {
        this.frame = frame;
        this.comp = comp;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (VIEW_ART_BTN.equals(action)) {
            frame.mostraArticolo(comp, false, null);
        } else if (VIEW_SOTTOPRODOTTI.equals(action)){
            ProdottoComposito pd = (ProdottoComposito) ArticoloBusiness.getArticolo(comp.getIdArticolo()).getSingleObject();
            frame.mostraSottoProdotti(pd);
        } else if (TO_ADD_BTN.equals(action)) {
            Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            if (c == null){
                JOptionPane.showMessageDialog(frame,"Devi prima effettuare l'accesso","Accesso non effettuato", JOptionPane.ERROR_MESSAGE);
            } else {
                UtenteBusiness.getListeOfCliente(c);
                AddToListaDialog addToListaDialog = new AddToListaDialog(frame, "Aggiungi alla lista", comp);
            }
        } else if (ADD_TO_LISTA_BTN.equals(action)){
            Articolo art = ArticoloBusiness.getArticolo(comp.getIdArticolo()).getSingleObject();
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
            } else if (!ArticoloBusiness.isArticoloBoughtFrom(comp.getIdArticolo(), c.getId()).getSingleObject()){
                JOptionPane.showMessageDialog(frame, "Non puoi recensire un articolo che non hai acquistato", "Recensione non disponibile", JOptionPane.ERROR_MESSAGE);
            } else if (RecensioneBusiness.isRecensioneDone(comp.getIdArticolo(), c.getId())){
                JOptionPane.showMessageDialog(dialog, "Hai già lasciato una recensione per questo articolo", "Recensione non disponibile", JOptionPane.ERROR_MESSAGE);
            } else {
                AddRecensioneDialog addRecensioneDialog = new AddRecensioneDialog(frame, "Invia recensione", comp, false, null);
            }
        } else if (ADD_RECENSIONE.equals(action)){
            Recensione.Punteggio valutazione = RecensioneBusiness.integerToPunteggio(sliderValutazione.getValue());
            Date dataAttuale = new java.sql.Date(System.currentTimeMillis());
            Utente u = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            if(u instanceof Cliente){
                Cliente c = (Cliente) u;
                String titolo = fieldTitolo.getText();
                String testo = fieldTesto.getText();
                if (titolo.isBlank() || testo.isBlank()){
                    JOptionPane.showMessageDialog(dialog, "Uno dei campi è vuoto", "Recensione non disponibile", JOptionPane.ERROR_MESSAGE);
                }
                Recensione newRec = new Recensione(titolo, testo, valutazione, dataAttuale, null, c.getId());
                newRec.setIdRecensione(0);
                result = RecensioneBusiness.addRecensione(newRec, comp.getIdArticolo());
            }


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
            OrdinamentoRecensioni ordinamentoRecensioni = new OrdinamentoRecensioni(listaRecensioni);
            IOrdinamentoRecensioneStrategy strategy = new RecensioniRecentiStrategy();
            OrdinamentoRecensioni.Ordinamento ordinamento = (OrdinamentoRecensioni.Ordinamento) ordinamentoBox.getSelectedItem();
            switch (ordinamento){
                case RECENTI -> strategy = new RecensioniRecentiStrategy();
                case MIGLIORI -> strategy = new RecensioniMiglioriStrategy();
            }
            ordinamentoRecensioni.setOrdinamentoRecensioneStrategy(strategy);
            ordinamentoRecensioni.ordina();

            frame.mostraArticolo(comp, true, listaRecensioni);

        } else if (SORT_CATALOGO.equals(action)){
            OrdinamentoArticoli ordinamentoArticoli = new OrdinamentoArticoli((List<Articolo>) SessionManager.getSession().get(SessionManager.ALL_ARTICOLI));
            IOrdinamentoArticoliStrategy strategy = new ArticoliPiuVotatiStrategy();
            OrdinamentoArticoli.Ordinamento ordinamento = (OrdinamentoArticoli.Ordinamento) ordinamentoBox.getSelectedItem();
            switch (ordinamento) {
                case PIU_VOTATI -> strategy = new ArticoliPiuVotatiStrategy();
                case PREZZO_PIU_ALTO -> strategy = new ArticoliPrezzoAltoStrategy();
                case PREZZO_PIU_BASSO -> strategy = new ArticoliPrezzoBassoStrategy();
                case ORDINE_ALFABETICO -> strategy = new ArticoliOrdineAlfabeticoStrategy();
                case TIPOLOGIA -> strategy = new ArticoliTipologiaStrategy();
            }
            ordinamentoArticoli.setOrdinamentoArticoliStrategy(strategy);
            ordinamentoArticoli.ordina();

            frame.mostraCatalogo(null,false);
        } else if (SELECT_CATEGORIA.equals(action)){
            Categoria categoriaSelezionata = (Categoria) categoriaBox.getSelectedItem();
            ArrayList<Articolo> articoliCat = ArticoloBusiness.getArticoliOfCategoria((ArrayList<Articolo>) SessionManager.getSession().get(SessionManager.ALL_ARTICOLI), categoriaSelezionata);
            frame.mostraCatalogo(articoliCat, true);

        } else if (DELETE_RECENSIONE.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare la recensione?", "Eliminare recensione?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = RecensioneBusiness.removeRecensione(recensione.getId());
                JOptionPane.showMessageDialog(frame,"Recensione eliminata", "Recensione eliminata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraArticolo(comp, false, null);
            }
        } else if (NEXT_FOTO.equals(action)){

            int index = Integer.parseInt(labelImm.getName());
            ImageIcon currentIcon;

            if (index + 1 < comp.getImmagini().toArray().length){
                currentIcon = FotoBusiness.scaleImageIcon(comp.getImmagini().get(index+1), 250,250);
                index++;
                labelImm.setName(index + "");
                labelImm.setIcon(currentIcon);
                labelIndex.setText("" + (index + 1) +"/"+comp.getImmagini().toArray().length);
            }/* else  {
                currentIcon = FotoBusiness.scaleImageIcon(comp.getImmagini().get(0), 250,250);
                index = 0;
                labelImm.setName(index + "");
                labelImm.setIcon(currentIcon);
                labelIndex.setText("" + 1 +"/"+comp.getImmagini().toArray().length);

            }*/
        } else if (BACK_FOTO.equals(action)){

            int index = Integer.parseInt(labelImm.getName());
            ImageIcon currentIcon;

            if (index - 1 >= 0){
                currentIcon = FotoBusiness.scaleImageIcon(comp.getImmagini().get(index-1), 250,250);
                index--;
                labelImm.setName(index + "");
                labelImm.setIcon(currentIcon);
                labelIndex.setText("" + (index + 1) +"/"+comp.getImmagini().toArray().length);

            } /*else  {
                currentIcon = FotoBusiness.scaleImageIcon(comp.getImmagini().get(comp.getImmagini().toArray().length-1), 250,250);
                index = comp.getImmagini().toArray().length-1;
                labelImm.setName(index + "");
                labelImm.setIcon(currentIcon);
                labelIndex.setText("" + 1 +"/"+comp.getImmagini().toArray().length);

            }*/
        }
    }
}
