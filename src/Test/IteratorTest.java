package Test;

import Business.AbstractFactory.FactoryProvider;
import Business.AbstractFactory.IAbstractFactory;
import Model.Articolo;
import Model.IProdotto;
import Model.Iterator;
import Model.Prodotto;
import Model.ProdottoComposito;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class IteratorTest {
    @Test
    public void test(){
       IAbstractFactory factory = FactoryProvider.getFactory(FactoryProvider.FactoryType.PRODOTTO_COMPOSITO);
        ProdottoComposito prodottoComposito = (ProdottoComposito) factory.crea();
        ArrayList<IProdotto> sottoPr = new ArrayList<>();

        factory = FactoryProvider.getFactory(FactoryProvider.FactoryType.PRODOTTO);
        for (int i = 0; i < 10; i++) {
            Prodotto articolo = (Prodotto) factory.crea();
            articolo.setId(i);
            sottoPr.add(articolo);
        }
        prodottoComposito.addSottoProdotti(sottoPr);

        Iterator iterator = prodottoComposito.getIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(iterator.currentItem());
        Assert.assertFalse(iterator.hasNext()); //Dovrebbe aver finito e non dover andare piu avanti.  Expected: false
        Assert.assertFalse(iterator.hasNext());
        iterator.reset();
        Assert.assertTrue(iterator.hasNext());
    }
}
