package View;

import Model.Cliente;
import View.Listeners.ManagerListener;
import View.ViewModel.ClientiTableModel;
import View.ViewModel.RigaCliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientiTablePanel extends JPanel {

    public ClientiTablePanel(ArrayList<Cliente> clienti, MainPage frame){

        setLayout(new BorderLayout());

        ArrayList<RigaCliente> righe = new ArrayList<>();
        for (Cliente c: clienti  ) {
            RigaCliente riga = new RigaCliente();
            riga.setIdCliente(c.getId());
            riga.setNome(c.getPersona().getNome());
            riga.setCognome(c.getPersona().getCognome());
            riga.setUsername(c.getUsername());
            riga.setEmail(c.getPersona().getEmail());
            riga.setProfessione(c.getProfessione());
            riga.setCanalePreferito(c.getCanalePreferito());
            riga.setStato(c.getStato());
            riga.setSelezionato(false);
            righe.add(riga);
        }

        ClientiTableModel clientiTableModel = new ClientiTableModel(righe);
        JTable table = new JTable(clientiTableModel);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        ManagerListener managerListener = new ManagerListener(frame, table);

        JPanel pulsantiAzioneTabella = new JPanel();
        pulsantiAzioneTabella.setLayout(new FlowLayout());
        JButton bloccaUtente = new JButton("Blocca utente");
        bloccaUtente.setActionCommand(ManagerListener.BLOCK_CLIENTE);
        bloccaUtente.addActionListener(managerListener);

        JButton sbloccaUtente = new JButton("Sblocca utente");
        sbloccaUtente.setActionCommand(ManagerListener.UNLOCK_CLIENTE);
        sbloccaUtente.addActionListener(managerListener);

        JButton inviaMessaggioBtn = new JButton("Invia messaggio");
        inviaMessaggioBtn.setActionCommand(ManagerListener.TO_INVIA_MESSAGGIO);
        inviaMessaggioBtn.addActionListener(managerListener);

        bloccaUtente.setFocusPainted(false);
        sbloccaUtente.setFocusPainted(false);
        inviaMessaggioBtn.setFocusPainted(false);

        pulsantiAzioneTabella.add(bloccaUtente);
        pulsantiAzioneTabella.add(sbloccaUtente);
        pulsantiAzioneTabella.add(inviaMessaggioBtn);
        add(pulsantiAzioneTabella, BorderLayout.SOUTH);


    }

}
