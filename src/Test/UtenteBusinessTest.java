package Test;

import Business.ExecuteResult;
import Business.ListaAcquistoBusiness;
import Business.UtenteBusiness;
import DAO.IUtenteDAO;
import DAO.UtenteDAO;
import Model.Cliente;
import Model.ListaAcquisto;
import org.junit.Assert;
import org.junit.Test;

public class UtenteBusinessTest {

    @Test
    public void bloccaUtente(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Cliente cliente = utenteDAO.loadCliente("USER");
        Assert.assertTrue(UtenteBusiness.changeClienteStatus(cliente.getId(), Cliente.StatoUtenteType.BLOCCATO));
        // NON FUNZIONA PERCHè L'EMAIL NON è VALIDA
    }



}
