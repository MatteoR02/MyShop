package Business.Strategy;

import Model.Recensione;

import java.util.List;

public interface IOrdinamentoRecensioneStrategy {

    public void ordina(List<Recensione> listaRecensioni);
}
