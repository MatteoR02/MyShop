package DAO;

import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ErogatoreDAO implements IErogatoreDAO {
    private static final ErogatoreDAO instance = new ErogatoreDAO();
    private Erogatore erogatore;
    private static ResultSet rs;

    public static int PRODUTTORE_DEFAULT_ID = 1;

    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    private ErogatoreDAO(){
        erogatore = null;
        rs = null;
    }
    public static ErogatoreDAO getInstance() {
        return instance;
    }

    @Override
    public Erogatore loadErogatore(int idErogatore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.erogatore WHERE idErogatore = '" + idErogatore + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                erogatore = new Erogatore();
                erogatore.setId(rs.getInt("idErogatore"));
                erogatore.setNome(rs.getString("nome"));
                erogatore.setSitoWeb(rs.getString("sito_web"));
                erogatore.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                return erogatore;
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
    public ArrayList<Erogatore> loadAllErogatori() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.erogatore;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Erogatore> produttori = new ArrayList<>();

        try {
            while(rs.next()){
                erogatore = new Erogatore();
                erogatore.setId(rs.getInt("idErogatore"));
                erogatore.setNome(rs.getString("nome"));
                erogatore.setSitoWeb(rs.getString("sito_web"));
                erogatore.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getString("civico")));
                produttori.add(erogatore);
            }return produttori;
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
    public int addErogatore(Erogatore erogatore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.erogatore (nome, sito_web, nazione, citta, cap, via, civico) VALUES (?,?,?,?,?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, erogatore.getNome());
                preparedStatement.setString(2, erogatore.getSitoWeb());
                preparedStatement.setString(3, erogatore.getIndirizzo().getNazione());
                preparedStatement.setString(4, erogatore.getIndirizzo().getCitta());
                preparedStatement.setString(5, erogatore.getIndirizzo().getCap());
                preparedStatement.setString(6, erogatore.getIndirizzo().getVia());
                preparedStatement.setString(7, erogatore.getIndirizzo().getCivico());
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
    public int updateErogatore(Erogatore erogatore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.erogatore SET nome = ?, sito_web = ?, nazione = ?, citta = ?, cap = ?, via = ?, civico = ? WHERE idProduttore = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, erogatore.getNome());
                preparedStatement.setString(2, erogatore.getSitoWeb());
                preparedStatement.setString(3, erogatore.getIndirizzo().getNazione());
                preparedStatement.setString(4, erogatore.getIndirizzo().getCitta());
                preparedStatement.setString(5, erogatore.getIndirizzo().getCap());
                preparedStatement.setString(6, erogatore.getIndirizzo().getVia());
                preparedStatement.setString(7, erogatore.getIndirizzo().getCivico());
                preparedStatement.setInt(8, erogatore.getId());
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
    public int removeErogatore(int idErogatore) {

        articoloDAO.setFKErogatoreToDefault(idErogatore);

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.erogatore WHERE idErogatore = '" + idErogatore + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }
}
