package Model;

public class Admin extends Utente{

    public Admin(Persona persona, String username, String password, Indirizzo indirizzo) {
        super(persona, username, password, indirizzo);
    }

    public Admin() {
        super();
    }
}
