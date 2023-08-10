package Model;

import java.util.List;

public class Servizio extends Articolo{

    public Servizio(String nome, String descrizione, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria, Erogatore erogatore) {
        super(nome, descrizione, prezzo, recensioni, immagini, categoria, erogatore);
    }

    public Servizio() {
        super();
    }
}
