package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaServizio{


    private String nome;
    private List<CategoriaServizio> sottoCategorie;

    public CategoriaServizio() {
    }

    public CategoriaServizio(String nome) {
        this.nome = nome;
        this.sottoCategorie = new ArrayList<CategoriaServizio>();
    }

    public List<CategoriaServizio> getSottoCategorie() {
        return sottoCategorie;
    }

    public void aggiungiSottoCategoria(CategoriaServizio c){
        sottoCategorie.add(c);
    }

    public void aggiungiSottoCategoria(List<CategoriaServizio> c){
        sottoCategorie.addAll(c);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
