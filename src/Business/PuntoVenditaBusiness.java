package Business;

import DAO.*;
import Model.PuntoVendita;

public class PuntoVenditaBusiness {

    private static IMagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    private static IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private static IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static IUtenteDAO utenteDAO = UtenteDAO.getInstance();



}
