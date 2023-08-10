package Model;

public class Erogatore {

    private int id;
    private String nome;
    private String sitoWeb;
    private Indirizzo indirizzo;

    public Erogatore(String nome, String sitoWeb, Indirizzo indirizzo) {
        this.nome = nome;
        this.sitoWeb = sitoWeb;
        this.indirizzo = indirizzo;
    }

    public Erogatore(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }

    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Erogatore{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sitoWeb='" + sitoWeb + '\'' +
                ", indirizzo=" + indirizzo +
                '}';
    }
}
