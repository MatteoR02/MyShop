package DAO;

import Model.PuntoVendita;

import java.util.List;

public interface IPuntoVenditaDAO {

    PuntoVendita loadPuntoVendita(int idPuntoVendita);
    List<PuntoVendita> loadPuntiVendita();

    PuntoVendita loadPuntoVenditaOfManager(int idManager);
    List<PuntoVendita> loadPuntiVenditaOfCliente(int idCliente);

    int addPuntoVendita(PuntoVendita puntoVendita);
    int updatePuntoVendita(PuntoVendita puntoVendita);
    int removePuntoVendita(int idPuntoVendita);

    int registraCliente(int idCliente, int idPuntoVendita);
    int removeClienteFromPuntoVendita(int idCliente, int idPuntoVendita);



}
