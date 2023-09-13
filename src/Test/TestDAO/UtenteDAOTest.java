package Test.TestDAO;

import DAO.IUtenteDAO;
import DAO.UtenteDAO;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
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
        Assert.assertTrue(utenteDAO.checkCredentials("cliente","cliente"));
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
        Persona identita = new Persona("Prova","Prova", "prova","3403403400", Date.valueOf("2023-01-01"));
        Indirizzo indirizzo = new Indirizzo("Italia","Lecce","73100","Via AAA","3");
        Cliente cliente = new Cliente(identita,"USER", "passw", indirizzo, Cliente.ProfessioneType.STUDENTE, Cliente.CanalePreferitoType.EMAIL,Date.valueOf(LocalDate.now()), 2, Cliente.StatoUtenteType.ABILITATO);

        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertEquals(2,utenteDAO.addCliente(cliente));
        Assert.assertEquals("Italia", cliente.getIndirizzo().getNazione());
    }

}
