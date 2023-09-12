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

    public static final int RECENSIONE_DEFAULT_ID = 0;

    private RecensioneDAO(){
        recensione = null;
        rs = null;
    }
    public static RecensioneDAO getInstance() {
        return instance;
    }

    /**
     * Verifica se l'id fornito appartiene ad una recensione sul database
     * @param idRecensione
     * @return true se l'id fornito appartiene ad una recensione, false altrimenti
     */
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

    /**
     * Carica una particolare recensione dal database
     * @param idRecensione
     * @return
     */
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
                recensione.setIdRecensione(rs.getInt("Recensione_idRecensione"));
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

    /**
     * Carica la risposta del manager di una particolare recensione
     * @param idRecensione
     * @return
     */
    @Override
    public Recensione loadRisposta(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT RR.* FROM myshop.recensione AS R INNER JOIN myshop.recensione AS RR ON RR.Recensione_idRecensione = R.idRecensione WHERE R.idRecensione = '"+idRecensione+"';";
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
                recensione.setIdRecensione(rs.getInt("Recensione_idRecensione"));
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

    /**
     * Carica tutte le recensioni dal database
     * @return
     */
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
                recensione.setIdRecensione(rs.getInt("Recensione_idRecensione"));
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

    /**
     * Carica tutte le recensioni di un particolare articolo dal database
     * @param idArticolo
     * @return
     */
    @Override
    public List<Recensione> loadRecensioniOfArticolo(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.recensione WHERE Articolo_idArticolo = '"+ idArticolo + "' AND Recensione_idRecensione = 0;";
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
                recensione.setIdRecensione(rs.getInt("Recensione_idRecensione"));
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

    /**
     * Carica tutte le recensioni di un particolare cliente dal database
     * @param idCliente
     * @return
     */
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
                recensione.setIdRecensione(rs.getInt("Recensione_idRecensione"));
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

    /**
     * Verifica se un particolare cliente ha effettuato una recensione ad un particolare articolo sul database
     * @param idArticolo
     * @param idCliente
     * @return true se quel cliente ha già effettuato una recensione a quell'articolo, false altrimenti
     */
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

    /**
     * Verifica se un particolare manager ha già risposto ad una particolare recensione sul database
     * @param idRecensione
     * @param idManager
     * @return true se quel manager ha già risposto alla recensione, false altrimenti
     */
    @Override
    public boolean isRispostaDone(int idRecensione, int idManager) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) as count FROM myshop.recensione WHERE Cliente_Utente_idUtente='"+idManager+"' AND Recensione_idRecensione='"+idRecensione+"';";

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

    /**
     * Aggiunge una nuova recensione al database ad un particolare articolo
     * @param recensione
     * @param idArticolo
     * @return numero di righe modificate sul database
     */
    @Override
    public int addRecensione(Recensione recensione, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.recensione (titolo, testo, valutazione, data, Cliente_Utente_idUtente, Articolo_idArticolo, Recensione_idRecensione) VALUES (?,?,?,?,?,?,?);";

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
                preparedStatement.setInt(7, recensione.getIdRecensione());
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

    /**
     * Aggiorna una recensione sul database
     * @param recensione
     * @return numero di righe modificate sul database
     */
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

    /**
     * Elimina una recensione dal database
     * @param idRecensione
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeRecensione(int idRecensione) {

        removeRisposta(idRecensione);

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

    /**
     * Aggiunge una risposta ad una particolare recensione
     * @param recensione
     * @param idRecensione
     * @return numero di righe modificate sul database
     */
    @Override
    public int addRisposta(Recensione recensione, int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.recensione (titolo, testo, valutazione, data, Cliente_Utente_idUtente, Recensione_idRecensione) VALUES (?,?,?,?,?,?);";

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
                preparedStatement.setInt(6, idRecensione);
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

    /**
     * Imposta la chiave esterna riferita a idCliente ad un valore di default
     * @param idCliente
     * @return numero di righe modificate sul database
     */
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

    /**
     * Rimuove la risposta di una particolare recensione
     * @param idRecensione
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeRisposta(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.recensione WHERE Recensione_idRecensione = '"+idRecensione+"';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

}
