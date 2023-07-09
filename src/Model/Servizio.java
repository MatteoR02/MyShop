package Model;

import java.util.List;

public class Servizio extends Articolo{

    public Servizio(String nome, float prezzo, List<Recensione> recensioni, List<Foto> immagini, Categoria categoria) {
        super(nome, prezzo, recensioni, immagini, categoria);
    }

    public Servizio() {
        super();
    }
}
