package Test.TestDAO;

import DAO.IUtenteDAO;
import DAO.UtenteDAO;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOTest {
/*
    @Test
    public void getPassword(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertEquals("fede5",utenteDAO.getPassword("ale5"));
    }*/


    @Test
    public void userExists(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.userExists("ale"));
    }

    @Test
    public void checkCredentials(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.checkCredentials("ale5","fede5"));
    }

    @Test
    public void isCliente(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.isCliente("undefined"));
    }

    @Test
    public void isManager(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.isManager("undefined"));
    }

    @Test
    public void isAdmin(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.isAdmin("fede"));
    }


    @Test
    public void caricaCliente(){
        String usr = "BABBO";
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Cliente cliente = utenteDAO.loadCliente(usr);
        Assert.assertNotNull(cliente);
        System.out.println(cliente);
    }

    @Test
    public void caricaAllClienti(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        List<Cliente> utenti = utenteDAO.loadAllClienti();
        Assert.assertNotNull(utenti);
        System.out.println(utenti);
    }

    @Test
    public void addClienteOK(){
        Persona identita = new Persona("Alessandro","Convertino", "ale@gmail.com","3403403400",Timestamp.valueOf("2001-06-02 00:00:00"));
        Indirizzo indirizzo = new Indirizzo("Italia","Lecce","73100","Via AAA",3);
        Cliente cliente = new Cliente(identita,"Ale5", "Orango", indirizzo, Cliente.ProfessioneType.STUDENTE, Cliente.CanalePreferitoType.EMAIL, Timestamp.valueOf(LocalDateTime.now()), Cliente.StatoUtenteType.ABILITATO,null,null,null);

        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertEquals(2,utenteDAO.addCliente(cliente));
        Assert.assertEquals("Italia", cliente.getIndirizzo().getNazione());
    }

}
