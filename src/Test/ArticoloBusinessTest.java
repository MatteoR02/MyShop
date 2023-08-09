package Test;

import Business.ArticoloBusiness;
import Business.ExecuteResult;
import org.junit.Assert;
import org.junit.Test;

public class ArticoloBusinessTest {


    @Test
    public void isArticoloBoughtFromTest(){
        ExecuteResult<Boolean> result = ArticoloBusiness.isArticoloBoughtFrom(10,6);
        System.out.println(result.getMessage());
        Assert.assertTrue(result.getSingleObject());
    }
}
