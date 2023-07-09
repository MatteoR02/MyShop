package Test.TestDAO;

import DAO.CategoriaDAO;
import DAO.ICategoriaDAO;
import org.junit.Test;

public class CategoriaDAOTest {

    @Test
    public void loadCategoria(){
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        System.out.println(categoriaDAO.loadCategoria(1));
    }

    @Test
    public void loadAllCategorie(){
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        System.out.println(categoriaDAO.loadAllCategorie());
        //System.out.println(categoriaDAO.loadAllSottoCategoriaOfID(1));
    }
}
