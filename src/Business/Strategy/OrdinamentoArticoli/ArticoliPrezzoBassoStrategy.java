package Business.Strategy.OrdinamentoArticoli;

import Model.Articolo;

import java.util.Comparator;
import java.util.List;

public class ArticoliPrezzoBassoStrategy implements IOrdinamentoArticoliStrategy {
    @Override
    public void ordina(List<Articolo> listaArticoli) {
        listaArticoli.sort(new Comparator<Articolo>() {
            @Override
            public int compare(Articolo o1, Articolo o2) {
                    if (o2 == null || o1 == null) return 0;

                    return o1.getPrezzo().compareTo(o2.getPrezzo());
            }
        });
    }
}
