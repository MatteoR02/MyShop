package View.ListeAcquisto;

import Model.Articolo;
import Model.IProdotto;
import Model.ListaAcquisto;
import View.Listeners.Builders.ClienteListenerBuilder;
import View.Listeners.ClienteListener;
import View.MainPage;
import View.ViewModel.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ListaTablePanel extends JPanel {

    public ListaTablePanel(ListaAcquisto lista, MainPage frame){

        setLayout(new BorderLayout());

        Map<Articolo, Integer> articoli = lista.getArticoli();
        Iterator<Map.Entry<Articolo, Integer>> iterator = articoli.entrySet().iterator();

        ArrayList<RigaArticoloLista> righe = new ArrayList<>();


        while (iterator.hasNext()){
            Map.Entry<Articolo,Integer> entry = iterator.next();
            RigaArticoloLista riga = new RigaArticoloLista();
            riga.setIdArticolo(entry.getKey().getId());
            riga.setNome(entry.getKey().getNome());
            riga.setQuantita(entry.getValue());
            riga.setPrezzo(entry.getKey().getPrezzo() * entry.getValue());
            riga.setCategoria(entry.getKey().getCategoria().getNome());
            riga.setErogatore(entry.getKey().getErogatore().getNome());
            if (entry.getKey() instanceof IProdotto){
                riga.setDisponibile(lista.isProdottoDisponibile((IProdotto) entry.getKey()));
            }
            riga.setSelezionato(false);
            righe.add(riga);
        }

        ListaTableModel listaTableModel = new ListaTableModel(righe);
        JTable table = new JTable(listaTableModel);

        TableColumnModel colModel = table.getColumnModel();
        TableColumn col = colModel.getColumn(2);
        col.setCellEditor(new SpinnerColoumnModel());

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        //add(scrollPane, BorderLayout.CENTER);

        float costoTotale = 0;

        for (Articolo art: lista.getArticoli().keySet() ) {
            costoTotale+= art.getPrezzo()*lista.getArticoli().get(art);
        }

        String costoTotaleFormattato = String.format("%.2f", costoTotale);

        JLabel statoLista = new JLabel("Stato lista: " + lista.getStatoPagamento());
        JLabel prezzoLista = new JLabel("Costo totale: " + costoTotaleFormattato + " €");

        JLabel suggerimento = new JLabel("Per modificare la quantità degli articoli nella lista basta cliccare due volte sul campo 'Quantita'");

        JPanel pannello = new JPanel(new BorderLayout());
        JPanel sudPannello = new JPanel(new FlowLayout());
        JPanel nordPannello = new JPanel(new FlowLayout());

        nordPannello.add(suggerimento);

        sudPannello.add(statoLista);
        sudPannello.add(prezzoLista);

        pannello.add(scrollPane, BorderLayout.CENTER);
        pannello.add(sudPannello, BorderLayout.SOUTH);
        pannello.add(nordPannello, BorderLayout.NORTH);

        add(pannello, BorderLayout.CENTER);

        ClienteListener clienteListener = ClienteListenerBuilder.newBuilder(frame).tableLista(table).listaAcq(lista).build();

        JPanel pulsantiAzioneTabella = new JPanel();
        pulsantiAzioneTabella.setLayout(new FlowLayout());

        JButton rimuoviArticoloBtn = new JButton("Rimuovi articolo");
        rimuoviArticoloBtn.setActionCommand(ClienteListener.REMOVE_FROM_LISTA);
        rimuoviArticoloBtn.addActionListener(clienteListener);

        JButton aggiornaQuantitaBtn = new JButton("Aggiorna quantità");
        aggiornaQuantitaBtn.setActionCommand(ClienteListener.UPDATE_QUANTITY_IN_LISTA);
        aggiornaQuantitaBtn.addActionListener(clienteListener);

        JButton eliminaListaBtn = new JButton("Elimina lista");
        eliminaListaBtn.setActionCommand(ClienteListener.REMOVE_LISTA);
        eliminaListaBtn.addActionListener(clienteListener);

        JButton stampaListaBtn = new JButton("Stampa lista");
        stampaListaBtn.setActionCommand(ClienteListener.PRINT_LIST);
        stampaListaBtn.addActionListener(clienteListener);

        JButton toPrenotaBtn = new JButton("Prenota articolo/i");
        toPrenotaBtn.setActionCommand(ClienteListener.TO_PRENOTA_ARTICOLI);
        toPrenotaBtn.addActionListener(clienteListener);

        if (lista.getStatoPagamento()!= ListaAcquisto.StatoPagamentoType.PAGATO){
            pulsantiAzioneTabella.add(rimuoviArticoloBtn);
            pulsantiAzioneTabella.add(aggiornaQuantitaBtn);
            pulsantiAzioneTabella.add(eliminaListaBtn);
            pulsantiAzioneTabella.add(stampaListaBtn);
            pulsantiAzioneTabella.add(toPrenotaBtn);
        }

        add(pulsantiAzioneTabella, BorderLayout.SOUTH);

    }

}
