package Business.Strategy;

import Model.Recensione;

import java.util.List;

public class OrdinamentoRecensioni {

    private List<Recensione> listaRecensioni;
    private IOrdinamentoRecensioneStrategy ordinamentoRecensioneStrategy;
    public enum Ordinamento{MIGLIORI, RECENTI, URGENTI}

    public OrdinamentoRecensioni(List<Recensione> listaRecensioni){
        this.listaRecensioni = listaRecensioni;
    }

    public void setOrdinamentoRecensioneStrategy(IOrdinamentoRecensioneStrategy ordinamentoRecensioneStrategy) {
        this.ordinamentoRecensioneStrategy = ordinamentoRecensioneStrategy;
    }

    public void ordina() {
        ordinamentoRecensioneStrategy.ordina(listaRecensioni);
    }

}
