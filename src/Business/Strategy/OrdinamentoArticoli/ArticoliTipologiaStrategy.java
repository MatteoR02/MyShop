package Business.Strategy.OrdinamentoArticoli;

import Model.Articolo;
import Model.Prodotto;
import Model.ProdottoComposito;
import Model.Servizio;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ArticoliTipologiaStrategy implements IOrdinamentoArticoliStrategy {
    @Override
    public void ordina(List<Articolo> listaArticoli) {
        listaArticoli.sort(new Comparator<Articolo>() {
            @Override
            public int compare(Articolo o1, Articolo o2) {
                    if (o2 == null || o1 == null) return 0;

                   int o1Tipo = 0;
                   int o2Tipo = 0;

                   if (o1 instanceof Prodotto) {
                       o1Tipo = 3;
                   } else if (o1 instanceof ProdottoComposito){
                       o1Tipo = 2;
                   } else if (o1 instanceof Servizio){
                       o1Tipo = 1;
                   }

                if (o2 instanceof Prodotto) {
                    o2Tipo = 3;
                } else if (o2 instanceof ProdottoComposito){
                    o2Tipo = 2;
                } else if (o2 instanceof Servizio){
                    o2Tipo = 1;
                }
                return o2Tipo - o1Tipo;
            }
        });
    }
}
