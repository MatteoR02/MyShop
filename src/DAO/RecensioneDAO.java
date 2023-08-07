package DAO;

import DbInterface.command.*;
import Model.Foto;
import Model.Recensione;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO implements IRecensioneDAO{

    private static final RecensioneDAO instance = new RecensioneDAO();
    private Recensione recensione;
    private static ResultSet rs;

    private static final IFotoDAO fotoDAO = FotoDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();

    private RecensioneDAO(){
        recensione = null;
        rs = null;
    }
    public static RecensioneDAO getInstance() {
        return instance;
    }

    @Override
    public boolean isRecensione(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.recensione WHERE idRecensione ='" + idRecensione + "';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readOp.close();
        }
        return false;
    }

    @Override
    public Recensione loadRecensione(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.recensione WHERE idRecensione = '" + idRecensione + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                recensione = new Recensione();
                recensione.setId(rs.getInt("idRecensione"));
                recensione.setTitolo(rs.getString("titolo"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setValutazione(Recensione.Punteggio.valueOf(rs.getString("valutazione")));
                recensione.setData(rs.getDate("data"));
                recensione.setImmagini(fotoDAO.loadAllFotoOfRecensione(rs.getInt("idRecensione")));
                recensione.setIdCliente(rs.getInt("Cliente_Utente_idUtente"));
                return recensione;
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
    public List<Recensione> loadAllRecensioni() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.recensione;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Recensione> recensioni = new ArrayList<>();

        try {
            while(rs.next()) {
                recensione = new Recensione();
                recensione.setId(rs.getInt("idRecensione"));
                recensione.setTitolo(rs.getString("titolo"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setValutazione(Recensione.Punteggio.valueOf(rs.getString("valutazione")));
                recensione.setData(rs.getDate("data"));
                recensione.setImmagini(fotoDAO.loadAllFotoOfRecensione(rs.getInt("idRecensione")));
                recensione.setIdCliente(rs.getInt("Cliente_Utente_idUtente"));
                recensioni.add(recensione);
            } return recensioni;
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
    public List<Recensione> loadRecensioniOfArticolo(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.recensione WHERE Articolo_idArticolo = '"+ idArticolo + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Recensione> recensioni = new ArrayList<>();

        try {
            while(rs.next()) {
                recensione = new Recensione();
                recensione.setId(rs.getInt("idRecensione"));
                recensione.setTitolo(rs.getString("titolo"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setValutazione(Recensione.Punteggio.valueOf(rs.getString("valutazione")));
                recensione.setData(rs.getDate("data"));
                recensione.setImmagini(fotoDAO.loadAllFotoOfRecensione(rs.getInt("idRecensione")));
                recensione.setIdCliente((rs.getInt("Cliente_Utente_idUtente")));
                recensioni.add(recensione);
            }
            return recensioni;
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
    public List<Recensione> loadRecensioniOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.recensione WHERE Cliente_Utente_idUtente = '"+ idCliente + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Recensione> recensioni = new ArrayList<>();

        try {
            while(rs.next()) {
                recensione = new Recensione();
                recensione.setId(rs.getInt("idRecensione"));
                recensione.setTitolo(rs.getString("titolo"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setValutazione(Recensione.Punteggio.valueOf(rs.getString("valutazione")));
                recensione.setData(rs.getDate("data"));
                recensione.setImmagini(fotoDAO.loadAllFotoOfRecensione(rs.getInt("idRecensione")));
                recensione.setIdCliente(rs.getInt("Cliente_Utente_idUtente"));
                recensioni.add(recensione);
            } return recensioni;
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
    public boolean isRecensioneDone(int idArticolo, int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) as count FROM myshop.recensione WHERE Cliente_Utente_idUtente='"+idCliente+"' AND Articolo_idArticolo='"+idArticolo+"';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count > 0;
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
    public int addRecensione(Recensione recensione, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.recensione (titolo, testo, valutazione, data, Cliente_Utente_idUtente, Articolo_idArticolo) VALUES (?,?,?,?,?,?);";

        IDbOperation addByteOp = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByteOp).getPreparedStatement();
        int rowCountRecensione = 0;
        int rowCountFotoRecensione = 0;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, recensione.getTitolo());
                preparedStatement.setString(2, recensione.getTesto());
                preparedStatement.setString(3, recensione.getValutazione().toString());
                preparedStatement.setDate(4, (Date) recensione.getData());
                preparedStatement.setInt(5, recensione.getIdCliente());
                preparedStatement.setInt(6, idArticolo);
                rowCountRecensione = preparedStatement.executeUpdate();
                preparedStatement.close();
                if (recensione.getImmagini()!= null){
                    for ( Foto foto : recensione.getImmagini()) {
                        rowCountFotoRecensione += fotoDAO.addFotoToRecensione(foto, recensione.getId());
                    }
                }
                return rowCountRecensione + rowCountFotoRecensione;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            addByteOp.close();
        }
        return 0;
    }

    @Override
    public int updateRecensione(Recensione recensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.recensione SET titolo = ?, testo = ?, valutazione = ?, data = ?, Cliente_Utente_idUtente = ? WHERE idRecensione = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setString(1, recensione.getTitolo());
                preparedStatement.setString(2, recensione.getTesto());
                preparedStatement.setString(3, recensione.getValutazione().toString());
                preparedStatement.setDate(4, (Date)recensione.getData());
                preparedStatement.setInt(5, recensione.getIdCliente());
                preparedStatement.setInt(6, recensione.getId());
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
    public int removeRecensione(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM recensione WHERE idRecensione = '" + idRecensione + "';";

        for (Foto item : loadRecensione(idRecensione).getImmagini()) {
            fotoDAO.removeFoto(item.getId());
        }

        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    @Override
    public int setFKClienteToDefault(int idCliente){
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.recensione SET Cliente_Utente_idUtente = '" + UtenteDAO.CLIENTE_DEFAULT_ID +
                "' WHERE `Cliente_Utente_idUtente` = '" + idCliente + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

}
