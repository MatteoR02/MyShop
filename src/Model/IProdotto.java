package Model;

public interface IProdotto {

    public int getId();
    public String getNome();
    public Float getPrezzo();
    public Produttore getProduttore();
    public Categoria getCategoria();
}
