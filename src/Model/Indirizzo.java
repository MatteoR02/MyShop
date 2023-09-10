package Model;

public class Indirizzo {
    private String nazione;
    private String citta;
    private String cap;
    private String via;
    private String civico;

    public Indirizzo(String nazione, String citta, String cap, String via, String civico) {
        this.nazione = nazione;
        this.citta = citta;
        this.cap = cap;
        this.via = via;
        this.civico = civico;
    }

    public Indirizzo() {
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String  getCivico() {
        return civico;
    }

    public void setCivico(String  civico) {
        this.civico = civico;
    }

    @Override
    public String toString() {
        return nazione + ", " + citta + ", " + via + ", " + civico;
    }
}
