package Model;

public interface IProdotto {

    int getId();
    String getNome();
    Float getPrezzo();
    Categoria getCategoria();
    boolean isDisponibile();

    Iterator getIterator();
}
