package View.Listeners.Builders;

import Model.IProdotto;
import Model.ListaAcquisto;
import Model.Prenotazione;
import View.Listeners.ClienteListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.util.ArrayList;

public final class ClienteListenerBuilder {

    private MainPage frame;
    private JTable table;
    private JTextField fieldNewLista;
    private ListaAcquisto listaAcq;
    private JDialog dialog;
    private ComponenteCatalogo comp;
    private DefaultListModel<ListaAcquisto> listModel;
    private ArrayList<ListaAcquisto> arrayListeAcq;
    private ArrayList<IProdotto> arrayProdotti;
    private ArrayList<JSpinner> arraySpinners;
    private JButton buttonMostraNascondi;
    private Prenotazione prenotazione;

    public ClienteListenerBuilder(MainPage frame){
        this.frame = frame;
    }

    public static ClienteListenerBuilder newBuilder(MainPage frame){
        return new ClienteListenerBuilder(frame);
    }

    public ClienteListenerBuilder tableLista(JTable table){
        this.table = table;
        return this;
    }

    public ClienteListenerBuilder fieldNewLista(JTextField nomeNewLista){
        this.fieldNewLista = nomeNewLista;
        return this;
    }

    public ClienteListenerBuilder listaAcq(ListaAcquisto listaAcq){
        this.listaAcq = listaAcq;
        return this;
    }

    public ClienteListenerBuilder dialog(JDialog dialog){
        this.dialog = dialog;
        return this;
    }

    public ClienteListenerBuilder componenteCatalogo(ComponenteCatalogo comp){
        this.comp = comp;
        return this;
    }

    public ClienteListenerBuilder listModel(DefaultListModel listModel){
        this.listModel = listModel;
        return this;
    }

    public ClienteListenerBuilder arrayListeAcq(ArrayList<ListaAcquisto> arrayListeAcq){
        this.arrayListeAcq = arrayListeAcq;
        return this;
    }

    public ClienteListenerBuilder arrayProdotti(ArrayList<IProdotto> arrayProdotti){
        this.arrayProdotti = arrayProdotti;
        return this;
    }

    public ClienteListenerBuilder arraySpinners(ArrayList<JSpinner> arraySpinners){
        this.arraySpinners = arraySpinners;
        return this;
    }

    public ClienteListenerBuilder buttonMostraNascondi(JButton buttonMostraNascondi){
        this.buttonMostraNascondi = buttonMostraNascondi;
        return this;
    }

    public ClienteListenerBuilder prenotazione(Prenotazione prenotazione){
        this.prenotazione = prenotazione;
        return this;
    }

    public ClienteListener build (){
        return new ClienteListener(frame, table, fieldNewLista, listaAcq, dialog, comp, listModel, arrayListeAcq, arrayProdotti, arraySpinners, buttonMostraNascondi, prenotazione);
    }

}
