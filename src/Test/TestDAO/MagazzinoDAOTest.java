package Test.TestDAO;

import DAO.IMagazzinoDAO;
import DAO.MagazzinoDAO;
import Model.Indirizzo;
import Model.Magazzino;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MagazzinoDAOTest {

    private IMagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();

    @Test
    public void getMagazzino(){
        Magazzino magazzino = magazzinoDAO.loadMagazzino(1);
        Assert.assertNotNull(magazzino);
        System.out.println(magazzino);
    }

    @Test
    public void getAllMagazzino(){
        List<Magazzino> magazzini = magazzinoDAO.loadAllMagazzini();
        Assert.assertNotNull(magazzini);
        System.out.println(magazzini);
    }

    @Test
    public void getAllMagazzinoFromPuntoVendita(){
        ArrayList<Magazzino> magazzini = (ArrayList<Magazzino>) magazzinoDAO.loadMagazziniOfPuntoVendita(1);
        Assert.assertNotNull(magazzini);
        System.out.println(magazzini);
    }

    @Test
    public void addMagazzinoOK(){
        Magazzino magazzino = new Magazzino(new Indirizzo("test", "test", "test", "test", "test"), 1);
        Assert.assertEquals(1,magazzinoDAO.addMagazzino(magazzino));
    }

    @Test
    public void updateMagazzino(){
        Magazzino magazzino = new Magazzino( new Indirizzo("test2", "test2", "test2", "test2", "test2"), 1);
        Assert.assertEquals(1,magazzinoDAO.updateMagazzino(magazzino));
    }

    @Test
    public void removeMagazzino(){
        Assert.assertEquals(1, magazzinoDAO.removeMagazzino(3));
    }


}
