package View.Listeners.Builders;

import Model.PuntoVendita;
import View.Listeners.AdminListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public final class AdminListenerBuilder {
    private MainPage frame;

    private JComboBox puntoVenditaBox;

    private ComponenteCatalogo componenteCatalogo;

    private PuntoVendita puntoVendita;

    private JTextArea fieldDescrizione;
    private JTextField fieldPrezzo;
    private JTextField fieldCorsia, fieldScaffale;
    private JComboBox magazzinoBox;
    private JComboBox categoriaBox;
    private JComboBox erogatoreBox;
    private JComboBox tipoBox;
    private JLabel labelImmagini;

    private JTable tableProdotti;

    private JTextField fieldNome, fieldCognome, fieldEmail, fieldTelefono, fieldUsername;
    private JPasswordField fieldPassword;
    private JDateChooser dataNascita;
    private JTextField fieldSito, fieldNazione, fieldCitta, fieldCap, fieldVia, fieldCivico;

    public AdminListenerBuilder(MainPage frame){
        this.frame = frame;
    }

    public static AdminListenerBuilder newBuilder(MainPage frame){
        return new AdminListenerBuilder(frame);
    }

    public AdminListenerBuilder comboBoxPV(JComboBox selPuntoVenditaBox){
        this.puntoVenditaBox = selPuntoVenditaBox;
        return this;
    }

    public AdminListenerBuilder compCatalogo(ComponenteCatalogo componenteCatalogo){
        this.componenteCatalogo = componenteCatalogo;
        return this;
    }

    public AdminListenerBuilder puntoVendita(PuntoVendita puntoVendita){
        this.puntoVendita = puntoVendita;
        return this;
    }

    public AdminListenerBuilder fieldNome(JTextField fieldNome){
        this.fieldNome = fieldNome;
        return this;
    }

    public AdminListenerBuilder fieldDescrizione(JTextArea fieldDescrizione){
        this.fieldDescrizione = fieldDescrizione;
        return this;
    }

    public AdminListenerBuilder fieldPrezzo(JTextField fieldPrezzo){
        this.fieldPrezzo = fieldPrezzo;
        return this;
    }

    public AdminListenerBuilder fieldCorisa(JTextField fieldCorsia){
        this.fieldCorsia = fieldCorsia;
        return this;
    }

    public AdminListenerBuilder fieldScaffale(JTextField fieldScaffale){
        this.fieldScaffale = fieldScaffale;
        return this;
    }

    public AdminListenerBuilder comboBoxMagazzino(JComboBox magazzinoBox){
        this.magazzinoBox = magazzinoBox;
        return this;
    }

    public AdminListenerBuilder comboBoxCategoria(JComboBox categoriaBox){
        this.categoriaBox = categoriaBox;
        return this;
    }

    public AdminListenerBuilder comboBoxErogatore(JComboBox erogatoreBox){
        this.erogatoreBox = erogatoreBox;
        return this;
    }

    public AdminListenerBuilder comboBoxTipologia(JComboBox tipoBox){
        this.tipoBox = tipoBox;
        return this;
    }

    public AdminListenerBuilder labelImmagini(JLabel labelImmagini){
        this.labelImmagini = labelImmagini;
        return this;
    }

    public AdminListenerBuilder tableProdotti(JTable tableProdotti){
        this.tableProdotti = tableProdotti;
        return this;
    }

    public AdminListenerBuilder fieldCognome(JTextField fieldCognome){
        this.fieldCognome = fieldCognome;
        return this;
    }

    public AdminListenerBuilder fieldEmail(JTextField fieldEmail){
        this.fieldEmail = fieldEmail;
        return this;
    }

    public AdminListenerBuilder fieldTelefono(JTextField fieldTelefono){
        this.fieldTelefono = fieldTelefono;
        return this;
    }

    public AdminListenerBuilder fieldUsername(JTextField fieldUsername){
        this.fieldUsername = fieldUsername;
        return this;
    }

    public AdminListenerBuilder fieldPassword(JPasswordField fieldPassword){
        this.fieldPassword = fieldPassword;
        return this;
    }

    public AdminListenerBuilder dateChooserNascita(JDateChooser dataNascita){
        this.dataNascita = dataNascita;
        return this;
    }

    public AdminListenerBuilder fieldSito(JTextField fieldSito){
        this.fieldSito = fieldSito;
        return this;
    }

    public AdminListenerBuilder fieldNazione(JTextField fieldNazione){
        this.fieldNazione = fieldNazione;
        return this;
    }

    public AdminListenerBuilder fieldCitta(JTextField fieldCitta){
        this.fieldCitta = fieldCitta;
        return this;
    }

    public AdminListenerBuilder fieldCap(JTextField fieldCap){
        this.fieldCap = fieldCap;
        return this;
    }

    public AdminListenerBuilder fieldVia(JTextField fieldVia){
        this.fieldVia = fieldVia;
        return this;
    }

    public AdminListenerBuilder fieldCivico(JTextField fieldCivico){
        this.fieldCivico = fieldCivico;
        return this;
    }

    public AdminListener build(){
        return new AdminListener(frame, puntoVenditaBox, componenteCatalogo, puntoVendita, fieldDescrizione, fieldPrezzo, fieldCorsia, fieldScaffale, magazzinoBox, categoriaBox, erogatoreBox, tipoBox, labelImmagini, tableProdotti, fieldNome, fieldCognome, fieldEmail, fieldTelefono, fieldUsername, fieldPassword, dataNascita, fieldSito, fieldNazione, fieldCitta, fieldCap, fieldVia, fieldCivico);
    }
}
