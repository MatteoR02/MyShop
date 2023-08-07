package Test;

import Business.ArticoloBusiness;
import Business.ExecuteResult;
import Business.Strategy.IOrdinamentoRecensioneStrategy;
import Business.Strategy.OrdinamentoRecensioni;
import Business.Strategy.RecensioniMiglioriStrategy;
import Business.Strategy.RecensioniRecentiStrategy;
import Model.Recensione;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ArticoloBusinessTest {


    @Test
    public void isArticoloBoughtFromTest(){
        ExecuteResult<Boolean> result = ArticoloBusiness.isArticoloBoughtFrom(10,6);
        System.out.println(result.getMessage());
        Assert.assertTrue(result.getSingleObject());
    }
}
