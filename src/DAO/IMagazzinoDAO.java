package DAO;

import Model.Collocazione;
import Model.Magazzino;

import java.util.List;

public interface IMagazzinoDAO {

    Magazzino loadMagazzino(int idMagazzino);
    List<Magazzino> loadAllMagazzini();

    List<Magazzino> loadMagazziniOfPuntoVendita(int idPuntoVendita);

    Collocazione loadCollocazioneOfProdotto(int idProdotto);

    int addMagazzino(Magazzino magazzino);
    int updateMagazzino(Magazzino magazzino);
    int removeMagazzino(int idMagazzino);

    int setFKPuntoVenditaToDefault(int idPuntoVendita);

}
