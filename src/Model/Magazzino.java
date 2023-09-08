package Model;

import java.util.HashMap;

public class Magazzino {

    private int id;
    private Indirizzo indirizzo;
    private int idPuntoVendita;

    public Magazzino(Indirizzo indirizzo, int idPuntoVendita) {
        this.indirizzo = indirizzo;
        this.idPuntoVendita = idPuntoVendita;
    }

    public Magazzino() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getIdPuntoVendita() {
        return idPuntoVendita;
    }

    public void setIdPuntoVendita(int idPuntoVendita) {
        this.idPuntoVendita = idPuntoVendita;
    }

    @Override
    public String toString() {
        return "Magazzino in " + indirizzo;
    }
}
