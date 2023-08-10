package View.ViewModel;

import Business.ArticoloBusiness;
import Model.Recensione;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ComponenteCatalogo {
    private int idArticolo;
    private String nomeArticolo;
    private String descrizioneArticolo;
    private float prezzo;
    private String nomeCategoria;
    private String nomeErogatore;
    private ArticoloBusiness.TipoArticolo tipoArticolo;
    private List<ImageIcon> immagini = new ArrayList<>();
    private List<Recensione> recensioni = new ArrayList<>();

    public int getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(int idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    public String getDescrizioneArticolo() {
        return descrizioneArticolo;
    }

    public void setDescrizioneArticolo(String descrizioneArticolo) {
        this.descrizioneArticolo = descrizioneArticolo;
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

    public String getNomeErogatore() {
        return nomeErogatore;
    }

    public void setNomeErogatore(String nomeErogatore) {
        this.nomeErogatore = nomeErogatore;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArticoloBusiness.TipoArticolo getTipoArticolo() {
        return tipoArticolo;
    }

    public void setTipoArticolo(ArticoloBusiness.TipoArticolo tipoArticolo) {
        this.tipoArticolo = tipoArticolo;
    }
}