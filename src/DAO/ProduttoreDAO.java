package DAO;

import DbInterface.IDbConnection;
import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduttoreDAO implements IProduttoreDAO{
    private static ProduttoreDAO instance = new ProduttoreDAO();
    private Produttore produttore;
    private static ResultSet rs;

    public static int PRODUTTORE_DEFAULT_ID = 1;

    private final ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    private ProduttoreDAO(){
        produttore = null;
        rs = null;
    }
    public static ProduttoreDAO getInstance() {
        return instance;
    }

    @Override
    public Produttore loadProduttore(int idProduttore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.produttore WHERE idProduttore = '" + idProduttore + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                produttore = new Produttore();
                produttore.setId(rs.getInt("idProduttore"));
                produttore.setNome(rs.getString("nome"));
                produttore.setSitoWeb(rs.getString("sito_web"));
                produttore.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                return produttore;
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
    public ArrayList<Produttore> loadAllProduttori() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.produttore WHERE idProduttore;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Produttore> produttori = new ArrayList<>();

        try {
            while(rs.next()){
                produttore = new Produttore();
                produttore.setId(rs.getInt("idProduttore"));
                produttore.setNome(rs.getString("nome"));
                produttore.setSitoWeb(rs.getString("sito_web"));
                produttore.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                produttori.add(produttore);
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
    public int addProduttore(Produttore produttore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.produttore (nome, sito_web, nazione, citta, cap, via, civico) VALUES (?,?,?,?,?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, produttore.getNome());
                preparedStatement.setString(2, produttore.getSitoWeb());
                preparedStatement.setString(3,produttore.getIndirizzo().getNazione());
                preparedStatement.setString(4,produttore.getIndirizzo().getCitta());
                preparedStatement.setString(5,produttore.getIndirizzo().getCap());
                preparedStatement.setString(6,produttore.getIndirizzo().getVia());
                preparedStatement.setInt(7, produttore.getIndirizzo().getCivico());
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
    public int updateProduttore(Produttore produttore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.produttore SET nome = ?, sito_web = ?, nazione = ?, citta = ?, cap = ?, via = ?, civico = ? WHERE idProduttore = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, produttore.getNome());
                preparedStatement.setString(2, produttore.getSitoWeb());
                preparedStatement.setString(3, produttore.getIndirizzo().getNazione());
                preparedStatement.setString(4, produttore.getIndirizzo().getCitta());
                preparedStatement.setString(5, produttore.getIndirizzo().getCap());
                preparedStatement.setString(6, produttore.getIndirizzo().getVia());
                preparedStatement.setInt(7, produttore.getIndirizzo().getCivico());
                preparedStatement.setInt(8, produttore.getId());
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
    public int removeProduttore(int idProduttore) {

        articoloDAO.setFKProduttoreToDefault(idProduttore);

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.produttore WHERE idProduttore = '" + idProduttore + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }
}
