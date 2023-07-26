package View.ViewModel;

import Model.Cliente;

public class RigaCliente {

    private int idCliente;
    private String nome;
    private String cognome;
    private String username;
    private Cliente.ProfessioneType professione;
    private Cliente.CanalePreferitoType canalePreferito;
    private Cliente.StatoUtenteType stato;
    private Boolean selezionato;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Cliente.ProfessioneType getProfessione() {
        return professione;
    }

    public void setProfessione(Cliente.ProfessioneType professione) {
        this.professione = professione;
    }

    public Cliente.CanalePreferitoType getCanalePreferito() {
        return canalePreferito;
    }

    public void setCanalePreferito(Cliente.CanalePreferitoType canalePreferito) {
        this.canalePreferito = canalePreferito;
    }

    public Cliente.StatoUtenteType getStato() {
        return stato;
    }

    public void setStato(Cliente.StatoUtenteType stato) {
        this.stato = stato;
    }

    public Boolean getSelezionato() {
        return selezionato;
    }

    public void setSelezionato(Boolean selezionato) {
        this.selezionato = selezionato;
    }


}
