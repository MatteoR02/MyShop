package Business.AbstractFactory;

import Model.Articolo;
import Model.Prodotto;

public class ProdottoFactory implements IAbstractFactory<Articolo> {
    @Override
    public Prodotto crea() {
        return new Prodotto();
    }
}
