package Model;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

    int id;
    private String nome;
    private List<Categoria> sottoCategorie = new ArrayList<Categoria>();


    public Categoria(String nome, List<Categoria> sottoCategorie) {
        this.nome = nome;
        this.sottoCategorie = sottoCategorie;
    }

    public Categoria() {
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

    public List<Categoria> getSottoCategorie() {
        return sottoCategorie;
    }

    public void setSottoCategorie(List<Categoria> sottoCategorie) {
        this.sottoCategorie = sottoCategorie;
    }

    public void aggiungiSottoCategoria(Categoria c){
        sottoCategorie.add(c);
    }

    public void aggiungiSottoCategoria(List<Categoria> c){
        sottoCategorie.addAll(c);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sottoCategorie=" + sottoCategorie +
                '}';
    }
}
