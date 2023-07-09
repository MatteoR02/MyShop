package DAO;

import Model.Categoria;

import java.util.List;

public interface ICategoriaDAO {

    Categoria loadCategoria(int idCategoria);
    Categoria loadSottoCategoria(int idCategoria);
    List<Categoria> loadAllCategorie();
    int addCategoria(Categoria categoria);
    int updateCategoria(Categoria categoria);
    int removeCategoria(int idCategoria);

    List<Categoria> loadAllMacroCategorie();
    List<Categoria> loadAllSottoCategorie();
    List<Categoria> loadAllSottoCategoriaOfID(int idCategoria);

    boolean isSottoCategoria(int idCategoria);
    boolean isMacroCategoria(int idCategoria);

}
