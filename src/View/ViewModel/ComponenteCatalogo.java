package View.ViewModel;

import Business.NotWorking_ArticoloBusiness;
import Model.Recensione;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ComponenteCatalogo {
    private int id;
    private String nomeArticolo;
    private float prezzo;
    private String nomeCategoria;
    private String nomeProduttore;
    private NotWorking_ArticoloBusiness.TipoArticolo tipoArticolo;
    private List<ImageIcon> immagini = new ArrayList<>();
    private List<Recensione> recensioni = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public List<ImageIcon> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<ImageIcon> immagini) {
        this.immagini = immagini;
    }

    public void setDefaultImmagine(ImageIcon immagine){
        immagini.add(immagine);
        immagini.set(0, immagine);
    }

    public String getNomeProduttore() {
        return nomeProduttore;
    }

    public void setNomeProduttore(String nomeProduttore) {
        this.nomeProduttore = nomeProduttore;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public NotWorking_ArticoloBusiness.TipoArticolo getTipoArticolo() {
        return tipoArticolo;
    }

    public void setTipoArticolo(NotWorking_ArticoloBusiness.TipoArticolo tipoArticolo) {
        this.tipoArticolo = tipoArticolo;
    }
}