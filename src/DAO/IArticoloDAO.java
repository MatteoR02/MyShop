package DAO;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IArticoloDAO {

    boolean isProdotto(int idArticolo);
    boolean isServizio(int idArticolo);

    Prodotto loadProdotto(int idProdotto);
    ArrayList<Articolo> loadAllProdotti();
    ProdottoComposito loadProdottoComposito(int idProdottoComposito);
    boolean isProdottoComposito(int idProdottoComposito);
    boolean isSottoProdotto(int idProdotto);
    int addProdotto(Articolo prodotto, boolean returnID);
    int updateProdotto(Articolo prodotto);
    int removeProdotto(int idProdotto);
    int removeSottoProdotto(int idProdComp, int idSottoProd);

    int addSottoProdotto(int idProdComp, IProdotto prodotto);

    HashMap<Articolo, Integer> getArticoliFromLista(int idLista);

    Servizio loadServizio(int idServizio);
    ArrayList<Servizio> loadAllServizi();
    int addServizio(Servizio servizio, boolean returnID);
    int updateServizio(Servizio servizio);
    int removeServizio(int idServizio);

    ArrayList<Articolo> loadAllArticoliFromPuntoVendita(int idPuntoVendita);

    int setFKCategoriaToDefault(int idCategoria);
    int setFKMagazzinoToDefault(int idMagazzino);
    int setFKErogatoreToDefault(int idProduttore);

}
