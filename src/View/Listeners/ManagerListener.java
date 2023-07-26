package View.Listeners;

import Business.UtenteBusiness;
import Model.Cliente;
import View.MainPage;
import View.ViewModel.ClientiTableModel;
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
   // public final static String
   // public final static String


    private MainPage frame;
    private JTable table;


    public ManagerListener(MainPage frame) {
        this.frame = frame;
    }

    public ManagerListener(MainPage frame, JTable table){
        this.frame = frame;
        this.table = table;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (GESTISCI_ART_BTN.equals(action)) {
            frame.mostraCatalogo();
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
        }
    }
}
