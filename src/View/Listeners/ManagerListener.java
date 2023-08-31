package View.Listeners;

import Business.ExecuteResult;
import Business.PuntoVenditaBusiness;
import Business.UtenteBusiness;
import Model.Cliente;
import Model.Prodotto;
import View.MainPage;
import View.ViewModel.ClientiTableModel;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.GestisciQuantitaDialog;
import View.ViewModel.RigaCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagerListener implements ActionListener {

    public final static String GESTISCI_ART_BTN = "gestisci_art_btn";
    public final static String GESTISCI_CLIENTI_BTN = "gestisci_clienti_btn";
    public final static String BLOCK_CLIENTE = "block_cliente";
    public final static String UNLOCK_CLIENTE = "unlock_cliente";
    public final static String RISPONDI_RECENSIONE = "rispondi_recensione";
    public final static String REMOVE_RECENSIONE = "remove_recensione";
    public final static String GESTISCI_QUANTITA = "gestisci_quantità";
    public final static String RIFORNISCI_PRODOTTO = "rifornisci_prodotto";
   // public final static String
   // public final static String


    private MainPage frame;
    private JTable table;
    private ComponenteCatalogo comp;
    private JDialog dialog;
    private Prodotto prodotto;
    private JSpinner spinner;


    public ManagerListener(MainPage frame) {
        this.frame = frame;
    }

    public ManagerListener(MainPage frame, JTable table){
        this.frame = frame;
        this.table = table;
    }

    public ManagerListener(MainPage frame, ComponenteCatalogo comp){
        this.frame = frame;
        this.comp = comp;
    }

    public ManagerListener(MainPage frame, JDialog dialog, Prodotto prodotto, JSpinner spinner) {
        this.frame = frame;
        this.dialog = dialog;
        this.prodotto = prodotto;
        this.spinner = spinner;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (GESTISCI_ART_BTN.equals(action)) {
            frame.mostraCatalogo(null, false);
        } else if(GESTISCI_CLIENTI_BTN.equals(action)){
            frame.mostraClientiTable();
        } else if(BLOCK_CLIENTE.equals(action)){
            ArrayList<RigaCliente> righeClienti = new ArrayList<>();
            ClientiTableModel tModel = (ClientiTableModel) table.getModel();

            for (int i=0; i < table.getRowCount(); i++) {
                boolean check;
                RigaCliente rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!righeClienti.contains(rigaSelezionata)) {
                        righeClienti.add(rigaSelezionata);
                    }
                }
            }
            for (RigaCliente riga: righeClienti ) {
                int idCliente = riga.getIdCliente();
                System.out.println("Id Cliente selezionato: " + idCliente);
                UtenteBusiness.changeClienteStatus(idCliente, Cliente.StatoUtenteType.BLOCCATO);
                tModel.fireTableDataChanged();
            }
            frame.mostraClientiTable();
        } else if(UNLOCK_CLIENTE.equals(action)){
            ArrayList<RigaCliente> righeClienti = new ArrayList<>();
            ClientiTableModel tModel = (ClientiTableModel) table.getModel();
            for (int i=0; i < table.getRowCount(); i++) {
                boolean check;
                RigaCliente rigaSelezionata = tModel.getRighe().get(i);
                check = rigaSelezionata.getSelezionato();
                if (check) {
                    if (!righeClienti.contains(rigaSelezionata)) {
                        righeClienti.add(rigaSelezionata);
                    }
                }
            }
            for (RigaCliente riga: righeClienti ) {
                int idCliente = riga.getIdCliente();
                System.out.println("Id Cliente selezionato: " + idCliente);
                UtenteBusiness.changeClienteStatus(idCliente, Cliente.StatoUtenteType.ABILITATO);
            }
            frame.mostraClientiTable();
        } else if (GESTISCI_QUANTITA.equals(action)){
            GestisciQuantitaDialog gestisciQuantitaDialog = new GestisciQuantitaDialog(frame,"Gestisci quantità", comp);
        } else if (RIFORNISCI_PRODOTTO.equals(action)){
            ExecuteResult<Boolean> result = PuntoVenditaBusiness.aggiornaQuantitaInMagazzino(prodotto, (Integer) spinner.getValue());
            if (result.getResult()== ExecuteResult.ResultStatement.OK){
                JOptionPane.showMessageDialog(dialog,"Rifornimento effettuato con successo", "Rifornimento effettuato", JOptionPane.INFORMATION_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(dialog, "Errore nel rifornimento", "Errore rifornimento", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();
        }
    }
}
