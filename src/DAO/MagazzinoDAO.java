package DAO;

import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MagazzinoDAO implements IMagazzinoDAO{

    private static MagazzinoDAO instance = new MagazzinoDAO();
    private Magazzino magazzino;
    private Collocazione collocazione;
    private static ResultSet rs;

    public static int MAGAZZINO_DEFAULT_ID = 1;

    private final ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    private MagazzinoDAO(){
        collocazione = null;
        magazzino = null;
        rs = null;
    }
    public static MagazzinoDAO getInstance() {
        return instance;
    }

    @Override
    public Magazzino loadMagazzino(int idMagazzino) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.magazzino WHERE idMagazzino = '" + idMagazzino + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                magazzino = new Magazzino();
                magazzino.setId(rs.getInt("idMagazzino"));
                magazzino.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                magazzino.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                return magazzino;
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
    public List<Magazzino> loadAllMagazzini() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.magazzino;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Magazzino> magazzini = new ArrayList<>();

        try {
            while (rs.next()){
                magazzino = new Magazzino();
                magazzino.setId(rs.getInt("idMagazzino"));
                magazzino.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                magazzino.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                magazzini.add(magazzino);
            }return magazzini;
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
    public List<Magazzino> loadMagazziniOfPuntoVendita(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.magazzino WHERE PuntoVendita_idPuntoVendita = '"+ idPuntoVendita +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Magazzino> magazzini = new ArrayList<>();

        try {
            while (rs.next()){
                magazzino = new Magazzino();
                magazzino.setId(rs.getInt("idMagazzino"));
                magazzino.setIndirizzo(new Indirizzo(rs.getString("nazione"), rs.getString("citta"), rs.getString("cap"), rs.getString("via"), rs.getInt("civico")));
                magazzino.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                magazzini.add(magazzino);
            }return magazzini;
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
    public Collocazione loadCollocazioneOfProdotto(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.magazzino_has_prodotto WHERE Prodotto_Articolo_idArticolo = '" + idProdotto + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        int idMagazzino = 0;

        try {
            rs.next();
            magazzino = new Magazzino();
            collocazione = new Collocazione();
            if (rs.getRow() == 1) {
                idMagazzino = rs.getInt("Magazzino_idMagazzino");
                collocazione.setQuantita(rs.getInt("quantita"));
                collocazione.setCorsia(rs.getString("corsia"));
                collocazione.setScaffale(rs.getInt("scaffale"));
            }
            collocazione.setMagazzino(loadMagazzino(idMagazzino));
            return collocazione;
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
    public int addMagazzino(Magazzino magazzino) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.magazzino (nazione, citta, cap, via, civico, PuntoVendita_idPuntoVendita) VALUES (?,?,?,?,?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, magazzino.getIndirizzo().getNazione());
                preparedStatement.setString(2, magazzino.getIndirizzo().getCitta());
                preparedStatement.setString(3, magazzino.getIndirizzo().getCap());
                preparedStatement.setString(4, magazzino.getIndirizzo().getVia());
                preparedStatement.setInt(5, magazzino.getIndirizzo().getCivico());
                preparedStatement.setInt(6, magazzino.getIdPuntoVendita());
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
    public int updateMagazzino(Magazzino magazzino) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.magazzino SET nazione = ?, citta = ?, cap = ?, via = ?, civico = ?, PuntoVendita_idPuntoVendita = ? WHERE idMagazzino = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, magazzino.getIndirizzo().getNazione());
                preparedStatement.setString(2, magazzino.getIndirizzo().getCitta());
                preparedStatement.setString(3, magazzino.getIndirizzo().getCap());
                preparedStatement.setString(4, magazzino.getIndirizzo().getVia());
                preparedStatement.setInt(5, magazzino.getIndirizzo().getCivico());
                preparedStatement.setInt(6, magazzino.getIdPuntoVendita());
                preparedStatement.setInt(7, magazzino.getId());
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
    public int removeMagazzino(int idMagazzino) {

        articoloDAO.setFKMagazzinoToDefault(idMagazzino);

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.magazzino WHERE idMagazzino = '" + idMagazzino + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    @Override
    public int setFKPuntoVenditaToDefault(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.magazzino SET PuntoVendita_idPuntoVendita = '" + PuntoVenditaDAO.PUNTOVENDITA_DEFAULT_ID +
                "' WHERE `PuntoVendita_idPuntoVendita` = '" + idPuntoVendita + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }


}
