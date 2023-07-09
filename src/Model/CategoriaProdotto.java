package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaProdotto {

    private String nome;
    private List<CategoriaProdotto> sottoCategorie;

    public CategoriaProdotto() {
    }

    public CategoriaProdotto(String nome) {
        this.nome = nome;
        this.sottoCategorie = new ArrayList<CategoriaProdotto>();
    }

    public List<CategoriaProdotto> getSottoCategorie() {
        return sottoCategorie;
    }

    public void aggiungiSottoCategoria(CategoriaProdotto c){
        sottoCategorie.add(c);
    }

    public void aggiungiSottoCategoria(List<CategoriaProdotto> c){
        sottoCategorie.addAll(c);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
