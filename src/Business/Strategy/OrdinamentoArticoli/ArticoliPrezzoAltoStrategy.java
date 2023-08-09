package Business.Strategy.OrdinamentoArticoli;

import Model.Articolo;
import Model.Recensione;

import java.util.Comparator;
import java.util.List;

public class ArticoliPrezzoAltoStrategy implements IOrdinamentoArticoliStrategy {
    @Override
    public void ordina(List<Articolo> listaArticoli) {
        listaArticoli.sort(new Comparator<Articolo>() {
            @Override
            public int compare(Articolo o1, Articolo o2) {
                    if (o2 == null || o1 == null) return 0;

                    return o2.getPrezzo().compareTo(o1.getPrezzo());
            }
        });
    }
}
