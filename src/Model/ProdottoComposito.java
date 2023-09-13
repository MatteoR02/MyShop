package Model;

import java.util.ArrayList;
import java.util.List;

public class ProdottoComposito extends Articolo implements IProdotto{


    private final List<IProdotto> sottoProdotti = new ArrayList<IProdotto>();

    public ProdottoComposito(String nome, String descrizione, float prezzo, Categoria categoria,  Erogatore erogatore) {
        super(nome, descrizione, prezzo, categoria, erogatore);
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


    public List<IProdotto> getSottoProdotti() {
        return sottoProdotti;
    }

    @Override
    public boolean isDisponibile(){
        ArrayList<Boolean> disponibilita = new ArrayList<>();
        for (IProdotto iProd: getSottoProdotti() ) {
            if (iProd instanceof Prodotto){
                disponibilita.add(((Prodotto)iProd).isDisponibile());
            } else {
                disponibilita.add(isDisponibile());
            }
        } return !disponibilita.contains(false);
    }

    @Override
    public Iterator getIterator() {
        return new ProdottoCompositoIterator();
    }

    private class ProdottoCompositoIterator implements Model.Iterator<IProdotto> {

        int index = 0;
        IProdotto current;

        @Override
        public IProdotto next() {
            current = sottoProdotti.get(index);
            return sottoProdotti.get(index++);
        }

        @Override
        public boolean hasNext() {
            return index < sottoProdotti.size();
        }

        @Override
        public IProdotto currentItem() {
            if (current == null) {
                return null;
            } else {
                return current;
            }
        }

        @Override
        public void reset() {
            index = 0;
            current = null;
        }
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
                ", sottoProdotti=" + sottoProdotti +
                '}';
    }
}
