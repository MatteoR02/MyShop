package View.Listeners;

import Business.*;
import Business.Bridge.Documento;
import Business.Bridge.DocumentoListaAcquisto;
import Business.Bridge.PdfBoxAPI;
import Model.Articolo;
import Model.Cliente;
import Model.ListaAcquisto;
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
    public final static String UPDATE_QUANTITY = "update_quantity";
    public final static String PRINT_LIST = "print_list";
    public final static String CREATE_NEW_LISTA = "create_new_lista";
    public final static String REMOVE_LISTA = "remove_lista";
    public final static String TO_CREATE_NEW_LISTA = "to_create_new_lista";
    public final static String TO_PROFILE_BTN = "to_profile_btn";
    //public final static String ;

    private MainPage frame;
    private JTable table;
    private JPanel panel;
    private JTextField newLista;
    private ListaAcquisto lista;
    private JDialog dialog;
    private ComponenteCatalogo comp;

    public ClienteListener(JTextField newLista, JDialog dialog) {
        this.newLista = newLista;
        this.dialog = dialog;
    }

    public ClienteListener(MainPage frame, JPanel panel, JTable table, ListaAcquisto lista) {
        this.frame = frame;
        this.panel = panel;
        this.table = table;
        this.lista = lista;
    }

    public ClienteListener(JDialog dialog, JTextField newLista, ComponenteCatalogo comp) {
        this.dialog = dialog;
        this.newLista = newLista;
        this.comp = comp;
    }

    public ClienteListener(MainPage frame) {
        this.frame = frame;
    }

    public ClienteListener(MainPage frame, JTable table) {
        this.frame = frame;
        this.table = table;
    }

    public ClienteListener(MainPage frame, JTable table, ListaAcquisto lista) {
        this.frame = frame;
        this.table = table;
        this.lista = lista;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (TO_LISTE_BTN.equals(action)) {
            frame.mostraListe();
        } else if(UPDATE_QUANTITY.equals(action)){
            ArrayList<RigaArticoloLista> righeLista = new ArrayList<>();
            ListaTableModel tModel = (ListaTableModel) table.getModel();
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            for (int i=0; i < table.getRowCount(); i++){
                righeLista.add(tModel.getRighe().get(i));
                RigaArticoloLista riga = righeLista.get(i);
                Articolo art = ArticoloBusiness.getArticolo(riga.getIdArticolo()).getSingleObject();
                result = ListaAcquistoBusiness.addOrRemoveToLista(art, lista, riga.getQuantita(),(Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER));
            }
            JOptionPane.showMessageDialog(frame,"Le quantita' degli articoli nella lista sono state aggiornate", "Quantità modificata", JOptionPane.INFORMATION_MESSAGE);
            tModel.fireTableDataChanged();
            panel.repaint();
            panel.revalidate();

        } else if(PRINT_LIST.equals(action)){
            Documento listaPDF = new DocumentoListaAcquisto(lista, new PdfBoxAPI());
            listaPDF.invia(((Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER)).getPersona().getEmail());

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
                    panel.repaint();
                    panel.revalidate();
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
        }
    }
}
