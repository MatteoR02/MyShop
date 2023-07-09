package Model;

import java.util.ArrayList;
import java.util.List;

public class Articolo {

    protected int id;
    protected String nome;
    protected Float prezzo;
    protected List<Recensione> recensioni;
    protected List<Foto> immagini;
    protected Categoria categoria;

    public Articolo(String nome, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.recensioni = recensioni;
        this.immagini = immagini;
        this.categoria = categoria;
    }

    public Articolo() {
        this.id = 0;
        this.nome = "";
        this.prezzo = 0F;
        this.recensioni = new ArrayList<>();
        this.immagini = new ArrayList<>();
        this.categoria = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public List<Foto> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<Foto> immagini) {
        this.immagini = immagini;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Articolo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", recensioni=" + recensioni +
                ", immagini=" + immagini +
                ", categoria=" + categoria +
                '}';
    }
}