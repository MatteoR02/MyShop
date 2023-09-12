package View.Listeners.Builders;

import Model.Prenotazione;
import Model.Prodotto;
import Model.Recensione;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.RigaCliente;

import javax.swing.*;

public final class ManagerListenerBuilder {

    private MainPage frame;
    private JTable table;
    private ComponenteCatalogo comp;
    private JDialog dialog;
    private Prodotto prodotto;
    private JSpinner spinner;

    private JTextField fieldOggetto;
    private JTextArea fieldCorpo;
    private RigaCliente rigaCliente;
    private JLabel labelAllegato;

    private JTextField fieldTitolo;
    private JTextArea fieldTesto;
    private Recensione recensione;

    private Prenotazione prenotazione;



    public ManagerListenerBuilder(MainPage frame){
        this.frame = frame;
    }

    public static ManagerListenerBuilder newBuilder(MainPage frame){
        return new ManagerListenerBuilder(frame);
    }

    public ManagerListenerBuilder table(JTable table){
        this.table = table;
        return this;
    }

    public ManagerListenerBuilder compCatalogo(ComponenteCatalogo componenteCatalogo){
        this.comp = componenteCatalogo;
        return this;
    }

    public ManagerListenerBuilder dialog(JDialog dialog){
        this.dialog = dialog;
        return this;
    }

    public ManagerListenerBuilder prodotto(Prodotto prodotto){
        this.prodotto = prodotto;
        return this;
    }

    public ManagerListenerBuilder spinner(JSpinner spinner){
        this.spinner = spinner;
        return this;
    }

    public ManagerListenerBuilder fieldOggetto(JTextField fieldOggetto){
        this.fieldOggetto = fieldOggetto;
        return this;
    }

    public ManagerListenerBuilder fieldCorpo(JTextArea fieldCorpo){
        this.fieldCorpo = fieldCorpo;
        return this;
    }

    public ManagerListenerBuilder rigaCliente(RigaCliente rigaCliente){
        this.rigaCliente = rigaCliente;
        return this;
    }

    public ManagerListenerBuilder labelAllegato(JLabel labelAllegato){
        this.labelAllegato = labelAllegato;
        return this;
    }

    public ManagerListenerBuilder fieldTitolo(JTextField fieldTitolo){
        this.fieldTitolo = fieldTitolo;
        return this;
    }

    public ManagerListenerBuilder fieldTesto(JTextArea fieldTesto){
        this.fieldTesto = fieldTesto;
        return this;
    }

    public ManagerListenerBuilder recensione(Recensione recensione){
        this.recensione = recensione;
        return this;
    }

    public ManagerListenerBuilder prenotazione(Prenotazione prenotazione){
        this.prenotazione = prenotazione;
        return this;
    }

    public ManagerListener build(){
        return new ManagerListener(frame,  table,  comp,  dialog,  prodotto,  spinner,  fieldOggetto,  fieldCorpo,  rigaCliente,  labelAllegato, fieldTitolo,  fieldTesto, recensione, prenotazione);
    }

}
