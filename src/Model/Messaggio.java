package Model;

import java.sql.Blob;

public class Messaggio {

    public enum StatoMessaggioType{INVIATO, RICEVUTO, VISUALIZZATO, LETTO, RISPOSTO};

    private int id;
    private String titolo;
    private String testo;
    private Blob immagine;
    private StatoMessaggioType statoMessaggio;

    public Messaggio(String titolo, String testo, Blob immagine, StatoMessaggioType statoMessaggio) {
        this.titolo = titolo;
        this.testo = testo;
        this.immagine = immagine;
        this.statoMessaggio = statoMessaggio;
    }

    public Messaggio() {
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

    public Blob getImmagine() {
        return immagine;
    }

    public void setImmagine(Blob immagine) {
        this.immagine = immagine;
    }

    public StatoMessaggioType getStatoMessaggio() {
        return statoMessaggio;
    }

    public void setStatoMessaggio(StatoMessaggioType statoMessaggio) {
        this.statoMessaggio = statoMessaggio;
    }
}
