package Test.TestDAO;

import DAO.ArticoloDAO;
import DAO.IListaAcquistoDAO;
import DAO.ListaAcquistoDAO;
import Model.Articolo;
import Model.ListaAcquisto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaAcquistoDAOTest {

    private IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();

    @Test
    public void isLista(){
        Assert.assertTrue(listaAcquistoDAO.isListaAcquisto(90));
    }

    @Test
    public void getListeCliente(){
        ArrayList<ListaAcquisto> liste = listaAcquistoDAO.getListeOfCliente(6);
        Assert.assertNotNull(liste);
        System.out.println( liste );
    }

    @Test
    public void addLista(){
        Assert.assertEquals(1, listaAcquistoDAO.addListaAcquisto("prova",1));
    }

    @Test
    public void getListaClientenome(){
        ListaAcquisto lista = listaAcquistoDAO.loadListaAcquisto(3);
        Assert.assertNotNull(lista);
        System.out.println(lista);
    }
    @Test
    public void getListaClienteid(){
        ListaAcquisto lista = listaAcquistoDAO.loadListaAcquisto(90);
        Assert.assertNotNull(lista);
        System.out.println(lista.getArticoli().toString());
    }


    @Test
    public void getarticoloListaOK(){
        Map<Articolo, Integer> map = listaAcquistoDAO.getArticoliFromLista(1);
        Assert.assertNotNull(map);
        System.out.println(map);
    }

    @Test
    public void removeLista(){
        Assert.assertEquals(1, listaAcquistoDAO.removeListaAcquisto(90));
    }


    @Test
    public void updateArticoliNellaLista(){
        ListaAcquisto lista = listaAcquistoDAO.loadListaAcquisto(1);
        Articolo articolo = ArticoloDAO.getInstance().loadProdotto(12);
        lista.getArticoli().put(articolo, 2);
        Assert.assertEquals(2, listaAcquistoDAO.updateListaAcquisto(lista));
    }

    @Test
    public void insertArticoliNellaLista(){
        Assert.assertEquals(1, listaAcquistoDAO.insertArticoloInLista(1,1,1));
    }

    @Test
    public void updateArticoloNellaLista(){
        Assert.assertEquals(1, listaAcquistoDAO.updateArticoloInLista(1,1,4));
    }

    @Test
    public void removeArticoloDallaLista(){
        Assert.assertEquals(1, listaAcquistoDAO.removeArticoloInLista(1,1));
    }

    @Test
    public void getAllListe(){
        Assert.assertNotNull(listaAcquistoDAO.loadAllListaAcquisto());
    }



}
