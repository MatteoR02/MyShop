package Model;

import java.util.List;

public class Manager extends Utente{

    private int idPuntoVendita;

    public Manager( Persona persona, String username, String password, Indirizzo indirizzo, int idPuntoVendita) {
        super(persona, username, password, indirizzo);
        this.idPuntoVendita = idPuntoVendita;
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


    @Override
    public String toString() {
        return "Manager " + username;
    }
}
