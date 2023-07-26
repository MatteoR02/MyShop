package Business.AbstractFactory;

import Model.Articolo;
import Model.Servizio;

public class ServizioFactory implements IAbstractFactory<Articolo> {
    @Override
    public Servizio crea() {
        return new Servizio();
    }
}
