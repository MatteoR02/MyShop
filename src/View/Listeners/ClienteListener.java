package View.Listeners;

import Business.*;
import Business.Email.ListaAcquistoEmail;
import Model.*;
import View.Dialog.AddListaDialog;
import View.Dialog.AddPrenotazioneDialog;
import View.Dialog.AddToListaDialog;
import View.MainPage;
import View.ViewModel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteListener implements ActionListener {

    public final static String TO_LISTE_BTN = "to_liste_btn";
    public final static String REMOVE_FROM_LISTA = "remove_from_lista";
    public final static String UPDATE_QUANTITY_IN_LISTA = "update_quantity_in_lista";
    public final static String PRINT_LIST = "print_list";
    public final static String CREATE_NEW_LISTA = "create_new_lista";
    public final static String REMOVE_LISTA = "remove_lista";
    public final static String TO_CREATE_NEW_LISTA = "to_create_new_lista";
    public final static String TO_PROFILE_BTN = "to_profile_btn";
    public final static String MOSTRA_LISTE_PAGATE = "mostra_liste_pagate";
    public final static String NASCONDI_LISTE_PAGATE = "nascondi_liste_pagate";
    public final static String TO_PRENOTA_ARTICOLI = "to_prenota_articoli";
    public final static String PRENOTA_ARTICOLI = "prenota_articoli";
    public final static String TO_PRENOTAZIONI = "to_prenotazioni";
    public final static String UPDATE_QUANTITY_IN_PRENOTAZIONE = "update_quantity_in_prenotazione";
    public final static String ANNULLA_PRENOTAZIONE = "annulla_prenotazione";
    public final static String REMOVE_FROM_PRENOTAZIONE = "remove_from_prenotazione";

    private MainPage frame;
    private JTable table;
    private JTextField newLista;
    private ListaAcquisto lista;
    private JDialog dialog;
    private ComponenteCatalogo comp;
    private DefaultListModel<ListaAcquisto> listModel;
    private ArrayList<ListaAcquisto> liste;
    private ArrayList<IProdotto> articoli;
    private ArrayList<JSpinner> spinners;
    private JButton button;
    private Prenotazione prenotazione;

    public ClienteListener(MainPage frame, JTable table, JTextField newLista, ListaAcquisto lista, JDialog dialog, ComponenteCatalogo comp, DefaultListModel<ListaAcquisto> listModel, ArrayList<ListaAcquisto> liste, ArrayList<IProdotto> articoli, ArrayList<JSpinner> spinners, JButton button, Prenotazione prenotazione) {
        this.frame = frame;
        this.table = table;
        this.newLista = newLista;
        this.lista = lista;
        this.dialog = dialog;
        this.comp = comp;
        this.listModel = listModel;
        this.liste = liste;
        this.articoli = articoli;
        this.spinners = spinners;
        this.button = button;
        this.prenotazione = prenotazione;
    }


    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (TO_LISTE_BTN.equals(action)) {
            frame.mostraListe();
        } else if(UPDATE_QUANTITY_IN_LISTA.equals(action)){
            ArrayList<RigaArticoloLista> righeLista = new ArrayList<>();
            ListaTableModel tModel = (ListaTableModel) table.getModel();
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            for (int i=0; i < table.getRowCount(); i++){
                righeLista.add(tModel.getRighe().get(i));
                RigaArticoloLista riga = righeLista.get(i);
                Articolo art = ArticoloBusiness.getArticolo(riga.getIdArticolo()).getSingleObject();
                result = ListaAcquistoBusiness.addOrRemoveToLista(art, lista, riga.getQuantita(),(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                lista.getArticoli().replace(art,riga.getQuantita());
                tModel.getRighe().get(i).setDisponibile(lista.isProdottoDisponibile((IProdotto) art));
            }
            JOptionPane.showMessageDialog(frame,"Le quantita' degli articoli nella lista sono state aggiornate", "Quantità modificata", JOptionPane.INFORMATION_MESSAGE);
            tModel.fireTableDataChanged();
            frame.repaint();
            frame.revalidate();

        } else if(PRINT_LIST.equals(action)){
            ListaAcquistoEmail listaAcquistoEmail = new ListaAcquistoEmail(((Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER)).getPersona().getEmail(), lista);
            listaAcquistoEmail.inviaEmail();
            JOptionPane.showMessageDialog(dialog,"Lista d'acquisto " + lista.getNome() + " inviata alla tua email", "Lista stampata", JOptionPane.INFORMATION_MESSAGE);


        } else if (REMOVE_FROM_LISTA.equals(action)){
            Map<RigaArticoloLista, Integer> mapArticoloLista = new HashMap<>();
            //ArrayList<RigaArticoloLista> righeArticoloLista = new ArrayList<>();
            ListaTableModel tModel = (ListaTableModel) table.getModel();
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            for (int i=0; i < table.getRowCount(); i++) {
                boolean check;
                RigaArticoloLista rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!mapArticoloLista.containsKey(rigaSelezionata)) {
                       // righeArticoloLista.add(rigaSelezionata);
                        mapArticoloLista.put(rigaSelezionata,i);
                    }
                }
            }
            if (!mapArticoloLista.keySet().isEmpty()){
                for (RigaArticoloLista riga: mapArticoloLista.keySet() ) {
                    int idArticolo = riga.getIdArticolo();
                    Articolo art = ArticoloBusiness.getArticolo(idArticolo).getSingleObject();
                    System.out.println("Articolo selezionato: " + art.getNome());
                    result = ListaAcquistoBusiness.addOrRemoveToLista(art, lista, 0,(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    int indice = mapArticoloLista.get(riga);
                    tModel.getRighe().remove(riga);
                    tModel.fireTableRowsDeleted(indice,indice);
                    JOptionPane.showMessageDialog(frame,result.getMessage(), "Articolo rimosso dalla lista", JOptionPane.INFORMATION_MESSAGE);
                    tModel.fireTableDataChanged();
                    frame.repaint();
                    frame.revalidate();
                }
            } else {
                JOptionPane.showMessageDialog(frame,"Nessun articolo selezionato", "Errore lista", JOptionPane.ERROR_MESSAGE);
            }
        } else if(CREATE_NEW_LISTA.equals(action)){
            String nuovaLista = newLista.getText();
            if (dialog instanceof AddToListaDialog){
                if (nuovaLista.isBlank()){
                    JOptionPane.showMessageDialog(dialog,"Inserire un nome per creare una nuova lista", "Errore creazione lista", JOptionPane.ERROR_MESSAGE);
                } else {
                    ExecuteResult<Boolean> result = ListaAcquistoBusiness.addLista(nuovaLista, (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    JOptionPane.showMessageDialog(dialog,"Nuova lista creata", "Nuova lista", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    UtenteBusiness.getListeOfCliente((Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    dialog = new AddToListaDialog(frame, "Aggiungi alla lista", comp);
                }
            } else if(dialog instanceof AddListaDialog) {
                if (nuovaLista.isBlank()) {
                    JOptionPane.showMessageDialog(dialog, "Inserire un nome per creare una nuova lista", "Errore creazione lista", JOptionPane.ERROR_MESSAGE);
                } else {
                    ExecuteResult<Boolean> result = ListaAcquistoBusiness.addLista(nuovaLista, (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    JOptionPane.showMessageDialog(dialog, "Nuova lista creata", "Nuova lista", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    UtenteBusiness.getListeOfCliente((Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    frame.mostraListe();
                }
            }

        } else if(REMOVE_LISTA.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare la lista?", "Eliminare lista?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = ListaAcquistoBusiness.removeLista(lista.getId());
                JOptionPane.showMessageDialog(frame,"Lista eliminata", "Lista eliminata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraListe();
            }
        } else if(TO_CREATE_NEW_LISTA.equals(action)){
            AddListaDialog addToListaDialog = new AddListaDialog(frame, "Aggiungi alla lista");
        } else if (MOSTRA_LISTE_PAGATE.equals(action)){
            for (ListaAcquisto listaAcq : liste) {
                if (listaAcq.getStatoPagamento() == ListaAcquisto.StatoPagamentoType.PAGATO){
                    listModel.addElement(listaAcq);
                }
            }
            button.setActionCommand(NASCONDI_LISTE_PAGATE);
            button.setText("Nascondi liste pagate");
            frame.validate();
            frame.repaint();
        } else if (NASCONDI_LISTE_PAGATE.equals(action)){
            for (ListaAcquisto listaAcq : liste) {
                if (listaAcq.getStatoPagamento() == ListaAcquisto.StatoPagamentoType.PAGATO){
                    listModel.removeElement(listaAcq);
                }
            }
            button.setActionCommand(MOSTRA_LISTE_PAGATE);
            button.setText("Mostra liste pagate");
            frame.validate();
            frame.repaint();
        } else if (TO_PRENOTA_ARTICOLI.equals(action)){
            ArrayList<IProdotto> articoli = new ArrayList<>();

            ArrayList<RigaArticoloLista> righeArticoli = new ArrayList<>();
            ListaTableModel tModel = (ListaTableModel) table.getModel();

            for (int i=0; i < table.getRowCount(); i++) {
                boolean check;
                RigaArticoloLista rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!righeArticoli.contains(rigaSelezionata)) {
                        righeArticoli.add(rigaSelezionata);
                    }
                }
            }
            for (RigaArticoloLista riga: righeArticoli ) {
                int idArticolo = riga.getIdArticolo();
                if (ArticoloBusiness.articoloCheckType(riga.getIdArticolo()) == ArticoloBusiness.TipoArticolo.SERVIZIO){
                    JOptionPane.showMessageDialog(dialog, "Non puoi prenotare un servizio", "Errore prenotazione", JOptionPane.ERROR_MESSAGE);
                } else {
                    articoli.add((IProdotto) ArticoloBusiness.getArticolo(idArticolo).getSingleObject());
                }
            }

            if (righeArticoli.isEmpty()){
                JOptionPane.showMessageDialog(dialog, "Selezionare gli articoli da prenotare", "Errore prenotazione", JOptionPane.ERROR_MESSAGE);
            } else if (!articoli.isEmpty()){
                AddPrenotazioneDialog addPrenotazioneDialog = new AddPrenotazioneDialog(frame, "Prenota articoli", articoli);
            }
        } else if (PRENOTA_ARTICOLI.equals(action)){
            HashMap<IProdotto, Integer> articoliQuant = new HashMap<>();
            int i = 0;
            for (IProdotto prod : articoli ) {
                articoliQuant.put(prod, (Integer) spinners.get(i).getValue());
                i++;
            }
            ExecuteResult<Boolean> result = PrenotazioneBusiness.prenotaProdotti(articoliQuant);
            if (result.getResult() == ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(dialog,"Prenotazione effettuata con successo", "Prenotazione effettuata", JOptionPane.INFORMATION_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(dialog, "Errore nella prenotazione", "Errore prenotazione", JOptionPane.ERROR_MESSAGE);
            } else if ( result.getResult() == ExecuteResult.ResultStatement.OK_WITH_WARNINGS){
                JOptionPane.showMessageDialog(dialog, "Uno dei prodotti selezionati è stato già prenotato", "Errore prenotazione", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();
        } else if (TO_PRENOTAZIONI.equals(action)){
            frame.mostraPrenotazioni();
        } else if (UPDATE_QUANTITY_IN_PRENOTAZIONE.equals(action)){
            ArrayList<RigaPrenotazioneLista> righePrenotazione = new ArrayList<>();
            PrenotazioniClienteTableModel tModel = (PrenotazioniClienteTableModel) table.getModel();
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            for (int i=0; i < table.getRowCount(); i++){
                righePrenotazione.add(tModel.getRighe().get(i));
                RigaPrenotazioneLista riga = righePrenotazione.get(i);
                Articolo art = ArticoloBusiness.getArticolo(riga.getIdArticolo()).getSingleObject();
                result = PrenotazioneBusiness.addOrRemoveToPrenotazione(art, prenotazione, riga.getQuantita(),(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                prenotazione.getProdottiPrenotati().replace((IProdotto) art,riga.getQuantita());
            }
            JOptionPane.showMessageDialog(frame,"Le quantità dei prodotti nella prenotazione sono state aggiornate", "Quantità modificata", JOptionPane.INFORMATION_MESSAGE);
            tModel.fireTableDataChanged();
            frame.repaint();
            frame.revalidate();
        } else if (ANNULLA_PRENOTAZIONE.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler annullare la prenotazione?", "Annullare prenotazione?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = PrenotazioneBusiness.removePrenotazione(prenotazione.getId());
                JOptionPane.showMessageDialog(frame,"Prenotazione annullata", "Prenotazione annullata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraPrenotazioni();
            }
        } else if (REMOVE_FROM_PRENOTAZIONE.equals(action)){
            Map<RigaPrenotazioneLista, Integer> mapProdottoLista = new HashMap<>();
            PrenotazioniClienteTableModel tModel = (PrenotazioniClienteTableModel) table.getModel();
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            for (int i=0; i < table.getRowCount(); i++) {
                boolean check;
                RigaPrenotazioneLista rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!mapProdottoLista.containsKey(rigaSelezionata)) {
                        mapProdottoLista.put(rigaSelezionata,i);
                    }
                }
            }
            if (!mapProdottoLista.keySet().isEmpty()){
                for (RigaPrenotazioneLista riga: mapProdottoLista.keySet() ) {
                    int idArticolo = riga.getIdArticolo();
                    Articolo art = ArticoloBusiness.getArticolo(idArticolo).getSingleObject();
                    result = PrenotazioneBusiness.addOrRemoveToPrenotazione(art, prenotazione, 0,(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
                    int indice = mapProdottoLista.get(riga);
                    tModel.getRighe().remove(riga);
                    tModel.fireTableRowsDeleted(indice,indice);
                    JOptionPane.showMessageDialog(frame,result.getMessage(), "Prodotto rimosso dalla prenotazione", JOptionPane.INFORMATION_MESSAGE);
                    tModel.fireTableDataChanged();
                    frame.repaint();
                    frame.revalidate();
                }
            } else {
                JOptionPane.showMessageDialog(frame,"Nessun prodotto selezionato", "Errore prenotazione", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
