package View.Listeners;

import Business.*;
import Business.Email.MessaggioManagerEmail;
import Model.*;
import View.MainPage;
import View.ViewModel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

public class ManagerListener implements ActionListener {

    public final static String GESTISCI_ART_BTN = "gestisci_art_btn";
    public final static String GESTISCI_CLIENTI_BTN = "gestisci_clienti_btn";
    public final static String BLOCK_CLIENTE = "block_cliente";
    public final static String UNLOCK_CLIENTE = "unlock_cliente";
    public final static String TO_RISPONDI_RECENSIONE = "to_rispondi_recensione";
    public final static String RISPONDI_RECENSIONE = "rispondi_recensione";
    public final static String REMOVE_RECENSIONE = "remove_recensione";
    public final static String GESTISCI_QUANTITA = "gestisci_quantità";
    public final static String RIFORNISCI_PRODOTTO = "rifornisci_prodotto";
    public final static String TO_INVIA_MESSAGGIO = "to_invia_messaggio";
    public final static String ALLEGA_FILE = "allega_file";
    public final static String INVIA_MESSAGGIO = "invia_messaggio";
    public final static String TO_PRENOTAZIONI = "to_prenotazioni";
    public final static String ANNULLA_PRENOTAZIONE = "annulla_prenotazione";
    public final static String COMPLETA_PRENOTAZIONE = "completa_prenotazione";
   // public final static String
   // public final static String


    private MainPage frame;
    private JTable table;
    private ComponenteCatalogo comp;
    private JDialog dialog;
    private Prodotto prodotto;
    private JSpinner spinner;

    private File allegato;

    private JTextField oggettoField;
    private JTextArea corpoField;
    private RigaCliente rigaCliente;
    private JLabel labelAllegato;

    private JTextField fieldTitolo;
    private JTextArea fieldTesto;
    private Recensione recensione;

    private Prenotazione prenotazione;


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

    public ManagerListener(MainPage frame, Prenotazione prenotazione) {
        this.frame = frame;
        this.prenotazione = prenotazione;
    }

    public ManagerListener(MainPage frame, JDialog dialog, JTextField fieldTitolo, JTextArea fieldTesto, ComponenteCatalogo comp, Recensione recensione) {
        this.frame = frame;
        this.comp = comp;
        this.dialog = dialog;
        this.fieldTitolo = fieldTitolo;
        this.fieldTesto = fieldTesto;
        this.recensione = recensione;
    }

    public ManagerListener(MainPage frame, JDialog dialog, Prodotto prodotto, JSpinner spinner) {
        this.frame = frame;
        this.dialog = dialog;
        this.prodotto = prodotto;
        this.spinner = spinner;
    }

    public ManagerListener(JDialog dialog, JTextField oggettoField, JTextArea corpoField, RigaCliente rigaCliente, JLabel labelAllegato) {
        this.dialog = dialog;
        this.oggettoField = oggettoField;
        this.corpoField = corpoField;
        this.rigaCliente = rigaCliente;
        this.labelAllegato = labelAllegato;
    }

    public ManagerListener(MainPage frame, ComponenteCatalogo comp, Recensione recensione) {
        this.frame = frame;
        this.comp = comp;
        this.recensione = recensione;
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
        } else if (TO_INVIA_MESSAGGIO.equals(action)){
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
            if (righeClienti.toArray().length > 1){
                JOptionPane.showMessageDialog(frame, "Selezionare un solo cliente a cui inviare un messaggio", "Errore messaggio", JOptionPane.ERROR_MESSAGE);
            } else if (righeClienti.toArray().length == 0) {
                JOptionPane.showMessageDialog(frame, "Selezionare un cliente", "Errore messaggio", JOptionPane.ERROR_MESSAGE);
            }else {
                InviaMessaggioDialog inviaMessaggioDialog = new InviaMessaggioDialog(frame, "Messaggio", righeClienti.get(0));
            }
        } else if (ALLEGA_FILE.equals(action)){
            JFileChooser fileChooser = new JFileChooser();
            int risposta = fileChooser.showOpenDialog(dialog);
            if (risposta == JFileChooser.APPROVE_OPTION){
                allegato = new File(fileChooser.getSelectedFile().getAbsolutePath());
                labelAllegato.setText(allegato.getName());
            }

        } else if (INVIA_MESSAGGIO.equals(action)){
            String oggetto = oggettoField.getText();
            String corpo = corpoField.getText();
            Manager m = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            MessaggioManagerEmail messaggioManagerEmail = new MessaggioManagerEmail(rigaCliente.getEmail(), oggetto, corpo, allegato, m);
            messaggioManagerEmail.inviaEmail();
            JOptionPane.showMessageDialog(dialog,"Messaggio inviato", "Messaggio inviato", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } else if (TO_RISPONDI_RECENSIONE.equals(action)){
            AddRecensioneDialog addRecensioneDialog = new AddRecensioneDialog(frame, "Invia recensione", comp, true, recensione);
        } else if (RISPONDI_RECENSIONE.equals(action)){
            Date dataAttuale = new java.sql.Date(System.currentTimeMillis());
            Utente u = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            ExecuteResult<Boolean> result = new ExecuteResult<>();
            Manager m = (Manager) u;
            Recensione newRec = new Recensione(fieldTitolo.getText(), fieldTesto.getText(), Recensione.Punteggio.ECCELLENTE , dataAttuale, null, m.getId());
            newRec.setIdRecensione(recensione.getId());
            result = RecensioneBusiness.addRisposta(newRec, recensione.getId());
            System.out.println(result.getResult());
            System.out.println(result.getMessage());
            if (result.getResult() == ExecuteResult.ResultStatement.OK_WITH_WARNINGS){
                JOptionPane.showMessageDialog(dialog, "Hai già lasciato una risposta per questo articolo", "Risposta non disponibile", JOptionPane.ERROR_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.NOT_OK){
                JOptionPane.showMessageDialog(dialog, "Non puoi rispondere a questa recensione", "Risposta non disponibile", JOptionPane.ERROR_MESSAGE);
            } else if (result.getResult() == ExecuteResult.ResultStatement.OK){
                dialog.dispose();
                JOptionPane.showMessageDialog(dialog,result.getMessage(), "Risposta inviata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraArticolo(comp, false, null);
            }
        } else if (TO_PRENOTAZIONI.equals(action)){
            frame.mostraPrenotazioni();
        } else if (ANNULLA_PRENOTAZIONE.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Annullare prenotazione?", "Eliminare prenotazione?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = PrenotazioneBusiness.removePrenotazione(prenotazione.getId());
                JOptionPane.showMessageDialog(frame,"Prenotazione annullata", "Prenotazione annullata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraPrenotazioni();
            }
        } else if (COMPLETA_PRENOTAZIONE.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Completare prenotazione?", "Completare prenotazione?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0) {
                ExecuteResult<Boolean> result = PrenotazioneBusiness.changePrenotazioneStatus(prenotazione.getId(), Prenotazione.StatoPrenotazione.ARRIVATA);
                JOptionPane.showMessageDialog(frame, "Stato prenotazione cambiato", "Stato prenotazione cambiato", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraPrenotazioni();
            }
        }
    }
}
