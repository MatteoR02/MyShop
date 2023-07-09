package Model;

import java.sql.Timestamp;

public class Utente {

    protected int id;
    protected Persona persona;
    protected String username;
    protected String password;
    protected Indirizzo indirizzo;

    public Utente(Persona persona, String username, String password, Indirizzo indirizzo) {
        this.persona = persona;
        this.username = username;
        this.password = password;
        this.indirizzo = indirizzo;
    }

    public Utente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", persona=" + persona +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", indirizzo=" + indirizzo +
                '}';
    }
}