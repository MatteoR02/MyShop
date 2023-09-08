package View.ViewModel;

public class RigaProdottoCreazione {

    private int idProdotto;
    private String nome;
    private float prezzo;
    private String categoria;
    private String erogatore;
    private boolean isProdottoComposito;
    private boolean selezionato;

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getErogatore() {
        return erogatore;
    }

    public void setErogatore(String erogatore) {
        this.erogatore = erogatore;
    }

    public boolean isProdottoComposito() {
        return isProdottoComposito;
    }

    public void setIsProdottoComposito(boolean prodottoComposito) {
        isProdottoComposito = prodottoComposito;
    }

    public boolean isSelezionato() {
        return selezionato;
    }

    public boolean getSelezionato() {
        return selezionato;
    }

    public void setSelezionato(boolean selezionato) {
        this.selezionato = selezionato;
    }
}
