package DAO;

import Model.Recensione;

import java.util.List;

public interface IRecensioneDAO {

    boolean isRecensione(int idRecensione);

    Recensione loadRecensione(int idRecensione);
    Recensione loadRisposta(int idRecensione);
    List<Recensione> loadAllRecensioni();
    List<Recensione> loadRecensioniOfArticolo(int idArticolo);
    List<Recensione> loadRecensioniOfCliente(int idCliente);

    boolean isRecensioneDone(int idArticolo, int idCliente);
    boolean isRispostaDone(int idRecensione, int idManager);

    int addRecensione(Recensione recensione, int idArticolo);
    int updateRecensione(Recensione recensione);
    int removeRecensione(int idRecensione);

    int addRisposta(Recensione recensione, int idRecensione);

    int setFKClienteToDefault(int idRecensione);

}
