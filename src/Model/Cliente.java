package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Cliente extends Utente {

    public enum ProfessioneType {IMPIEGATO, STUDENTE, DISOCCUPATO}

    ;

    public enum CanalePreferitoType {EMAIL, SMS}

    ;

    public enum StatoUtenteType {BLOCCATO, SOSPESO, ABILITATO}

    ;

    private Date dataAbilitazione;
    private ProfessioneType professione;
    private CanalePreferitoType canalePreferito;
    private StatoUtenteType stato;
    private List<Messaggio> messaggi;
    private List<ListaAcquisto> listeAcquisto;
    private List<PuntoVendita> puntiVenditaRegistrati;

    public Cliente(Persona persona, String username, String password, Indirizzo indirizzo, ProfessioneType professione, CanalePreferitoType canalePreferito, Date dataAbilitazione, StatoUtenteType stato, List<Messaggio> messaggi, List<ListaAcquisto> listeAcquisto, List<PuntoVendita> puntiVenditaRegistrati) {
        super(persona, username, password, indirizzo);
        this.dataAbilitazione = dataAbilitazione;
        this.professione = professione;
        this.canalePreferito = canalePreferito;
        this.stato = stato;
        this.messaggi = messaggi;
        this.listeAcquisto = listeAcquisto;
        this.puntiVenditaRegistrati = puntiVenditaRegistrati;
    }

    public Cliente() {
        this.canalePreferito = null;
        this.professione = null;
        this.stato = StatoUtenteType.ABILITATO;
    }

    public Date getDataAbilitazione() {
        return dataAbilitazione;
    }

    public void setDataAbilitazione(Date dataAbilitazione) {
        this.dataAbilitazione = dataAbilitazione;
    }

    public ProfessioneType getProfessione() {
        return professione;
    }

    public void setProfessione(ProfessioneType professione) {
        this.professione = professione;
    }

    public CanalePreferitoType getCanalePreferito() {
        return canalePreferito;
    }

    public void setCanalePreferito(CanalePreferitoType canalePreferito) {
        this.canalePreferito = canalePreferito;
    }

    public StatoUtenteType getStato() {
        return stato;
    }

    public void setStato(StatoUtenteType stato) {
        this.stato = stato;
    }

    public List<Messaggio> getMessaggi() {
        return messaggi;
    }

    public void setMessaggi(List<Messaggio> messaggi) {
        this.messaggi = messaggi;
    }

    public List<ListaAcquisto> getListeAcquisto() {
        return listeAcquisto;
    }

    public void setListeAcquisto(List<ListaAcquisto> listeAcquisto) {
        this.listeAcquisto = listeAcquisto;
    }

    public List<PuntoVendita> getPuntiVenditaRegistrati() {
        return puntiVenditaRegistrati;
    }

    public void setPuntiVenditaRegistrati(List<PuntoVendita> puntiVenditaRegistrati) {
        this.puntiVenditaRegistrati = puntiVenditaRegistrati;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "canalePreferito=" + canalePreferito +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}