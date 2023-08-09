package Business.Strategy.OrdinamentoRecensioni;

import Model.Recensione;

import java.util.Comparator;
import java.util.List;

public class RecensioniMiglioriStrategy implements IOrdinamentoRecensioneStrategy{
    @Override
    public void ordina(List<Recensione> listaRecensioni) {
        listaRecensioni.sort(new Comparator<Recensione>() {
            @Override
            public int compare(Recensione o1, Recensione o2) {

                if (o1.getValutazione() == o2.getValutazione()){
                    return 0;
                }
                int valutazione1 = 0;
                int valutazione2 = 0;

                switch (o1.getValutazione()){
                    case ORRIBILE: valutazione1 = 1; break;
                    case SCARSO: valutazione1 = 2; break;
                    case MEDIOCRE: valutazione1 = 3; break;
                    case BUONO: valutazione1 = 4; break;
                    case ECCELLENTE: valutazione1 = 5;
                }
                switch (o2.getValutazione()){
                    case ORRIBILE: valutazione2 = 1; break;
                    case SCARSO: valutazione2 = 2; break;
                    case MEDIOCRE: valutazione2 = 3; break;
                    case BUONO: valutazione2 = 4; break;
                    case ECCELLENTE: valutazione2 = 5;
                }
                return valutazione2 - valutazione1;
            }
        });
    }
}
