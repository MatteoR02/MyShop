package View;

import Model.IProdotto;
import Model.Prenotazione;
import View.Listeners.ClienteListener;
import View.ViewModel.PrenotazioniClienteTableModel;
import View.ViewModel.RigaPrenotazioneLista;
import View.ViewModel.SpinnerColoumnModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PrenotazioniManagerTablePanel extends JPanel {

    public PrenotazioniManagerTablePanel(Prenotazione prenotazione, MainPage frame){

        setLayout(new BorderLayout());
        Font testoFont = new Font("Arial", Font.PLAIN, 15);

        Map<IProdotto, Integer> articoli = prenotazione.getProdottiPrenotati();
        Iterator<Map.Entry<IProdotto, Integer>> iterator = articoli.entrySet().iterator();

        ArrayList<RigaPrenotazioneLista> righe = new ArrayList<>();


        while (iterator.hasNext()){
            Map.Entry<IProdotto,Integer> entry = iterator.next();
            RigaPrenotazioneLista riga = new RigaPrenotazioneLista();
            riga.setIdArticolo(entry.getKey().getId());
            riga.setNome(entry.getKey().getNome());
            riga.setQuantita(entry.getValue());
            riga.setSelezionato(false);
            righe.add(riga);
        }

        PrenotazioniClienteTableModel prenotazioniClienteTableModel = new PrenotazioniClienteTableModel(righe);
        JTable table = new JTable(prenotazioniClienteTableModel);

        TableColumnModel colModel = table.getColumnModel();
        TableColumn col = colModel.getColumn(2);
        col.setCellEditor(new SpinnerColoumnModel());

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JLabel statoPrenotazione = new JLabel("Stato prenotazione: " + prenotazione.getStatoPrenotazione().toString());
        JLabel dataPrenotazione = new JLabel();
        JLabel dataArrivo = new JLabel();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (prenotazione.getDataPrenotazione() != null && prenotazione.getDataArrivo()!=null){
            dataPrenotazione = new JLabel("Prenotazione effettuata il: " + sdf.format(prenotazione.getDataPrenotazione()) + " ");
            dataArrivo = new JLabel("Data di arrivo prevista per il:" + sdf.format(prenotazione.getDataArrivo()) + " ");
        }

        statoPrenotazione.setFont(testoFont);
        dataPrenotazione.setFont(testoFont);
        dataArrivo.setFont(testoFont);

        JPanel pannello = new JPanel(new BorderLayout());
        JPanel sudPannello = new JPanel(new FlowLayout());
        JPanel nordPannello = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 1));

        nordPannello.add(statoPrenotazione);
        nordPannello.add(dataPrenotazione);
        nordPannello.add(dataArrivo);


        pannello.add(scrollPane, BorderLayout.CENTER);
        pannello.add(sudPannello, BorderLayout.SOUTH);
        pannello.add(nordPannello, BorderLayout.NORTH);

        add(pannello, BorderLayout.CENTER);

        //ClienteListener clienteListener = new ClienteListener();

        JPanel pulsantiAzioneTabella = new JPanel();
        pulsantiAzioneTabella.setLayout(new FlowLayout());

        JButton rimuoviArticoloBtn = new JButton("Completa prenotazione");
        rimuoviArticoloBtn.setActionCommand(ClienteListener.REMOVE_FROM_LISTA);
        //rimuoviArticoloBtn.addActionListener(clienteListener);

        JButton annullaPrenotazioneBtn = new JButton("Annulla prenotazione");
        annullaPrenotazioneBtn.setActionCommand(ClienteListener.REMOVE_LISTA);
       // eliminaListaBtn.addActionListener(clienteListener);

        if (prenotazione.getStatoPrenotazione()== Prenotazione.StatoPrenotazione.IN_CORSO){
            pulsantiAzioneTabella.add(rimuoviArticoloBtn);
            pulsantiAzioneTabella.add(annullaPrenotazioneBtn);
        }

        add(pulsantiAzioneTabella, BorderLayout.SOUTH);

    }

}
