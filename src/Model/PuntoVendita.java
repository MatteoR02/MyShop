package Model;

import java.util.List;

public class PuntoVendita {

    private int id;
    private String nome;
    private Indirizzo indirizzo;
    private List<Magazzino> magazzini;
    private List<Articolo> articoli;

    public PuntoVendita(String nome, Indirizzo indirizzo, List<Magazzino> magazzini, List<Articolo> articoli) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.magazzini = magazzini;
        this.articoli = articoli;
    }

    public PuntoVendita(String nome, Indirizzo indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public List<Magazzino> getMagazzini() {
        return magazzini;
    }

    public void setMagazzini(List<Magazzino> magazzini) {
        this.magazzini = magazzini;
    }

    public List<Articolo> getArticoli() {
        return articoli;
    }

    public void setArticoli(List<Articolo> articoli) {
        this.articoli = articoli;
    }

    public PuntoVendita() {
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

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
