package DAO;

import Model.Foto;

import java.sql.Blob;
import java.util.List;

public interface IFotoDAO {

    Foto loadFoto(int idFoto);
    List<Foto> loadAllFoto();
    List<Foto> loadAllFotoOfRecensione(int idRecensione);
    List<Foto> loadAllFotoOfArticolo(int idArticolo);
    int addFoto(Foto foto);
    int addFotoToRecensione(Foto foto, int idRecensione);
    int updateFoto(Foto foto);
    int updateFotoOfRecensione(Foto foto, int idRecensione);
    int removeFoto(int idFoto);

    boolean isFotoOfArticolo(int idFoto);

    int addFotoToArticolo(Foto foto, int idArticolo);

    Foto loadDefaultFoto();

    int setFKArticoloHasFotoToDefault(int idArticolo);

}
