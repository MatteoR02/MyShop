package Model;

import java.util.ArrayList;
import java.util.List;

public class ProdottoComposito extends Articolo implements IProdotto{

    private Collocazione collocazione;

    private final List<IProdotto> sottoProdotti = new ArrayList<IProdotto>();

    public ProdottoComposito(String nome, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria, Collocazione collocazione) {
        super(nome, prezzo, recensioni, immagini, categoria);
        this.collocazione = collocazione;
    }

    public ProdottoComposito() {
    }

    public void addSottoProdotti(IProdotto prodotto) {
        sottoProdotti.add(prodotto);
    }

    public void addSottoProdotti(List<IProdotto> prodotti) {
        sottoProdotti.addAll(prodotti);
    }

    @Override
    public String getNome(){
        return this.nome;
    }
    @Override
    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public Float getPrezzo() {
        Float p = 0F;
        for (IProdotto prodotto : sottoProdotti) {
            p += prodotto.getPrezzo();
        }
        return p;
    }
    @Override
    public Produttore getProduttore(){
        return null;
    }

    public List<IProdotto> getSottoProdotti() {
        return sottoProdotti;
    }

    @Override
    public String toString() {
        return "ProdottoComposito{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", recensioni=" + recensioni +
                ", immagini=" + immagini +
                ", categoria=" + categoria +
                ", collocazione=" + collocazione +
                ", sottoProdotti=" + sottoProdotti +
                '}';
    }
}
