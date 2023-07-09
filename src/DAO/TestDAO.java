package DAO;

import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TestDAO {
    public static void main(String[] args) {
        System.out.println("Stampo tutti gli utenti che ho nel database");
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        ArrayList<Utente> utenti = utenteDAO.loadAllUtenti();
        for (Utente utente : utenti){
            System.out.println(utente);
        }
        boolean done = false;
        /*while (!done) {
            System.out.print("\nInserire l'username dell'utente da cercare (Q per terminare): ");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if ("Q".equalsIgnoreCase(input)) {
                done = true;
            } else {
                Cliente utente = utenteDAO.loadCliente(input);
                System.out.println(utente);
                if (utenteDAO.userExists(input)){
                    System.out.println("L'utente esiste");
                } else {
                    System.out.println("L'utente NON esiste");
                }

            }

        }*/

        /*Utente prova = new Utente(0,new Persona("Ciccio", "Bello", "cicciobello@gmail.com", "340", Timestamp.valueOf("2002-01-01 13:02:56.12345678")), "ciccio", "bello", new Indirizzo("nazione", "citta", 0, "via", 0));
        int rows = utenteDAO.addUtente(prova);/*

        /*int rows = utenteDAO.removeUtente("ciccio");
        System.out.println("Righe eliminate = " + rows);*/
        Cliente clienteP = new Cliente(new Persona("BABBO", "MINCHIA", "babbominchia@gmail.com", "340", Timestamp.valueOf("2002-01-01 13:02:56.12345678")), "BABBO", "babbo", new Indirizzo("nazione", "citta", "0", "via", 0), Cliente.ProfessioneType.IMPIEGATO, Cliente.CanalePreferitoType.EMAIL, Timestamp.valueOf("2022-11-01 13:02:56.12345678"), Cliente.StatoUtenteType.ABILITATO, new ArrayList<Messaggio>(), new ArrayList<ListaAcquisto>(), new ArrayList<PuntoVendita>());
       // utenteDAO.addCliente(clienteP);

        //utenteDAO.removeCliente("BABBO");
        /*if(utenteDAO.checkCredentials("ciccio", "bello")){
            System.out.println("Le credenziali sono corrette");
        } else {
            System.out.println("Le credenziali sono errate");
        }*/
        //utenteDAO.changeClienteStatus("BABBO", Cliente.StatoUtenteType.ABILITATO);

        if(utenteDAO.isBlocked("BABBO")){
            System.out.println("L'utente è bloccato");
        } else {
            System.out.println("L'utente è abilitato");
        }

        if (utenteDAO.isCliente("cliente")) {
            System.out.println("L'utente è un cliente");
        } else {
            System.out.println("L'utente non è un cliente");
        }

    }
}
