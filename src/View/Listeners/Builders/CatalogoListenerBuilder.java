package View.Listeners.Builders;

import Model.Recensione;
import View.Listeners.CatalogoListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.util.ArrayList;

public final class CatalogoListenerBuilder {

    private MainPage frame;
    private ComponenteCatalogo comp;
    private JComboBox listeBox;
    private JSpinner spinnerQuantita;
    private JDialog dialog;
    private JTextField fieldTitolo;
    private JTextArea fieldTesto;
    private JSlider sliderValutazione;
    private ArrayList<Recensione> listaRecensioni;
    private JComboBox ordinamentoBox;
    private JComboBox categoriaBox;
    private Recensione recensione;
    private JLabel labelImm;
    private JLabel labelIndex;

    public CatalogoListenerBuilder(MainPage frame){
        this.frame = frame;
    }

    public static CatalogoListenerBuilder newBuilder(MainPage frame){
        return new CatalogoListenerBuilder(frame);
    }

    public CatalogoListenerBuilder compCatalogo(ComponenteCatalogo componenteCatalogo){
        this.comp = componenteCatalogo;
        return this;
    }

    public CatalogoListenerBuilder comboBoxListe(JComboBox listeBox){
        this.listeBox = listeBox;
        return this;
    }

    public CatalogoListenerBuilder spinnerQuantita(JSpinner spinnerQuantita){
        this.spinnerQuantita = spinnerQuantita;
        return this;
    }

    public CatalogoListenerBuilder dialog(JDialog dialog){
        this.dialog = dialog;
        return this;
    }

    public CatalogoListenerBuilder fieldTitolo(JTextField fieldTitolo){
        this.fieldTitolo = fieldTitolo;
        return this;
    }

    public CatalogoListenerBuilder fieldTesto(JTextArea fieldTesto){
        this.fieldTesto = fieldTesto;
        return this;
    }

    public CatalogoListenerBuilder sliderValutazione(JSlider sliderValutazione){
        this.sliderValutazione = sliderValutazione;
        return this;
    }

    public CatalogoListenerBuilder arrayRecensioni(ArrayList<Recensione> listaRecensioni){
        this.listaRecensioni = listaRecensioni;
        return this;
    }

    public CatalogoListenerBuilder comboBoxOrdinamento(JComboBox ordinamentoBox){
        this.ordinamentoBox = ordinamentoBox;
        return this;
    }

    public CatalogoListenerBuilder comboBoxCategoria(JComboBox categoriaBox){
        this.categoriaBox = categoriaBox;
        return this;
    }

    public CatalogoListenerBuilder recensione(Recensione recensione){
        this.recensione = recensione;
        return this;
    }

    public CatalogoListenerBuilder labelImmagine(JLabel labelImm){
        this.labelImm = labelImm;
        return this;
    }

    public CatalogoListenerBuilder labelIndex(JLabel labelIndex){
        this.labelIndex = labelIndex;
        return this;
    }

    public CatalogoListener build(){
        return new CatalogoListener(frame, comp, listeBox, spinnerQuantita, dialog,  fieldTitolo, fieldTesto, sliderValutazione,  listaRecensioni,  ordinamentoBox, categoriaBox, recensione, labelImm,labelIndex);
    }
}
