package Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Prenotazione {

    public enum StatoPrenotazione {IN_CORSO, SOSPESA, ARRIVATA}

    private int id;
    private Map<IProdotto, Integer> prodottiPrenotati;
    private int idCliente;
    private int idPuntoVendita;
    private Date dataPrenotazione;
    private Date dataArrivo;
    private StatoPrenotazione statoPrenotazione;

    public Prenotazione(Map<IProdotto, Integer> prodottiPrenotati, int idCliente, int idPuntoVendita, Date dataPrenotazione, Date dataArrivo, StatoPrenotazione statoPrenotazione) {
        this.prodottiPrenotati = prodottiPrenotati;
        this.idCliente = idCliente;
        this.idPuntoVendita = idPuntoVendita;
        this.dataPrenotazione = dataPrenotazione;
        this.dataArrivo = dataArrivo;
        this.statoPrenotazione = statoPrenotazione;
    }

    public Prenotazione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<IProdotto, Integer> getProdottiPrenotati() {
        return prodottiPrenotati;
    }

    public void setProdottiPrenotati(Map<IProdotto, Integer> prodottiPrenotati) {
        this.prodottiPrenotati = prodottiPrenotati;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPuntoVendita() {
        return idPuntoVendita;
    }

    public void setIdPuntoVendita(int idPuntoVendita) {
        this.idPuntoVendita = idPuntoVendita;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public void setDataArrivo(Date dataArrivo) {
        this.dataArrivo = dataArrivo;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }

    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return "Prenotazione effettuata il: " + sdf.format(dataPrenotazione);
    }
}
