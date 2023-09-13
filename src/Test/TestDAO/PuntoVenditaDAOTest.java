package Test.TestDAO;

import DAO.IPuntoVenditaDAO;
import DAO.PuntoVenditaDAO;
import Model.Articolo;
import Model.Indirizzo;
import Model.Magazzino;
import Model.PuntoVendita;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PuntoVenditaDAOTest {

    IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();

    @Test
    public void isPuntoVendita(){
        Assert.assertTrue(puntoVenditaDAO.isPuntoVendita(1));
    }

    @Test
    public void getPuntoVendita(){
        PuntoVendita puntoVendita = puntoVenditaDAO.loadPuntoVendita(1);
        Assert.assertEquals("undefined", puntoVendita.getNome());
        System.out.println(puntoVendita);
    }

    @Test
    public void addPuntoVendita(){
        PuntoVendita puntoVendita = new PuntoVendita("prova", new Indirizzo("a","a","a","a","a"));
        Assert.assertEquals(1,puntoVenditaDAO.addPuntoVendita(puntoVendita));
    }

    @Test
    public void addUpdateVendita(){
        PuntoVendita puntoVendita = new PuntoVendita("PROVA2", new Indirizzo("a","a","a","a","a"));
        puntoVendita.setId(1);
        Assert.assertEquals(1,puntoVenditaDAO.updatePuntoVendita(puntoVendita));
    }

    @Test
    public void getAllPuntoVendita(){
        ArrayList<PuntoVendita> puntoVenditas = (ArrayList<PuntoVendita>) puntoVenditaDAO.loadAllPuntiVendita();
        Assert.assertNotNull(puntoVenditas);
    }

    @Test
    public void removePuntoVenditaOK(){
        Assert.assertEquals(0, puntoVenditaDAO.removePuntoVendita(2));
    }

}
