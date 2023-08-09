package Test;

import Business.Strategy.OrdinamentoRecensioni.IOrdinamentoRecensioneStrategy;
import Business.Strategy.OrdinamentoRecensioni.OrdinamentoRecensioni;
import Business.Strategy.OrdinamentoRecensioni.RecensioniMiglioriStrategy;
import Business.Strategy.OrdinamentoRecensioni.RecensioniRecentiStrategy;
import Model.Recensione;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrdinamentoRecensioniTest {

    List<Recensione> listaRecensioni;

    @Before
    public void setUp() throws Exception{
        listaRecensioni = new ArrayList<Recensione>();

        Recensione r1 = new Recensione();
        r1.setId(1);
        r1.setValutazione(Recensione.Punteggio.MEDIOCRE);
        Date data1 = Date.valueOf("2023-01-01");
        r1.setData(data1);

        Recensione r2 = new Recensione();
        r2.setId(2);
        r2.setValutazione(Recensione.Punteggio.ECCELLENTE);
        Date data2 = Date.valueOf("2021-05-30");
        r2.setData(data2);

        Recensione r3 = new Recensione();
        r3.setId(3);
        r3.setValutazione(Recensione.Punteggio.SCARSO);
        Date data3 = Date.valueOf("2021-10-30");
        r3.setData(data3);

        listaRecensioni.add(r1);
        listaRecensioni.add(r2);
        listaRecensioni.add(r3);
    }

    @After
    public void tearDown(){
        listaRecensioni = null;
    }

    @Test
    public void ordinaTest(){
        OrdinamentoRecensioni ordinamentoRecensioni = new OrdinamentoRecensioni(listaRecensioni);
        IOrdinamentoRecensioneStrategy strategy = new RecensioniMiglioriStrategy();
        ordinamentoRecensioni.setOrdinamentoRecensioneStrategy(strategy);
        ordinamentoRecensioni.ordina();

        System.out.println(listaRecensioni);

        Assert.assertEquals(listaRecensioni.get(0).getId(), 2);
        Assert.assertEquals(listaRecensioni.get(1).getId(), 1);
        Assert.assertEquals(listaRecensioni.get(2).getId(), 3);

        strategy = new RecensioniRecentiStrategy();
        ordinamentoRecensioni.setOrdinamentoRecensioneStrategy(strategy);
        ordinamentoRecensioni.ordina();

        System.out.println(listaRecensioni);
        Assert.assertEquals(listaRecensioni.get(0).getId(), 1);
        Assert.assertEquals(listaRecensioni.get(1).getId(), 3);
        Assert.assertEquals(listaRecensioni.get(2).getId(), 2);
    }
}
