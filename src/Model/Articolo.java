package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Articolo {

    protected int id;
    protected String nome;
    protected String descrizione;
    protected Float prezzo;
    protected List<Recensione> recensioni;
    protected List<Foto> immagini;
    protected Categoria categoria;
    protected Erogatore erogatore;

    public Articolo(String nome, String descrizione, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria, Erogatore erogatore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.recensioni = recensioni;
        this.immagini = immagini;
        this.categoria = categoria;
        this.erogatore = erogatore;
    }

    public Articolo(String nome, String descrizione, Float prezzo, Categoria categoria, Erogatore erogatore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.erogatore = erogatore;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Erogatore getErogatore() {
        return erogatore;
    }

    public void setErogatore(Erogatore erogatore) {
        this.erogatore = erogatore;
    }

    public int getPunteggioTotale(){
        int punteggioTotale = 0;
        for (Recensione rec: recensioni  ) {
            punteggioTotale += rec.getPunteggioInInteger();
        }
        return punteggioTotale;
    }

    public Float getMediaRecensioni(){
        if (recensioni.isEmpty()) return 0.0f;
        if (this.getPunteggioTotale() == 0) return 0.0f;
        Float media = 1.0f * this.getPunteggioTotale() / recensioni.toArray().length;
        return media;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articolo articolo = (Articolo) o;
        return id == articolo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Articolo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", immagini=" + immagini +
                '}';
    }
}