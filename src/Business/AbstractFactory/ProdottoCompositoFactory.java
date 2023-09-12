package Business.AbstractFactory;

import Model.Articolo;
import Model.Prodotto;
import Model.ProdottoComposito;

public class ProdottoCompositoFactory implements IAbstractFactory<Articolo> {
    @Override
    public ProdottoComposito crea() {
        return new ProdottoComposito();
    }

}
