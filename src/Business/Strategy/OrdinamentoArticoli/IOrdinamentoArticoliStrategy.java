package Business.Strategy.OrdinamentoArticoli;

import Model.Articolo;

import java.util.List;

public interface IOrdinamentoArticoliStrategy {

    public void ordina(List<Articolo> listaArticoli);
}
