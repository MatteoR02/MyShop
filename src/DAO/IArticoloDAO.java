package DAO;
import Model.*;

import java.util.List;

public interface IArticoloDAO {

    boolean isProdotto(int idArticolo);
    boolean isServizio(int idArticolo);

    Prodotto loadProdotto(int idProdotto);
    List<Prodotto> loadAllProdotti();
    ProdottoComposito loadProdottoComposito(int idProdottoComposito);
    boolean isProdottoComposito(int idProdottoComposito);
    boolean isSottoProdotto(int idProdotto);
    int addProdotto(Articolo prodotto);
    int updateProdotto(Articolo prodotto);
    int removeProdotto(int idProdotto);
    int createComposition(List<Integer> idProdotti, String nomeComp, int idCategoria);

    Servizio loadServizio(int idServizio);
    List<Servizio> loadAllServizi();
    int addServizio(Servizio servizio);
    int updateServizio(Servizio servizio);
    int removeServizio(int idServizio);

    int setFKCategoriaToDefault(int idCategoria);
    int setFKMagazzinoToDefault(int idMagazzino);
    int setFKProduttoreToDefault(int idProduttore);

}
