package DAO;

import Model.Articolo;
import Model.ListaAcquisto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IListaAcquistoDAO {

    boolean isListaAcquisto(int idLista);

    ListaAcquisto loadListaAcquisto(int idLista);
    ArrayList<ListaAcquisto> loadAllListaAcquisto();
    int addListaAcquisto(String nome, int idCliente);
    int updateListaAcquisto(ListaAcquisto listaAcquisto);
    int removeListaAcquisto(int idLista);

    Map<Articolo, Integer> getArticoliFromLista(int idLista);
    ArrayList<ListaAcquisto> getListeOfCliente(int idCliente);

    boolean isPagata(int idLista);
    int insertArticoloInLista(int idLista, int idArticolo, int quantita);
    int updateArticoloInLista(int idLista, int idArticolo, int quantita);
    int removeArticoloInLista(int idLista, int idArticolo);
    int removeAllArticoliInLista(int idLista);

    int removeArticoloFromListe(int idArticolo);

    boolean isInLista(int idLista, int idArticolo);
}
