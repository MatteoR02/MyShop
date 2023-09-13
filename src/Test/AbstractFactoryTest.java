package Test;

import Business.*;
import Business.AbstractFactory.FactoryProvider;
import Business.AbstractFactory.IAbstractFactory;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

public class AbstractFactoryTest {

    @Test
    public void factoryTest(){
        //Creo nuova factory che mi viene data dinamicamente dalla FactoryProducer
        IAbstractFactory factory = FactoryProvider.getFactory(FactoryProvider.FactoryType.PRODOTTO);

        //Ora che ho la factory settata posso crearmi gli oggetti
        assert factory != null;
        Articolo articolo1 = (Articolo) factory.crea();
        Articolo articolo2 = (Articolo) factory.crea();

        //Cambio Factory
        factory = FactoryProvider.getFactory(FactoryProvider.FactoryType.SERVIZIO);

        assert factory != null;
        Articolo articolo3 = (Articolo) factory.crea();

        //Verifico che l'oggetto creato dalla factory sia effettivamente un prodotto
        Assert.assertTrue(articolo1 instanceof Prodotto);
        Assert.assertTrue(articolo2 instanceof Prodotto);
        Assert.assertTrue(articolo3 instanceof Servizio);

    }
}
