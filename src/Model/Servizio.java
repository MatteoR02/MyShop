package Model;

import java.util.List;

public class Servizio extends Articolo{

    public Servizio(String nome, String descrizione, float prezzo, Categoria categoria, Erogatore erogatore) {
        super(nome, descrizione, prezzo, categoria, erogatore);
    }

    public Servizio() {
        super();
    }
}
