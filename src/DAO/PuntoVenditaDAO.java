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

    private PuntoVenditaDAO(){
        puntoVendita = null;
        rs = null;
    }
    public static PuntoVenditaDAO getInstance() {
        return instance;
    }

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
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                puntoVendita.setMagazzini(magazzinoDAO.loadMagazziniOfPuntoVendita(rs.getInt("idPuntoVendita")));
                //puntoVendita.setArticoli();
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

    @Override
    public List<PuntoVendita> loadPuntiVendita() {
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
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
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
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
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

    @Override
    public List<PuntoVendita> loadPuntiVenditaOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.puntovendita AS P INNER JOIN myshop.puntovendita_has_cliente AS PC ON P.idPuntoVendita = PC.PuntoVendita_idPuntoVendita WHERE PC.Cliente_Utente_idUtente='"+ idCliente +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<PuntoVendita> puntiVendita = new ArrayList<>();

        try {
            while (rs.next()){
                puntoVendita = new PuntoVendita();
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
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

    @Override
    public int addPuntoVendita(PuntoVendita puntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.puntovendita (nazione, citta, cap, via, civico) VALUES (?,?,?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, puntoVendita.getIndirizzo().getNazione());
                preparedStatement.setString(2, puntoVendita.getIndirizzo().getCitta());
                preparedStatement.setString(3, puntoVendita.getIndirizzo().getCap());
                preparedStatement.setString(4, puntoVendita.getIndirizzo().getVia());
                preparedStatement.setInt(5, puntoVendita.getIndirizzo().getCivico());
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

    @Override
    public int updatePuntoVendita(PuntoVendita puntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.puntovendita SET nazione = ?, citta = ?, cap = ?, via = ?, civico = ? WHERE idPuntoVendita = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, puntoVendita.getIndirizzo().getNazione());
                preparedStatement.setString(2, puntoVendita.getIndirizzo().getCitta());
                preparedStatement.setString(3, puntoVendita.getIndirizzo().getCap());
                preparedStatement.setString(4, puntoVendita.getIndirizzo().getVia());
                preparedStatement.setInt(5, puntoVendita.getIndirizzo().getCivico());
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

    @Override
    public int removePuntoVendita(int idPuntoVendita) {

        magazzinoDAO.setFKPuntoVenditaToDefault(idPuntoVendita);

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.puntovendita WHERE idPuntoVendita = '" + idPuntoVendita + "';";
        IDbOperation removePV = new WriteOperation(sql);
        int rowCount = executor.executeOperation(removePV).getRowsAffected();
        removePV.close();
        return rowCount;
    }

    @Override
    public int registraCliente(int idCliente, int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.puntovendita_has_cliente (PuntoVendita_idPuntoVendita, Cliente_Utente_idUtente, data_registrazione) VALUES (?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setInt(1, idPuntoVendita);
                preparedStatement.setInt(2, idCliente);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
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

    @Override
    public int removeClienteFromPuntoVendita(int idCliente, int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.puntovendita_has_cliente WHERE Cliente_Utente_idUtente = '" + idCliente + "' AND PuntoVendita_idPuntoVendita = '" + idPuntoVendita + "';";
        IDbOperation removeCliente = new WriteOperation(sql);
        int rowCount = executor.executeOperation(removeCliente).getRowsAffected();
        removeCliente.close();
        return rowCount;
    }
}
