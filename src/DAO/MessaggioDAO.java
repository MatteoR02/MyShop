package DAO;

import DbInterface.command.*;
import Model.Messaggio;
import Model.PuntoVendita;
import Model.Recensione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessaggioDAO implements IMessaggioDAO{

    private static MessaggioDAO instance = new MessaggioDAO();
    private Messaggio messaggio;
    private static ResultSet rs;

    private MessaggioDAO(){
        messaggio = null;
        rs = null;
    }
    public static MessaggioDAO getInstance() {
        return instance;
    }

    @Override
    public Messaggio loadMessaggio(int idMessaggio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.messaggio WHERE idMessaggio = '" + idMessaggio + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                messaggio = new Messaggio();
                messaggio.setId(rs.getInt("idMessaggio"));
                messaggio.setTitolo(rs.getString("titolo"));
                messaggio.setTesto(rs.getString("testo"));
                messaggio.setStatoMessaggio(Messaggio.StatoMessaggioType.valueOf(rs.getString("stato")));
                //messaggio.setImmagine(rs.getBlob("immagine"));
                return messaggio;
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
    public List<Messaggio> loadMessaggi() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.messaggio;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Messaggio> messaggi = new ArrayList<>();

        try {
            while (rs.next()){
                messaggio = new Messaggio();
                messaggio.setId(rs.getInt("idMessaggio"));
                messaggio.setTitolo(rs.getString("titolo"));
                messaggio.setTesto(rs.getString("testo"));
                messaggio.setStatoMessaggio(Messaggio.StatoMessaggioType.valueOf(rs.getString("stato")));
                //messaggio.setImmagine(rs.getBlob("immagine"));
                messaggi.add(messaggio);
            } return messaggi;
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
    public List<Messaggio> loadMessaggiOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.messaggio as M INNER JOIN myshop.cliente_has_messaggio AS CM ON M.idMessaggio = CM.Messaggio_idMessaggio WHERE CM.Cliente_Utente_idUtente = '"+ idCliente + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Messaggio> messaggi = new ArrayList<>();

        try {
            while (rs.next()){
                messaggio = new Messaggio();
                messaggio.setId(rs.getInt("idMessaggio"));
                messaggio.setTitolo(rs.getString("titolo"));
                messaggio.setTesto(rs.getString("testo"));
                messaggio.setStatoMessaggio(Messaggio.StatoMessaggioType.valueOf(rs.getString("stato")));
                //messaggio.setImmagine(rs.getBlob("immagine"));
                messaggi.add(messaggio);
            } return messaggi;
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
    public List<Messaggio> loadMessaggiOfManager(int idManager) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.messaggio as M INNER JOIN myshop.manager_has_messaggio AS MM ON M.idMessaggio = MM.Messaggio_idMessaggio WHERE MM.Manager_Utente_idUtente = '"+ idManager + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Messaggio> messaggi = new ArrayList<>();

        try {
            while (rs.next()){
                messaggio = new Messaggio();
                messaggio.setId(rs.getInt("idMessaggio"));
                messaggio.setTitolo(rs.getString("titolo"));
                messaggio.setTesto(rs.getString("testo"));
                messaggio.setStatoMessaggio(Messaggio.StatoMessaggioType.valueOf(rs.getString("stato")));
                //messaggio.setImmagine(rs.getBlob("immagine"));
                messaggi.add(messaggio);
            } return messaggi;
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
    public int addMessaggio(Messaggio messaggio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.messaggio (titolo, testo, stato) VALUES (?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, messaggio.getTitolo());
                preparedStatement.setString(2, messaggio.getTesto());
                preparedStatement.setString(3, messaggio.getStatoMessaggio().toString());
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
    public int updateMessaggio(Messaggio messaggio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.messaggio SET titolo = ?, testo = ?, stato = ? WHERE idMessaggio = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, messaggio.getTitolo());
                preparedStatement.setString(2, messaggio.getTesto());
                preparedStatement.setString(3, messaggio.getStatoMessaggio().toString());
                preparedStatement.setInt(4, messaggio.getId());
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
    public int removeMessaggio(int idMessaggio) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlMesCliente = "DELETE FROM myshop.cliente_has_messaggio WHERE Messaggio_idMessaggio = '" + idMessaggio + "';";
        IDbOperation removeMesCliente = new WriteOperation(sqlMesCliente);
        int rowCountMesCliente = executor.executeOperation(removeMesCliente).getRowsAffected();
        removeMesCliente.close();

        String sqlMesManager = "DELETE FROM myshop.manager_has_messaggio WHERE Messaggio_idMessaggio = '" + idMessaggio + "';";
        IDbOperation removeMesManager = new WriteOperation(sqlMesManager);
        int rowCountMesManager = executor.executeOperation(removeMesManager).getRowsAffected();
        removeMesManager.close();

        String sqlMes = "DELETE FROM myshop.messaggio WHERE idMessaggio = '" + idMessaggio + "';";
        IDbOperation removeMes = new WriteOperation(sqlMes);
        int rowCountMes = executor.executeOperation(removeMes).getRowsAffected();
        removeMes.close();
        return rowCountMes + rowCountMesCliente + rowCountMesManager;
    }

    @Override
    public int sendMessaggioToManager(int idMessaggio, int idCliente, int idManager) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlMesCliente = "INSERT INTO myshop.cliente_has_messaggio (Cliente_Utente_idUtente, Messaggio_idMessaggio, come) VALUES (?,?,?);";
        String sqlMesManager = "INSERT INTO myshop.manager_has_messaggio (Manager_Utente_idUtente, Messaggio_idMessaggio, come) VALUES (?,?,?);";

        IDbOperation addMesCliente = new WriteByteOperation(sqlMesCliente);
        PreparedStatement preparedStatementCliente = executor.executeOperation(addMesCliente).getPreparedStatement();
        IDbOperation addMesManager = new WriteByteOperation(sqlMesManager);
        PreparedStatement preparedStatementManager = executor.executeOperation(addMesManager).getPreparedStatement();
        int rowCountMesCliente=0;
        int rowCountMesManager=0;
        try{
            if(preparedStatementCliente!=null) {
                preparedStatementCliente.setInt(1, idCliente);
                preparedStatementCliente.setInt(2, idMessaggio);
                preparedStatementCliente.setString(3, "MITTENTE");
                rowCountMesCliente = preparedStatementCliente.executeUpdate();
                preparedStatementCliente.close();
            }
            if(preparedStatementManager!=null) {
                preparedStatementManager.setInt(1, idManager);
                preparedStatementManager.setInt(2, idMessaggio);
                preparedStatementManager.setString(3, "DESTINATARIO");
                rowCountMesManager = preparedStatementManager.executeUpdate();
                preparedStatementManager.close();
            }
            return  rowCountMesCliente + rowCountMesManager;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addMesCliente.close();
        }
        return 0;
    }

    @Override
    public int sendMessaggioToCliente(int idMessaggio, int idCliente, int idManager) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlMesCliente = "INSERT INTO myshop.cliente_has_messaggio (Cliente_Utente_idUtente, Messaggio_idMessaggio, come) VALUES (?,?,?);";
        String sqlMesManager = "INSERT INTO myshop.manager_has_messaggio (Manager_Utente_idUtente, Messaggio_idMessaggio, come) VALUES (?,?,?);";

        IDbOperation addMesCliente = new WriteByteOperation(sqlMesCliente);
        PreparedStatement preparedStatementCliente = executor.executeOperation(addMesCliente).getPreparedStatement();
        IDbOperation addMesManager = new WriteByteOperation(sqlMesManager);
        PreparedStatement preparedStatementManager = executor.executeOperation(addMesManager).getPreparedStatement();
        int rowCountMesCliente=0;
        int rowCountMesManager=0;
        try{
            if(preparedStatementCliente!=null) {
                preparedStatementCliente.setInt(1, idCliente);
                preparedStatementCliente.setInt(2, idMessaggio);
                preparedStatementCliente.setString(3, "DESTINATARIO");
                rowCountMesCliente = preparedStatementCliente.executeUpdate();
                preparedStatementCliente.close();
            }
            if(preparedStatementManager!=null) {
                preparedStatementManager.setInt(1, idManager);
                preparedStatementManager.setInt(2, idMessaggio);
                preparedStatementManager.setString(3, "MITTENTE");
                rowCountMesManager = preparedStatementManager.executeUpdate();
                preparedStatementManager.close();
            }
            return  rowCountMesCliente + rowCountMesManager;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addMesCliente.close();
        }
        return 0;
    }
}
