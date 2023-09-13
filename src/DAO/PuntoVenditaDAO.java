package DAO;

import DbInterface.command.*;
import Model.Indirizzo;
import Model.Magazzino;
import Model.PuntoVendita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PuntoVenditaDAO implements IPuntoVenditaDAO{

    private static final PuntoVenditaDAO instance = new PuntoVenditaDAO();
    private PuntoVendita puntoVendita;
    private static ResultSet rs;

    public static int PUNTOVENDITA_DEFAULT_ID = 1;

    private static final IMagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();

    private PuntoVenditaDAO(){
        puntoVendita = null;
        rs = null;
    }
    public static PuntoVenditaDAO getInstance() {
        return instance;
    }

    /**
     * Verifica se l'id fornito appartiene ad un punto vendita sul database
     * @param idPuntoVendita
     * @return true se l'id appartiene ad un punto vendita, false altrimenti
     */
    @Override
    public boolean isPuntoVendita(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.puntovendita WHERE idPuntoVendita='" + idPuntoVendita + "';";

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
     * Carica un particolare punto vendita dal database
     * @param idPuntoVendita
     * @return
     */
    @Override
    public PuntoVendita loadPuntoVendita(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.puntovendita WHERE idPuntoVendita = '" + idPuntoVendita + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita = new PuntoVendita();
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                puntoVendita.setMagazzini(magazzinoDAO.loadMagazziniOfPuntoVendita(rs.getInt("idPuntoVendita")));
                return puntoVendita;
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
     * Carica tutti i punti vendita dal database
     * @return
     */
    @Override
    public List<PuntoVendita> loadAllPuntiVendita() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.puntovendita;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<PuntoVendita> puntiVendita = new ArrayList<>();

        try {
            while (rs.next()){
                puntoVendita = new PuntoVendita();
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setNome(rs.getString("nome"));
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                puntoVendita.setMagazzini(magazzinoDAO.loadMagazziniOfPuntoVendita(rs.getInt("idPuntoVendita")));
                //puntoVendita.setArticoli();
                puntiVendita.add(puntoVendita);
            } return puntiVendita;
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
     * Carica il punto vendita assegnato ad un particolare manager
     * @param idManager
     * @return
     */
    @Override
    public PuntoVendita loadPuntoVenditaOfManager(int idManager) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.puntovendita WHERE Manager_Utente_idUtente = '" + idManager + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita = new PuntoVendita();
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setNome(rs.getString("nome"));
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                puntoVendita.setMagazzini(magazzinoDAO.loadMagazziniOfPuntoVendita(rs.getInt("idPuntoVendita")));
                puntoVendita.setArticoli(articoloDAO.loadAllArticoliFromPuntoVendita(rs.getInt("idPuntoVendita")));
                return puntoVendita;
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
     * Aggiunge un nuovo punto vendita sul database
     * @param puntoVendita
     * @return numero di righe modificate sul database
     */
    @Override
    public int addPuntoVendita(PuntoVendita puntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.puntovendita (nome, nazione, citta, cap, via, civico) VALUES (?,?,?,?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, puntoVendita.getNome());
                preparedStatement.setString(2, puntoVendita.getIndirizzo().getNazione());
                preparedStatement.setString(3, puntoVendita.getIndirizzo().getCitta());
                preparedStatement.setString(4, puntoVendita.getIndirizzo().getCap());
                preparedStatement.setString(5, puntoVendita.getIndirizzo().getVia());
                preparedStatement.setString(6, puntoVendita.getIndirizzo().getCivico());
                rowCount = preparedStatement.executeUpdate();
                preparedStatement.close();
                return rowCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addByte.close();
        }
        return 0;
    }

    /**
     * Aggiorna un particolare punto vendita sul database
     * @param puntoVendita
     * @return numero di righe modificate sul database
     */
    @Override
    public int updatePuntoVendita(PuntoVendita puntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.puntovendita SET nome = ?, nazione = ?, citta = ?, cap = ?, via = ?, civico = ? WHERE idPuntoVendita = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, puntoVendita.getNome());
                preparedStatement.setString(2, puntoVendita.getIndirizzo().getNazione());
                preparedStatement.setString(3, puntoVendita.getIndirizzo().getCitta());
                preparedStatement.setString(4, puntoVendita.getIndirizzo().getCap());
                preparedStatement.setString(5, puntoVendita.getIndirizzo().getVia());
                preparedStatement.setString(6, puntoVendita.getIndirizzo().getCivico());
                preparedStatement.setInt(7, puntoVendita.getId());
                rowCount = preparedStatement.executeUpdate();
                preparedStatement.close();
                return rowCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addByte.close();
        }
        return 0;
    }

    /**
     * Elimina un particolare punto vendita dal database
     * @param idPuntoVendita
     * @return numero di righe modificate sul database
     */
    @Override
    public int removePuntoVendita(int idPuntoVendita) {

        magazzinoDAO.setFKPuntoVenditaToDefault(idPuntoVendita);
        utenteDAO.setFKPuntoVenditaToDefault(idPuntoVendita);

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.puntovendita WHERE idPuntoVendita = '" + idPuntoVendita + "';";
        IDbOperation removePV = new WriteOperation(sql);
        int rowCount = executor.executeOperation(removePV).getRowsAffected();
        removePV.close();
        return rowCount;
    }

    /**
     * Aggiunge un particolare articolo ad un particolare punto vendita
     * @param idArticolo
     * @param idPuntoVendita
     * @return numero di righe modificate sul database
     */
    @Override
    public int addArticoloToPuntoVendita(int idArticolo, int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.puntovendita_has_articolo (PuntoVendita_idPuntoVendita, Articolo_idArticolo) VALUES (?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setInt(1, idPuntoVendita);
                preparedStatement.setInt(2, idArticolo);
                rowCount = preparedStatement.executeUpdate();
                preparedStatement.close();
                return rowCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addByte.close();
        }
        return 0;
    }
}
