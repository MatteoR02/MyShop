package Test.TestDAO;
import DAO.*;
import Model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArticoloDAOTest {

    private IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    private IProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    private Prodotto prodotto;
    private ProdottoComposito prodottoComposito;
    private Categoria categoria;
    private Produttore produttore;
    private Servizio servizio;

    @Before
    public void setUp(){
        categoria = categoriaDAO.loadCategoria(3);
        produttore = produttoreDAO.loadProduttore(1);

        prodottoComposito = new ProdottoComposito();
        prodottoComposito.setNome("CompositoPROVA");
        prodottoComposito.setPrezzo(0F);
        prodottoComposito.setCategoria(categoria);

        Magazzino magazzino = new Magazzino();

        Collocazione collocazione = new Collocazione(3,"10",3,magazzino);

        Prodotto sProd1 = new Prodotto();
        sProd1.setNome("PROVA1");
        sProd1.setPrezzo(20F);
        sProd1.setProduttore(produttore);
        sProd1.setCategoria(categoria);
        sProd1.setCollocazione(collocazione);

        Prodotto sProd2= new Prodotto();
        sProd2.setNome("PROVA2");
        sProd2.setPrezzo(50F);
        sProd2.setProduttore(produttore);
        sProd2.setCategoria(categoria);
        sProd2.setCollocazione(collocazione);

        Prodotto sProd3 = new Prodotto();
        sProd3.setNome("PROVA3");
        sProd3.setPrezzo(200F);
        sProd3.setProduttore(produttore);
        sProd3.setCategoria(categoria);
        sProd3.setCollocazione(collocazione);


        prodottoComposito.addSottoProdotti(sProd1);
        prodottoComposito.addSottoProdotti(sProd2);
        prodottoComposito.addSottoProdotti(sProd3);

        prodotto = new Prodotto();
        prodotto.setNome("ToDelete");
        prodotto.setPrezzo(999F);
        prodotto.setCategoria(categoria);
        prodotto.setProduttore(produttore);
        prodotto.setCollocazione(new Collocazione(5,"3", 999,magazzino));

        servizio = new Servizio();
        servizio.setCategoria(categoria);
        servizio.setNome("ToDeleteS");
        servizio.setPrezzo(999F);

    }

    @Test
    public void isServizio(){
        IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        Assert.assertTrue(articoloDAO.isServizio(6));
    }

    @Test
    public void isProdotto(){
        IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        Assert.assertTrue(articoloDAO.isProdotto(24));
    }

    @Test
    public void loadProdotto(){
        System.out.println(articoloDAO.loadProdotto(2));
    }

    @Test
    public void loadAllProdotti(){
        ArrayList<Articolo> prodotti =  articoloDAO.loadAllProdotti();
        System.out.println(prodotti);
    }

    @Test
    public void loadProdottoComposito(){
        System.out.println(articoloDAO.loadProdottoComposito(11));
    }

    @Test
    public void loadAllArticoliOfPV(){
        System.out.println(articoloDAO.loadAllArticoliFromPuntoVendita(2));
    }



}
