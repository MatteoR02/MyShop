package Model;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class Recensione {

    public enum Punteggio{ECCELLENTE, BUONO, MEDIOCRE, SCARSO, ORRIBILE}

    private int id;
    private String titolo;
    private String testo;
    private Punteggio valutazione;
    private Date data;
    private List<Foto> immagini;
    private int idCliente;

    public Recensione(String titolo, String testo, Punteggio valutazione, Date data, List<Foto> immagini, int idCliente) {
        this.titolo = titolo;
        this.testo = testo;
        this.valutazione = valutazione;
        this.data = data;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public int punteggioToInteger(Punteggio punteggio){
        int voto = 0;
        switch (valutazione){
            case ORRIBILE -> voto = 1;
            case SCARSO -> voto = 2;
            case MEDIOCRE -> voto = 3;
            case BUONO -> voto = 4;
            case ECCELLENTE -> voto = 5;
        }
        return voto;
    }

    public int getPunteggioInInteger(){
        return punteggioToInteger(this.valutazione);
    }

    @Override
    public String toString() {
        return "Recensione{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", testo='" + testo + '\'' +
                ", valutazione=" + valutazione +
                ", data=" + data +
                '}';
    }
}
