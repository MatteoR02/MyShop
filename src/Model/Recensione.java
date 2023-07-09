package Model;

import java.util.List;

public class Recensione {

    public enum Punteggio{ECCELLENTE, BUONO, MEDIOCRE, SCARSO, ORRIBILE};

    private int id;
    private String titolo;
    private String testo;
    private Punteggio valutazione;
    private List<Foto> immagini;
    private int idCliente;

    public Recensione(String titolo, String testo, Punteggio valutazione, List<Foto> immagini, int idCliente) {
        this.titolo = titolo;
        this.testo = testo;
        this.valutazione = valutazione;
        this.immagini = immagini;
        this.idCliente = idCliente;
    }

    public Recensione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Punteggio getValutazione() {
        return valutazione;
    }

    public void setValutazione(Punteggio valutazione) {
        this.valutazione = valutazione;
    }

    public List<Foto> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<Foto> immagini) {
        this.immagini = immagini;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
