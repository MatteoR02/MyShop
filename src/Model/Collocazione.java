package Model;

public class Collocazione {

    private int quantita;
    private String corsia;
    private int scaffale;
    private Magazzino magazzino;

    public Collocazione(int quantita, String corsia, int scaffale, Magazzino magazzino) {
        this.quantita = quantita;
        this.corsia = corsia;
        this.scaffale = scaffale;
        this.magazzino = magazzino;
    }

    public Collocazione() {
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCorsia() {
        return corsia;
    }

    public void setCorsia(String corsia) {
        this.corsia = corsia;
    }

    public int getScaffale() {
        return scaffale;
    }

    public void setScaffale(int scaffale) {
        this.scaffale = scaffale;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }

    @Override
    public String toString() {
        return "Collocazione{" +
                "quantita=" + quantita +
                ", corsia='" + corsia + '\'' +
                ", scaffale=" + scaffale +
                ", magazzino=" + magazzino +
                '}';
    }
}
