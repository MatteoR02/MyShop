package Model;

import java.util.List;

public class Manager extends Utente{

    private int idPuntoVendita;
    private List<Messaggio> messaggi;

    public Manager( Persona persona, String username, String password, Indirizzo indirizzo, int idPuntoVendita, List<Messaggio> messaggi) {
        super(persona, username, password, indirizzo);
        this.idPuntoVendita = idPuntoVendita;
        this.messaggi = messaggi;
    }

    public Manager() {
        super();
        idPuntoVendita = 1;
    }

    public int getIdPuntoVendita() {
        return idPuntoVendita;
    }

    public void setIdPuntoVendita(int idPuntoVendita) {
        this.idPuntoVendita = idPuntoVendita;
    }

    public List<Messaggio> getMessaggi() {
        return messaggi;
    }



    public void setMessaggi(List<Messaggio> messaggi) {
        this.messaggi = messaggi;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "idPuntoVendita=" + idPuntoVendita +
                '}';
    }
}
