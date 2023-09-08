package View.Listeners;

import Business.ArticoloBusiness;
import Business.ExecuteResult;
import Model.*;
import View.MainPage;
import View.ViewModel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminListener implements ActionListener {

    public final static String ASSIGN_MANAGER = "assign_manager";
    public final static String CREA_MANAGER = "crea_manager";
    public final static String TO_CREA_ARTICOLO = "to_crea_articolo";
    public final static String CREA_ARTICOLO = "crea_articolo";
    public final static String MODIFICA_ARTICOLO = "modifica_articolo";
    public final static String ELIMINA_ARTICOLO = "elimina_articolo";
    public final static String TO_CREA_PRODOTTO = "to_crea_prodotto";
    public final static String CREA_PRODOTTO = "crea_prodotto";
    public final static String TO_CREA_SERVIZIO = "to_crea_servizio";
    public final static String CREA_SERVIZIO = "crea_servizio";
    public final static String TO_CREA_PRODOTTO_COMPOSITO = "to_crea_prodotto_composito";
    public final static String CREA_PRODOTTO_COMPOSITO = "crea_prodotto_composito";
    public final static String SELEZIONA_IMMAGINI = "seleziona_immagini";
    public final static String TO_CREA_CATEGORIA = "to_crea_categoria";
    public final static String CREA_CATEGORIA = "crea_categoria";
    public final static String TO_CREA_EROGATORE = "to_crea_erogatore";
    public final static String CREA_EROGATORE = "crea_erogatore";

    private MainPage frame;

    private JComboBox selPuntoVenditaBox;

    private ComponenteCatalogo componenteCatalogo;

    private PuntoVendita puntoVendita;

    private JTextField fieldNome;
    private JTextArea fieldDescrizione;
    private JTextField fieldPrezzo;
    private JTextField fieldCorsia, fieldScaffale;
    private JComboBox magazzinoBox;
    private JComboBox categoriaBox;
    private JComboBox erogatoreBox;
    private JComboBox tipoBox;
    private JLabel labelImmagini;
    private File[] immagini;

    private JTable tableProdotti;

    private JTextField fieldSito, fieldNazione, fieldCitta, fieldCap, fieldVia, fieldCivico;


    public AdminListener(MainPage frame) {
        this.frame = frame;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    public AdminListener(MainPage frame, ComponenteCatalogo componenteCatalogo) {
        this.frame = frame;
        this.componenteCatalogo = componenteCatalogo;
    }

    public AdminListener(MainPage frame, JComboBox selPuntoVenditaBox) {
        this.frame = frame;
        this.selPuntoVenditaBox = selPuntoVenditaBox;
    }

    public AdminListener(MainPage frame, JTextField fieldNome, JTextArea fieldDescrizione, JTextField fieldPrezzo, JComboBox categoriaBox, JComboBox erogatoreBox, JLabel labelImmagini, PuntoVendita puntoVendita) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.fieldDescrizione = fieldDescrizione;
        this.fieldPrezzo = fieldPrezzo;
        this.categoriaBox = categoriaBox;
        this.erogatoreBox = erogatoreBox;
        this.labelImmagini = labelImmagini;
        this.puntoVendita = puntoVendita;
    }

    public AdminListener(MainPage frame, JTextField fieldNome, JTextArea fieldDescrizione, JTextField fieldPrezzo, JTextField fieldCorsia, JTextField fieldScaffale, JComboBox magazzinoBox, JComboBox categoriaBox, JComboBox erogatoreBox, JLabel labelImmagini, PuntoVendita puntoVendita) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.fieldDescrizione = fieldDescrizione;
        this.fieldPrezzo = fieldPrezzo;
        this.fieldCorsia = fieldCorsia;
        this.fieldScaffale = fieldScaffale;
        this.magazzinoBox = magazzinoBox;
        this.categoriaBox = categoriaBox;
        this.erogatoreBox = erogatoreBox;
        this.labelImmagini = labelImmagini;
        this.puntoVendita = puntoVendita;
    }

    public AdminListener(MainPage frame, JTextField fieldNome, JTextArea fieldDescrizione, JTextField fieldPrezzo, JComboBox categoriaBox, JComboBox erogatoreBox, JLabel labelImmagini, JTable tableProdotti) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.fieldDescrizione = fieldDescrizione;
        this.fieldPrezzo = fieldPrezzo;
        this.categoriaBox = categoriaBox;
        this.erogatoreBox = erogatoreBox;
        this.labelImmagini = labelImmagini;
        this.tableProdotti = tableProdotti;
    }

    public AdminListener(MainPage frame, JTextField fieldNome, JComboBox tipoBox) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.tipoBox = tipoBox;
    }

    public AdminListener(MainPage frame, JTextField fieldNome, JTextField fieldSito, JTextField fieldNazione, JTextField fieldCitta, JTextField fieldCap, JTextField fieldVia, JTextField fieldCivico) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.fieldSito = fieldSito;
        this.fieldNazione = fieldNazione;
        this.fieldCitta = fieldCitta;
        this.fieldCap = fieldCap;
        this.fieldVia = fieldVia;
        this.fieldCivico = fieldCivico;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (TO_CREA_ARTICOLO.equals(action)) {
            frame.mostraCreaArticolo();
        } else if (CREA_ARTICOLO.equals(action)){

        } else if (MODIFICA_ARTICOLO.equals(action)){

        } else if (ELIMINA_ARTICOLO.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare l'articolo?", "Eliminare articolo?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = ArticoloBusiness.removeArticolo(componenteCatalogo.getIdArticolo());
                JOptionPane.showMessageDialog(frame,"Articolo eliminato", "Articolo eliminato", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraCatalogo(null, false);
            }
        } else if (TO_CREA_PRODOTTO.equals(action)){
            PuntoVendita pv = (PuntoVendita) selPuntoVenditaBox.getSelectedItem();
            frame.mostraCreaProdotto(pv);
        } else if (TO_CREA_PRODOTTO_COMPOSITO.equals(action)){
            PuntoVendita pv = (PuntoVendita) selPuntoVenditaBox.getSelectedItem();
            frame.mostraCreaProdottoComposito(pv);
        } else if (TO_CREA_SERVIZIO.equals(action)){
            PuntoVendita pv = (PuntoVendita) selPuntoVenditaBox.getSelectedItem();
            frame.mostraCreaServizio(pv);
        } else if (SELEZIONA_IMMAGINI.equals(action)){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files (.png, .jpg, .jpeg)", "png", "jpg", "jpeg");
            fileChooser.setFileFilter(filter);
            int risposta = fileChooser.showOpenDialog(frame);
            if (risposta == JFileChooser.APPROVE_OPTION){
                this.immagini = fileChooser.getSelectedFiles();
                String nomeImmagini = "";
                for (int i=0; i<immagini.length; i++){
                    nomeImmagini += immagini[i].getName() + " ";
                    labelImmagini.setText(nomeImmagini);
                }
            }
        }
        else if (CREA_PRODOTTO.equals(action)){
            String nome = fieldNome.getText();
            String descrizione = fieldDescrizione.getText();
            Float prezzo = Float.parseFloat(fieldPrezzo.getText());
            String corsia = fieldCorsia.getText();
            int scaffale = Integer.parseInt(fieldScaffale.getText());
            Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
            Erogatore erogatore = (Erogatore) erogatoreBox.getSelectedItem();
            ArrayList<File> files = new ArrayList<>();
            if (immagini!=null){
                files = new ArrayList<>(Arrays.asList(immagini));
            }

            Prodotto prodottoNuovo = new Prodotto(nome,descrizione,prezzo,categoria,erogatore);
            prodottoNuovo.setCollocazione(new Collocazione(0, corsia, scaffale, (Magazzino) magazzinoBox.getSelectedItem()));

            ExecuteResult<Boolean> result = ArticoloBusiness.addArticolo(prodottoNuovo, ArticoloBusiness.TipoArticolo.PRODOTTO, files, puntoVendita.getId());
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(frame,"Prodotto creato con successo", "Prodotto creato", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "C'è stato un errore nella creazione del prodotto", "Errore creazione prodotto", JOptionPane.ERROR_MESSAGE);
            }
            frame.mostraMain();
        } else if (CREA_PRODOTTO_COMPOSITO.equals(action)){
            String nome = fieldNome.getText();
            String descrizione = fieldDescrizione.getText();
            Float prezzo = 0f;
            Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
            Erogatore erogatore = (Erogatore) erogatoreBox.getSelectedItem();
            ArrayList<File> files = new ArrayList<>();
            if (immagini!=null){
                files = new ArrayList<>(Arrays.asList(immagini));
            }

            ArrayList<IProdotto> prodotti = new ArrayList<>();

            ArrayList<RigaProdottoCreazione> righeProdotti = new ArrayList<>();
            CreazionePCTableModel tModel = (CreazionePCTableModel) tableProdotti.getModel();

            for (int i=0; i < tableProdotti.getRowCount(); i++) {
                boolean check;
                RigaProdottoCreazione rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!righeProdotti.contains(rigaSelezionata)) {
                        righeProdotti.add(rigaSelezionata);
                    }
                }
            }
            for (RigaProdottoCreazione riga: righeProdotti ) {
                int idProdotto = riga.getIdProdotto();
                prodotti.add(ArticoloBusiness.getIProdotto(idProdotto).getSingleObject());
            }

            ProdottoComposito prodottoCompositoNuovo = new ProdottoComposito(nome,descrizione,prezzo,categoria,erogatore);

            prodottoCompositoNuovo.addSottoProdotti(prodotti);

            ExecuteResult<Boolean> result = ArticoloBusiness.addArticolo(prodottoCompositoNuovo, ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO, files, puntoVendita.getId());
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(frame,"Prodotto Composito creato con successo", "Prodotto Composito creato", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "C'è stato un errore nella creazione del prodotto composito", "Errore creazione prodotto", JOptionPane.ERROR_MESSAGE);
            }
            frame.mostraMain();
        } else if (CREA_SERVIZIO.equals(action)){
            String nome = fieldNome.getText();
            String descrizione = fieldDescrizione.getText();
            Float prezzo = Float.parseFloat(fieldPrezzo.getText());
            Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
            Erogatore erogatore = (Erogatore) erogatoreBox.getSelectedItem();
            ArrayList<File> files = new ArrayList<>();
            if (immagini!=null){
                files = new ArrayList<>(Arrays.asList(immagini));
            }

            Servizio servizioNuovo = new Servizio(nome,descrizione,prezzo,categoria,erogatore);

            ExecuteResult<Boolean> result = ArticoloBusiness.addArticolo(servizioNuovo, ArticoloBusiness.TipoArticolo.SERVIZIO, files, puntoVendita.getId());
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(frame,"Servizio creato con successo", "Servizio creato", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "C'è stato un errore nella creazione del servizio", "Errore creazione servizio", JOptionPane.ERROR_MESSAGE);
            }
            frame.mostraMain();
        } else if(TO_CREA_CATEGORIA.equals(action)) {
            frame.mostraCreaCategoria();
        } else if (CREA_CATEGORIA.equals(action)){
            String nome = fieldNome.getText();
            Categoria.TipoCategoria tipoCategoria = (Categoria.TipoCategoria) tipoBox.getSelectedItem();
            Categoria categoriaNuova = new Categoria(nome, tipoCategoria);
            ExecuteResult<Boolean> result = ArticoloBusiness.creaCategoria(categoriaNuova);
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(frame,"Categoria creata con successo", "Categoria creata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraCreaArticolo();
            } else if (result.getResult()== ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(frame, "C'è stato un errore nella creazione della categoria", "Errore creazione categoria", JOptionPane.ERROR_MESSAGE);
            }
        } else if (TO_CREA_EROGATORE.equals(action)){
            frame.mostraCreaErogatore();
        } else if (CREA_EROGATORE.equals(action)){
            String nome = fieldNome.getText();
            String sito = fieldSito.getText();
            String nazione = fieldNazione.getText();
            String citta = fieldCitta.getText();
            String cap = fieldCap.getText();
            String via = fieldVia.getText();
            String civico = fieldCivico.getText();
            Indirizzo indirizzo = new Indirizzo(nazione, citta, cap, via, Integer.parseInt(civico));
            Erogatore erogatoreNuovo = new Erogatore(nome, sito, indirizzo);
            ExecuteResult<Boolean> result = ArticoloBusiness.creaErogatore(erogatoreNuovo);
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(frame,"Erogatore creato con successo", "Erogatore creato", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraCreaArticolo();
            } else if (result.getResult()== ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(frame, "C'è stato un errore nella creazione dell'erogatore", "Errore creazione erogatore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
