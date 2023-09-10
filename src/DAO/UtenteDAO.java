package DAO;

import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class UtenteDAO implements IUtenteDAO{
    private static final UtenteDAO instance = new UtenteDAO();
    private Utente utente;
    private Cliente cliente;
    private Manager manager;
    private Admin admin;
    private static ResultSet rs;

    public static int CLIENTE_DEFAULT_ID = 1;

    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IRecensioneDAO recensioneDAO = RecensioneDAO.getInstance();
    private static final IMessaggioDAO messaggioDAO = MessaggioDAO.getInstance();
    private static final IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();

    private UtenteDAO(){
        utente = null;
        rs = null;
    }
    public static UtenteDAO getInstance() {
        return instance;
    }

    /**
     * carica un utente dal db in locale
     * @param username dell'utente da caricare
     * @return utente
     */
    public Utente loadUtente(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente WHERE username = '" + username + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                utente = new Utente();
                utente.setId(rs.getInt("idUtente"));
                utente.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                return utente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    /**
     * carica tutti gli utenti dal db
     * @return lista di utenti
     */
    @Override
    public ArrayList<Utente> loadAllUtenti() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Utente> utenti = new ArrayList<>();
        try {
            while (rs.next()) {
                utente = new Utente();
                utente.setId(rs.getInt("idUtente"));
                utente.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                utenti.add(utente);
            }
            return utenti;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    /**
     * aggiunge un utente al db
     * @param utente da aggiungere
     * @return numero di righe su cui sono state effettuate operazioni
     */
    @Override
    public int addUtente(Utente utente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO `myshop`.`utente` (`nome`, `cognome`, `email`, `username`, `password`," +
                " `telefono`, `data_nascita`, `nazione`,  `citta`, `via`, `cap`, `civico`) VALUES " +
                "('" + utente.getPersona().getNome() + "', '" +  utente.getPersona().getCognome() + "', '" + utente.getPersona().getEmail() +
                "', '" + utente.getUsername() + "', '" + utente.getPassword() +
                "', '" + utente.getPersona().getTelefono() + "', '" + utente.getPersona().getDataNascita() +
                "', '" + utente.getIndirizzo().getNazione() + "', '" + utente.getIndirizzo().getCitta() +
                "', '" + utente.getIndirizzo().getVia() + "', '" + utente.getIndirizzo().getCap() +
                "', '" + utente.getIndirizzo().getCivico() + "');";

        IDbOperation add = new WriteOperation(sql);
        int rowCount = executor.executeOperation(add).getRowsAffected();
        add.close();
        return rowCount;
    }

    /**
     * rimuove un utente dal db
     * @param username dell'utente da rimuovere
     * @return numero di righe su cui sono state effettuate operazioni
     */
    @Override
    public int removeUtente(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM Utente WHERE username = '" + username + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    /**
     * aggiorna un utente dal locale al db
     * @param utente da aggiornare
     * @return numero di righe su cui sono state effettuate operazioni
     */
    @Override
    public int updateUtente(Utente utente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE `myshop`.`utente` SET `nome` = '" + utente.getPersona().getNome() +
                "', `cognome` = '" + utente.getPersona().getCognome() +
                "', `email` = '" + utente.getPersona().getEmail() +
                "', `username` = '" + utente.getUsername() +
                "', `password` = '" + utente.getPassword() +
                "', `telefono` = '" + utente.getPersona().getTelefono() +
                "', `data_nascita` = '" + utente.getPersona().getDataNascita() +
                "', `nazione` = '" + utente.getIndirizzo().getNazione() +
                "', `citta` = '" + utente.getIndirizzo().getCitta() +
                "', `cap` = '" + utente.getIndirizzo().getCap() +
                "', `via` = '" + utente.getIndirizzo().getVia() +
                "', `civico` = '" + utente.getIndirizzo().getCivico() + "' WHERE `idUtente` = '" + utente.getId() + "';";

        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

    /**
     * controlla se l'utente esiste nel db
     * @param username dell'utente da controllare
     * @return true se l'utente esiste, false altrimenti
     */
    @Override
    public boolean userExists(String username) {
        String sql = "SELECT count(*) AS count FROM myshop.utente as U WHERE U.username='"+ username + "';";

        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            readOp.close();
        }
    }

    @Override
    public boolean userEmailExists(String email) {
        String sql = "SELECT count(*) AS count FROM myshop.utente as U WHERE U.email='"+ email + "';";

        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            readOp.close();
        }
    }

    /**
     * verifica se l'utente è un cliente
     * @param username dell'utente da verificare
     * @return true se è un cliente, false altrimenti
     */
    @Override
    public boolean isCliente(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.utente as U INNER JOIN myshop.cliente as C on U.idUtente = C.Utente_idUtente WHERE U.username='" + username+"';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            readOp.close();
        }
    }

    @Override
    public boolean isManager(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.utente as U INNER JOIN myshop.manager as M on U.idUtente = M.Utente_idUtente WHERE U.username='" + username+"';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            readOp.close();
        }
    }

    @Override
    public boolean isAdmin(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.utente as U INNER JOIN myshop.admin as A on U.idUtente = A.Utente_idUtente WHERE U.username='" + username+"';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            readOp.close();
        }
    }

    @Override
    public Cliente loadCliente(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.cliente as C on U.idUtente = C.Utente_idUtente WHERE U.username='" + username + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("idUtente"));
                cliente.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                cliente.setUsername(rs.getString("username"));
                cliente.setPassword(rs.getString("password"));
                cliente.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                cliente.setProfessione(Cliente.ProfessioneType.valueOf(rs.getString("professione")));
                cliente.setDataAbilitazione(rs.getDate("data_abilitazione"));
                cliente.setCanalePreferito(Cliente.CanalePreferitoType.valueOf(rs.getString("canale_preferito")));
                cliente.setStato(Cliente.StatoUtenteType.valueOf(rs.getString("stato")));
                cliente.setListeAcquisto(listaAcquistoDAO.getListeOfCliente(rs.getInt("idUtente")));
                //cliente.setMessaggi(messaggioDAO.loadMessaggiOfCliente(rs.getInt("idUtente")));
                cliente.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                //cliente.setPuntiVenditaRegistrati(puntoVenditaDAO.loadPuntiVenditaOfCliente(rs.getInt("idUtente")));
                return cliente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public Cliente loadCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.cliente as C on U.idUtente = C.Utente_idUtente WHERE U.idUtente='" + idCliente + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("idUtente"));
                cliente.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                cliente.setUsername(rs.getString("username"));
                cliente.setPassword(rs.getString("password"));
                cliente.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                cliente.setProfessione(Cliente.ProfessioneType.valueOf(rs.getString("professione")));
                cliente.setDataAbilitazione(rs.getDate("data_abilitazione"));
                cliente.setCanalePreferito(Cliente.CanalePreferitoType.valueOf(rs.getString("canale_preferito")));
                cliente.setStato(Cliente.StatoUtenteType.valueOf(rs.getString("stato")));
                cliente.setListeAcquisto(listaAcquistoDAO.getListeOfCliente(rs.getInt("idUtente")));
                //cliente.setMessaggi(messaggioDAO.loadMessaggiOfCliente(rs.getInt("idUtente")));
                cliente.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                //cliente.setPuntiVenditaRegistrati(puntoVenditaDAO.loadPuntiVenditaOfCliente(rs.getInt("idUtente")));
                return cliente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> loadAllClienti() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.cliente as C on U.idUtente = C.Utente_idUtente;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Cliente> clienti = new ArrayList<>();
        try {
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("idUtente"));
                cliente.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                cliente.setUsername(rs.getString("username"));
                cliente.setPassword(rs.getString("password"));
                cliente.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                cliente.setProfessione(Cliente.ProfessioneType.valueOf(rs.getString("professione")));
                cliente.setDataAbilitazione(rs.getDate("data_abilitazione"));
                cliente.setCanalePreferito(Cliente.CanalePreferitoType.valueOf(rs.getString("canale_preferito")));
                cliente.setStato(Cliente.StatoUtenteType.valueOf(rs.getString("stato")));
                cliente.setListeAcquisto(listaAcquistoDAO.getListeOfCliente(rs.getInt("idUtente")));
                //cliente.setMessaggi(messaggioDAO.loadMessaggiOfCliente(rs.getInt("idUtente")));
                cliente.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                //cliente.setPuntiVenditaRegistrati(puntoVenditaDAO.loadPuntiVenditaOfCliente(rs.getInt("idUtente")));
                clienti.add(cliente);
            }
            return clienti;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> loadAllClientiOfPV(int idPV) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.cliente WHERE PuntoVendita_idPuntoVendita ='"+ idPV +"' ;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Cliente> clienti = new ArrayList<>();
        ArrayList<Integer> idClienti = new ArrayList<>();
        try {
            while (rs.next()) {
              idClienti.add(rs.getInt("Utente_idUtente"));
            }
            for (int id: idClienti ) {
                clienti.add(loadCliente(findUsernameByID(id)));
            }
            return clienti;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public int addCliente(Cliente cliente) {

        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlUtente = "INSERT INTO `myshop`.`utente` (`nome`, `cognome`, `email`, `username`, `password`," +
                " `telefono`, `data_nascita`, `nazione`,  `citta`, `via`, `cap`, `civico`) VALUES " +
                "('" + cliente.getPersona().getNome() + "', '" + cliente.getPersona().getCognome() + "', '" + cliente.getPersona().getEmail() +
                "', '" + cliente.getUsername() + "', '" + cliente.getPassword() +
                "', '" + cliente.getPersona().getTelefono() + "', '" + cliente.getPersona().getDataNascita() +
                "', '" + cliente.getIndirizzo().getNazione() + "', '" + cliente.getIndirizzo().getCitta() +
                "', '" + cliente.getIndirizzo().getVia() + "', '" + cliente.getIndirizzo().getCap() +
                "', '" + cliente.getIndirizzo().getCivico() + "');";

        IDbOperation add = new WriteByteOperation(sqlUtente);

        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGen = -1;
            try {
                rowCount = statement.executeUpdate(); //aggiungo cliente
                try (ResultSet generatedID = statement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGen = generatedID.getInt(1);
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();

            String sqlCliente = "INSERT INTO `myshop`.`cliente` (`Utente_idUtente`, `professione`, `canale_preferito`, `data_abilitazione`, `stato`, `PuntoVendita_idPuntoVendita`)" +
                    "VALUES " +
                    "('" + idGen + "', '" + cliente.getProfessione() + "', '" + cliente.getCanalePreferito() +
                    "', '" + cliente.getDataAbilitazione() + "', '" + cliente.getStato() + "', '"+ cliente.getIdPuntoVendita() +"');";
            add = new WriteOperation(sqlCliente);
            rowCount += executor.executeOperation(add).getRowsAffected();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;

    }

    @Override
    public int removeCliente(String username) {
        int idUtente = findIdByUsername(username);
        DbOperationExecutor executor = new DbOperationExecutor();

        recensioneDAO.setFKClienteToDefault(findIdByUsername(username));

       /* for ( Messaggio mes : loadCliente(username).getMessaggi()) {
            messaggioDAO.removeMessaggio(mes.getId());
        }*/

        for ( ListaAcquisto lista : loadCliente(username).getListeAcquisto()) {
            listaAcquistoDAO.removeListaAcquisto(lista.getId());
        }

        String sqlDeleteCliente = "DELETE FROM `myshop`.`cliente` WHERE `Utente_idUtente` = '" + idUtente + "';";
        IDbOperation removeCliente = new WriteOperation(sqlDeleteCliente);
        int rowCountCliente = executor.executeOperation(removeCliente).getRowsAffected();
        removeCliente.close();

        String sqlDeleteUtente = "DELETE FROM `myshop`.`utente` WHERE `idUtente` = '" + idUtente + "';";
        IDbOperation removeUtente = new WriteOperation(sqlDeleteUtente);
        int rowCountUtente =  executor.executeOperation(removeUtente).getRowsAffected();
        removeUtente.close();

        return rowCountCliente + rowCountUtente;
    }

    @Override
    public int updateCliente(Cliente cliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlUpdateUtente = "UPDATE `myshop`.`utente` SET `nome` = '" + cliente.getPersona().getNome() +
                "', `cognome` = '" + cliente.getPersona().getCognome() +
                "', `email` = '" + cliente.getPersona().getEmail() +
                "', `username` = '" + cliente.getUsername() +
                "', `password` = '" + cliente.getPassword() +
                "', `telefono` = '" + cliente.getPersona().getTelefono() +
                "', `data_nascita` = '" + cliente.getPersona().getDataNascita() +
                "', `nazione` = '" + cliente.getIndirizzo().getNazione() +
                "', `citta` = '" + cliente.getIndirizzo().getCitta() +
                "', `cap` = '" + cliente.getIndirizzo().getCap() +
                "', `via` = '" + cliente.getIndirizzo().getVia() +
                "', `civico` = '" + cliente.getIndirizzo().getCivico() + "' WHERE `idUtente` = '" + cliente.getId() + "';";

        String sqlUpdateCliente = "UPDATE `myshop`.`cliente` SET `professione` = '" + cliente.getProfessione() +
                "', `canale_preferito` = '" + cliente.getCanalePreferito() +
                "', `data_abilitazione` = '" + cliente.getDataAbilitazione() +
                "', `stato` = '" + cliente.getStato() + "', `PuntoVendita_idPuntoVendita` = '" + cliente.getIdPuntoVendita() + "' WHERE `Utente_idUtente` = '" + cliente.getId() + "';";

        IDbOperation updateUtente = new WriteOperation(sqlUpdateUtente);
        int rowCountUtente = executor.executeOperation(updateUtente).getRowsAffected();
        updateUtente.close();

        IDbOperation updateCliente = new WriteOperation(sqlUpdateCliente);
        int rowCountCliente = executor.executeOperation(updateCliente).getRowsAffected();
        updateCliente.close();

        return rowCountUtente + rowCountCliente;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        String sql = "SELECT count(*) AS count FROM myshop.utente as U WHERE U.username='"+username+"' AND BINARY U.password='"+password+"';";

        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int blockCliente(String username) {
        int idCliente = findIdByUsername(username);
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlBlocKCliente = "UPDATE `myshop`.`cliente` SET `stato` = 'BLOCCATO' WHERE `Utente_idUtente` = '" + idCliente + "';";
        IDbOperation blockCliente = new WriteOperation(sqlBlocKCliente);
        int rowCount = executor.executeOperation(blockCliente).getRowsAffected();
        blockCliente.close();
        return rowCount;
    }

    @Override
    public int unlockCliente(String username) {
        int idCliente = findIdByUsername(username);
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlBlocKCliente = "UPDATE `myshop`.`cliente` SET `stato` = 'BLOCCATO' WHERE `Utente_idUtente` = '" + idCliente + "';";
        IDbOperation blockCliente = new WriteOperation(sqlBlocKCliente);
        int rowCount = executor.executeOperation(blockCliente).getRowsAffected();
        blockCliente.close();
        return rowCount;
    }

    @Override
    public int changeClienteStatus(String username, Cliente.StatoUtenteType stato){
        DbOperationExecutor executor = new DbOperationExecutor();
        int idUtente = findIdByUsername(username);

        String sqlChangeClienteStatus = "UPDATE `myshop`.`cliente` SET `stato` = '" + stato + "' WHERE `Utente_idUtente` = '" + idUtente + "';";
        IDbOperation changeStatus = new WriteOperation(sqlChangeClienteStatus);
        int rowCount = executor.executeOperation(changeStatus).getRowsAffected();
        changeStatus.close();
        return rowCount;
    }

    @Override
    public boolean isBlocked(String username) {
        int idUtente = findIdByUsername(username);
        String sql = "SELECT `stato` FROM myshop.cliente WHERE `Utente_idUtente`='"+idUtente+"';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                Cliente.StatoUtenteType stato = Cliente.StatoUtenteType.valueOf(rs.getString("stato"));
                return stato == Cliente.StatoUtenteType.BLOCCATO;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Manager loadManager(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.manager as M on U.idUtente = M.Utente_idUtente WHERE U.username='" + username + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                manager = new Manager();
                manager.setId(rs.getInt("idUtente"));
                manager.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                manager.setUsername(rs.getString("username"));
                manager.setPassword(rs.getString("password"));
                manager.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                manager.setIdPuntoVendita((puntoVenditaDAO.loadPuntoVenditaOfManager(rs.getInt("idUtente"))).getId());
                return manager;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public Manager loadManager(int idManager) {
      return null;
    }

    @Override
    public ArrayList<Manager> loadAllManager() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.manager as M on U.idUtente = M.Utente_idUtente;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Manager> managers = new ArrayList<>();
        try {
            while (rs.next()) {
                manager = new Manager();
                manager.setId(rs.getInt("idUtente"));
                manager.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                manager.setUsername(rs.getString("username"));
                manager.setPassword(rs.getString("password"));
                manager.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                //manager.setMessaggi(messaggioDAO.loadMessaggiOfManager(rs.getInt("idUtente")));

                managers.add(manager);
            }
            return managers;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public int addManager(Manager manager) {
        DbOperationExecutor executor = new DbOperationExecutor();

        /*String sqlUtente = "INSERT INTO `myshop`.`utente` (`nome`, `cognome`, `email`, `username`, `password`," +
                " `telefono`, `data_nascita`, `nazione`,  `citta`, `via`, `cap`, `civico`) VALUES " +
                "('" + manager.getPersona().getNome() + "', '" + manager.getPersona().getCognome() + "', '" + manager.getPersona().getEmail() +
                "', '" + manager.getUsername() + "', '" + manager.getPassword() +
                "', '" + manager.getPersona().getTelefono() + "', '" + manager.getPersona().getDataNascita() +
                "', '" + manager.getIndirizzo().getNazione() + "', '" + manager.getIndirizzo().getCitta() +
                "', '" + manager.getIndirizzo().getVia() + "', '" + manager.getIndirizzo().getCap() +
                "', '" + manager.getIndirizzo().getCivico() + "');";

        IDbOperation add = new WriteByteOperation(sqlUtente);*/

        String sqlUtente = "INSERT INTO myshop.utente (nome, cognome, email, username, password, telefono, data_nascita, nazione, citta, via, cap, civico) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

        int rowCount = 0;
        IDbOperation addByte = new WriteByteOperation(sqlUtente);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        try {

            int idGen = -1;
            try {
                if(preparedStatement!=null) {
                    preparedStatement.setString(1, manager.getPersona().getNome());
                    preparedStatement.setString(2, manager.getPersona().getCognome());
                    preparedStatement.setString(3, manager.getPersona().getEmail());
                    preparedStatement.setString(4, manager.getUsername());
                    preparedStatement.setString(5, manager.getPassword());
                    preparedStatement.setString(6, manager.getPersona().getTelefono());
                    preparedStatement.setDate(7, manager.getPersona().getDataNascita());
                    preparedStatement.setString(8, manager.getIndirizzo().getNazione());
                    preparedStatement.setString(9, manager.getIndirizzo().getCitta());
                    preparedStatement.setString(10, manager.getIndirizzo().getCap());
                    preparedStatement.setString(11, manager.getIndirizzo().getVia());
                    preparedStatement.setString(12, manager.getIndirizzo().getCivico());

                    rowCount = preparedStatement.executeUpdate();
                }
                try (ResultSet generatedID = preparedStatement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGen = generatedID.getInt(1);
                    }
                }
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            preparedStatement.close();

           String sqlManager = "INSERT INTO `myshop`.`manager` (`Utente_idUtente`) VALUES " +
                    "('" + idGen + "');";

            addByte = new WriteOperation(sqlManager);
            rowCount += executor.executeOperation(addByte).getRowsAffected();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addByte.close();
        }
        return rowCount;

    }

    @Override
    public int removeManager(String username) {
        int idUtente = findIdByUsername(username);
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlDeleteManager = "DELETE FROM `myshop`.`manager` WHERE `Utente_idUtente` = '" + idUtente + "';";
        IDbOperation removeManager = new WriteOperation(sqlDeleteManager);
        int rowCountManager = executor.executeOperation(removeManager).getRowsAffected();
        removeManager.close();

        String sqlDeleteUtente = "DELETE FROM `myshop`.`utente` WHERE `idUtente` = '" + idUtente + "';";
        IDbOperation removeUtente = new WriteOperation(sqlDeleteUtente);
        int rowCountUtente =  executor.executeOperation(removeUtente).getRowsAffected();
        removeUtente.close();

        return rowCountManager + rowCountUtente;
    }

    @Override
    public int updateManager(Manager manager) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlUpdateUtente = "UPDATE `myshop`.`utente` SET `nome` = '" + manager.getPersona().getNome() +
                "', `cognome` = '" + manager.getPersona().getCognome() +
                "', `email` = '" + manager.getPersona().getEmail() +
                "', `username` = '" + manager.getUsername() +
                "', `password` = '" + manager.getPassword() +
                "', `telefono` = '" + manager.getPersona().getTelefono() +
                "', `data_nascita` = '" + manager.getPersona().getDataNascita() +
                "', `nazione` = '" + manager.getIndirizzo().getNazione() +
                "', `citta` = '" + manager.getIndirizzo().getCitta() +
                "', `cap` = '" + manager.getIndirizzo().getCap() +
                "', `via` = '" + manager.getIndirizzo().getVia() +
                "', `civico` = '" + manager.getIndirizzo().getCivico() + "' WHERE `idUtente` = '" + manager.getId() + "';";

        /*String sqlUpdateManager = "UPDATE `myshop`.`cliente` SET `professione` = '" + manager.getProfessione() +
                "', `canale_preferito` = '" + cliente.getCanalePreferito() +
                "', `data_abilitazione` = '" + cliente.getDataAbilitazione() +
                "', `stato` = '" + cliente.getStato() + "' WHERE `Utente_idUtente` = '" + cliente.getId() + "';";*/

        String sqlUpdateManager = "";

        IDbOperation updateUtente = new WriteOperation(sqlUpdateUtente);
        int rowCountUtente = executor.executeOperation(updateUtente).getRowsAffected();
        updateUtente.close();

        IDbOperation updateManager = new WriteOperation(sqlUpdateManager);
        int rowCountCliente = executor.executeOperation(updateManager).getRowsAffected();
        updateManager.close();

        return rowCountUtente + rowCountCliente;
    }

    @Override
    public int assignManager(int idManager, int idPuntoVendita) {
        String sql = "UPDATE puntoVendita SET dipendente_idDipendente= '" + idManager +"' WHERE idPuntoVendita='" + idPuntoVendita +"';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation writeOp = new WriteOperation(sql);

        int rowCount = executor.executeOperation(writeOp).getRowsAffected();
        writeOp.close();
        return rowCount;
    }

    @Override
    public Admin loadAdmin(String username) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.admin as A on U.idUtente = A.Utente_idUtente WHERE U.username='" + username + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                admin = new Admin();
                admin.setId(rs.getInt("idUtente"));
                admin.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                return admin;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public Admin loadAdmin(int idAdmin) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.admin as A on U.idUtente = A.Utente_idUtente WHERE U.idUtente='" + idAdmin + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                admin = new Admin();
                admin.setId(rs.getInt("idUtente"));
                admin.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                return admin;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public ArrayList<Admin> loadAllAdmin() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.utente as U INNER JOIN myshop.admin as A on U.idUtente = A.Utente_idUtente;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Admin> admins = new ArrayList<>();
        try {
            while (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("idUtente"));
                admin.setPersona(new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("telefono"), rs.getDate("data_nascita")));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
            admin.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                admins.add(admin);
            }
            return admins;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readOp.close();
        }
        return null;
    }

    @Override
    public int addAdmin(Admin admin) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlUtente = "INSERT INTO `myshop`.`utente` (`nome`, `cognome`, `email`, `username`, `password`," +
                " `telefono`, `data_nascita`, `nazione`,  `citta`, `via`, `cap`, `civico`) VALUES " +
                "('" + admin.getPersona().getNome() + "', '" + admin.getPersona().getCognome() + "', '" + admin.getPersona().getEmail() +
                "', '" + admin.getUsername() + "', '" + admin.getPassword() +
                "', '" + admin.getPersona().getTelefono() + "', '" + admin.getPersona().getDataNascita() +
                "', '" + admin.getIndirizzo().getNazione() + "', '" + admin.getIndirizzo().getCitta() +
                "', '" + admin.getIndirizzo().getVia() + "', '" + admin.getIndirizzo().getCap() +
                "', '" + admin.getIndirizzo().getCivico() + "');";

        IDbOperation add = new WriteByteOperation(sqlUtente);

        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGen = -1;
            try {
                rowCount = statement.executeUpdate(); //aggiungo admin
                try (ResultSet generatedID = statement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGen = generatedID.getInt(1);
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();

            String sqlAdmin = "INSERT INTO `myshop`.`admin` (`Utente_idUtente`) VALUES " +
                    "('" + idGen + "', '" +  "');";
            add = new WriteOperation(sqlAdmin);
            rowCount += executor.executeOperation(add).getRowsAffected();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;

    }

    @Override
    public int removeAdmin(String username) {
        int idUtente = findIdByUsername(username);
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlDeleteAdmin = "DELETE FROM `myshop`.`admin` WHERE `Utente_idUtente` = '" + idUtente + "';";
        IDbOperation removeAdmin = new WriteOperation(sqlDeleteAdmin);
        int rowCountAdmin = executor.executeOperation(removeAdmin).getRowsAffected();
        removeAdmin.close();

        String sqlDeleteUtente = "DELETE FROM `myshop`.`utente` WHERE `idUtente` = '" + idUtente + "';";
        IDbOperation removeUtente = new WriteOperation(sqlDeleteUtente);
        int rowCountUtente =  executor.executeOperation(removeUtente).getRowsAffected();
        removeUtente.close();

        return rowCountAdmin + rowCountUtente;
    }

    @Override
    public int updateAdmin(Admin admin) {
        return 0;
    }

    public int findIdByUsername(String username){
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlFindId = "SELECT `idUtente` FROM myshop.utente WHERE `username` = '" + username + "';";
        IDbOperation readId = new ReadOperation(sqlFindId);
        rs = executor.executeOperation(readId).getResultSet();
        int idUtente = -1;

        try {
            rs.next();
            if (rs.getRow() == 1) {
                idUtente = rs.getInt("idUtente");
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readId.close();
        }
        return idUtente;
    }

    public String findUsernameByID(int id){
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlFindId = "SELECT `username` FROM myshop.utente WHERE `idUtente` = '" + id + "';";
        IDbOperation readId = new ReadOperation(sqlFindId);
        rs = executor.executeOperation(readId).getResultSet();
        String username="";

        try {
            rs.next();
            if (rs.getRow() == 1) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            readId.close();
        }
        return username;
    }

    @Override
    public int setFKPuntoVenditaToDefault(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.cliente SET PuntoVendita_idPuntoVendita = '" + PuntoVenditaDAO.PUNTOVENDITA_DEFAULT_ID +
                "' WHERE `PuntoVendita_idPuntoVendita` = '" + idPuntoVendita + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

}
