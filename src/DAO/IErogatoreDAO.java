package DAO;

import Model.Erogatore;

import java.util.List;

public interface IErogatoreDAO {

    Erogatore loadErogatore(int idProduttore);
    List<Erogatore> loadAllErogatori();

    int addErogatore(Erogatore erogatore);
    int updateErogatore(Erogatore erogatore);
    int removeErogatore(int idProduttore);
}
