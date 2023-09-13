package Business.AbstractFactory;

import Model.*;

public class ProdottoFactory implements IAbstractFactory<Articolo> {
    @Override
    public Prodotto crea() {
        return new Prodotto();
    }
}
