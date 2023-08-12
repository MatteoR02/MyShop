package Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaAcquisto {

    public enum StatoPagamentoType{PAGATO, DA_PAGARE}

    private int id;
    private String nome;
    private StatoPagamentoType statoPagamento;
    private Timestamp dataPagamento;
    private Map<Articolo, Integer> articoli;  //Map articolo,quantità

    public ListaAcquisto(String nome, StatoPagamentoType statoPagamento, Timestamp dataPagamento, HashMap<Articolo,Integer> articoli) {
        this.nome = nome;
        this.statoPagamento = statoPagamento;
        this.dataPagamento = dataPagamento;
        this.articoli = articoli;
    }

    public ListaAcquisto() {
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

    public StatoPagamentoType getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(StatoPagamentoType statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    public Timestamp getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Timestamp dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Map<Articolo, Integer> getArticoli() {
        return articoli;
    }

    public void setArticoli(Map<Articolo, Integer> articoli) {
        this.articoli = articoli;
    }

    public boolean isPagato(){
        return this.statoPagamento == StatoPagamentoType.PAGATO;
    }

    public boolean isProdottoDisponibile(IProdotto prod) {
        if (prod instanceof Prodotto) {
            if (getArticoli().containsKey(prod)) {
                return getArticoli().get(prod) <= ((Prodotto) prod).getCollocazione().getQuantita();
            }
        } else if (prod instanceof ProdottoComposito) {
            ArrayList<Boolean> disponibilita = new ArrayList<>();
            for (IProdotto iProd: ((ProdottoComposito) prod).getSottoProdotti() ) {
                disponibilita.add(iProd.isDisponibile());
            } return !disponibilita.contains(false);
        }
        return false;
    }

    @Override
    public String toString() {
        return nome;
    }
}
