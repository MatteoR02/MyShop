package DAO;

import Model.PuntoVendita;

import java.util.List;

public interface IPuntoVenditaDAO {

    boolean isPuntoVendita(int idPuntoVendita);

    PuntoVendita loadPuntoVendita(int idPuntoVendita);
    List<PuntoVendita> loadAllPuntiVendita();

    PuntoVendita loadPuntoVenditaOfManager(int idManager);
   // List<PuntoVendita> loadPuntiVenditaOfCliente(int idCliente);

    int addPuntoVendita(PuntoVendita puntoVendita);
    int updatePuntoVendita(PuntoVendita puntoVendita);
    int removePuntoVendita(int idPuntoVendita);

    int addArticoloToPuntoVendita(int idArticolo, int idPuntoVendita);

    //int registraCliente(int idCliente, int idPuntoVendita);
    //int removeClienteFromPuntoVendita(int idCliente, int idPuntoVendita);



}
