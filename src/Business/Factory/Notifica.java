package Business.Factory;

import Model.Cliente;

import java.io.File;

public abstract class Notifica {

    private String titolo;
    private String testo;
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public abstract boolean inviaNotifica();
}
