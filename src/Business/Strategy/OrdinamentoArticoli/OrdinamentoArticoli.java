package Business.Strategy.OrdinamentoArticoli;

import Model.Articolo;
import Model.Recensione;

import java.util.List;

public class OrdinamentoArticoli {

    private List<Articolo> listaArticoli;
    private IOrdinamentoArticoliStrategy ordinamentoArticoliStrategy;
    public enum Ordinamento{PREZZO_PIU_ALTO, PREZZO_PIU_BASSO, PIU_VOTATI, ORDINE_ALFABETICO}

    public OrdinamentoArticoli(List<Articolo> listaArticoli){
        this.listaArticoli = listaArticoli;
    }

    public void setOrdinamentoArticoliStrategy(IOrdinamentoArticoliStrategy ordinamentoArticoliStrategy) {
        this.ordinamentoArticoliStrategy = ordinamentoArticoliStrategy;
    }

    public void ordina() {
        ordinamentoArticoliStrategy.ordina(listaArticoli);
    }

}
