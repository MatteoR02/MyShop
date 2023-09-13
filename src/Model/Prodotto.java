package Model;

import java.util.List;

public class Prodotto extends Articolo implements IProdotto{

    private Collocazione collocazione;

    public Prodotto(String nome, String descrizione, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria, Collocazione collocazione, Erogatore erogatore) {
        super(nome, descrizione, prezzo, recensioni, immagini, categoria, erogatore);
        this.collocazione = collocazione;
    }

    public Prodotto(String nome, String descrizione, Float prezzo, Categoria categoria, Erogatore erogatore) {
        super(nome, descrizione, prezzo, categoria, erogatore);
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


    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", immagini=" + immagini +
                '}';
    }

    @Override
    public boolean isDisponibile() {
        return getCollocazione().getQuantita() > 0;
    }

    @Override
    public Iterator getIterator() {
        return null;
    }
}
