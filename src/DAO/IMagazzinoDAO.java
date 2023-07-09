package DAO;

import Model.Magazzino;

import java.util.List;

public interface IMagazzinoDAO {

    Magazzino loadMagazzino(int idMagazzino);
    List<Magazzino> loadAllMagazzini();

    List<Magazzino> loadMagazziniOfPuntoVendita(int idPuntoVendita);

    int addMagazzino(Magazzino magazzino);
    int updateMagazzino(Magazzino magazzino);
    int removeMagazzino(int idMagazzino);

    int setFKPuntoVenditaToDefault(int idPuntoVendita);

}
