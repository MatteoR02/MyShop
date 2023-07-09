package DAO;

import Model.Produttore;

import java.util.List;

public interface IProduttoreDAO {

    Produttore loadProduttore(int idProduttore);
    List<Produttore> loadAllProduttori();

    int addProduttore(Produttore produttore);
    int updateProduttore(Produttore produttore);
    int removeProduttore(int idProduttore);
}
