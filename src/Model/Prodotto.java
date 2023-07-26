package Model;

import java.util.List;

public class Prodotto extends Articolo implements IProdotto{

    private Collocazione collocazione;
    private Produttore produttore;

    public Prodotto(String nome, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria, Collocazione collocazione, Produttore produttore) {
        super(nome, prezzo, recensioni, immagini, categoria);
        this.collocazione = collocazione;
        this.produttore = produttore;
    }

    public Prodotto() {
        super();
    }

    public Collocazione getCollocazione() {
        return collocazione;
    }

    public void setCollocazione(Collocazione collocazione) {
        this.collocazione = collocazione;
    }

    public Produttore getProduttore() {
        return produttore;
    }

    public void setProduttore(Produttore produttore) {
        this.produttore = produttore;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", immagini=" + immagini +
                '}';
    }
}
