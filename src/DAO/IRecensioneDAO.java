package DAO;

import Model.Recensione;

import java.util.List;

public interface IRecensioneDAO {

    Recensione loadRecensione(int idRecensione);
    List<Recensione> loadAllRecensioni();
    List<Recensione> loadRecensioniOfArticolo(int idArticolo);
    List<Recensione> loadRecensioniOfCliente(int idCliente);

    boolean isRecensioneDone(int idArticolo, int idCliente);

    int addRecensione(Recensione recensione, int idArticolo);
    int updateRecensione(Recensione recensione);
    int removeRecensione(int idRecensione);

    int setFKClienteToDefault(int idRecensione);

}
