package DAO;

import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrenotazioneDAO implements IPrenotazioneDAO{

    private static final PrenotazioneDAO instance = new PrenotazioneDAO();
    private static ResultSet rs;
    private Prenotazione prenotazione;

    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    private PrenotazioneDAO() {
        rs = null;
        prenotazione = null;
    }

    public static PrenotazioneDAO getInstance() {
        return instance;
    }

    /**
     * Verifica che l'id fornito appartiene ad una prenotazione sul database
     * @param idPrenotazione
     * @return true se l'id si riferisce ad una prenotazione, false altrimenti
     */
    @Override
    public boolean isPrenotazione(int idPrenotazione) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.prenotazione WHERE idPrenotazione ='" + idPrenotazione + "';";

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
     * Carica una particolare prenotazione dal database
     * @param idPrenotazione
     * @return
     */
    @Override
    public Prenotazione loadPrenotazione(int idPrenotazione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.prenotazione WHERE idPrenotazione = '" + idPrenotazione + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                prenotazione = new Prenotazione();
                prenotazione.setId(rs.getInt("idPrenotazione"));
                prenotazione.setIdCliente(rs.getInt("Cliente_Utente_idUtente"));
                prenotazione.setIdPuntoVendita(rs.getInt("PuntoVendita_idPuntoVendita"));
                prenotazione.setDataPrenotazione(rs.getDate("data_prenotazione"));
                prenotazione.setDataArrivo(rs.getDate("data_arrivo"));
                prenotazione.setStatoPrenotazione(Prenotazione.StatoPrenotazione.valueOf(rs.getString("stato")));
            }
            prenotazione.setProdottiPrenotati(loadProdottiFromPrenotazione(idPrenotazione));
            return prenotazione;
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
     * Carica tutte le prenotazioni dal database
     * @return
     */
    @Override
    public ArrayList<Prenotazione> loadAllPrenotazioni() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.prenotazione;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        ArrayList<Integer> idPrenotazioni = new ArrayList<>();

        try {
            while (rs.next()) {
                idPrenotazioni.add(rs.getInt("idPrenotazione"));
            }
            for (int id: idPrenotazioni ) {
                prenotazioni.add(loadPrenotazione(id));
            }
            return prenotazioni;
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
     * Carica tutti i prodotti e le loro relative quantità di una particolare prenotazione dal database
     * @param idPrenotazione
     * @return una mappa di prodotti e le loro relative quantità
     */
    @Override
    public Map<IProdotto, Integer> loadProdottiFromPrenotazione(int idPrenotazione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.prenotazione_has_prodotto WHERE Prenotazione_idPrenotazione ='"+ idPrenotazione +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        HashMap<IProdotto, Integer> prodottiPrenotati = new HashMap<>();
        HashMap<Integer,Integer> idProdottiQuant = new HashMap<>();

        try {
            while(rs.next()) {
                idProdottiQuant.put(rs.getInt("Prodotto_Articolo_idArticolo"),rs.getInt("quantita"));
            }
            for (int id : idProdottiQuant.keySet()) {
                    prodottiPrenotati.put(articoloDAO.loadProdotto(id),idProdottiQuant.get(id));
                }
            return prodottiPrenotati;
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
     * Carica tutte le prenotazioni di un particolare cliente dal database
     * @param idCliente
     * @return
     */
    @Override
    public ArrayList<Prenotazione> loadPrenotazioniOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.prenotazione WHERE Cliente_Utente_idUtente = '"+ idCliente +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        ArrayList<Integer> idPrenotazioni = new ArrayList<>();

        try {
            while (rs.next()) {
                idPrenotazioni.add(rs.getInt("idPrenotazione"));
            }
            for (int id: idPrenotazioni ) {
                prenotazioni.add(loadPrenotazione(id));
            }
            return prenotazioni;
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
     * Carica tutte le prenotazioni di un particolare punto vendita dal database
     * @param idPV
     * @return
     */
    @Override
    public ArrayList<Prenotazione> loadPrenotazioniOfPV(int idPV) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.prenotazione WHERE PuntoVendita_idPuntoVendita = '"+ idPV +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        ArrayList<Integer> idPrenotazioni = new ArrayList<>();

        try {
            while (rs.next()) {
                idPrenotazioni.add(rs.getInt("idPrenotazione"));
            }
            for (int id: idPrenotazioni ) {
                prenotazioni.add(loadPrenotazione(id));
            }
            return prenotazioni;
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
     * Svuota una prenotazione dal database
     * @param idPrenotazione
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeProdottiFromPrenotazione(int idPrenotazione) {

        Prenotazione prenotazione = loadPrenotazione(idPrenotazione);

        DbOperationExecutor executor = new DbOperationExecutor();
        int rowCount = 0;

        for (IProdotto prod : prenotazione.getProdottiPrenotati().keySet()) {
            String sqlDeletePrenProd = "DELETE FROM myshop.prenotazione_has_prodotto WHERE `Prodotto_Articolo_idArticolo` = '" + prod.getId() + "';";
            IDbOperation removePrenProd = new WriteOperation(sqlDeletePrenProd);
            rowCount += executor.executeOperation(removePrenProd).getRowsAffected();
            removePrenProd.close();
        }
        return rowCount;
    }

    /**
     * Rimuove un particolare prodotto da una particolare prenotazione sul database
     * @param idPrenotazione
     * @param idProdotto
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeProdottoFromPrenotazione(int idPrenotazione, int idProdotto) {

        DbOperationExecutor executor = new DbOperationExecutor();
        int rowCount = 0;

            String sqlDeletePrenProd = "DELETE FROM myshop.prenotazione_has_prodotto WHERE Prenotazione_idPrenotazione = '" + idPrenotazione + "' AND Prodotto_Articolo_idArticolo = '" + idProdotto + "';";
            IDbOperation removePrenProd = new WriteOperation(sqlDeletePrenProd);
            rowCount += executor.executeOperation(removePrenProd).getRowsAffected();
            removePrenProd.close();

        return rowCount;
    }

    /**
     * Rimuove un particolare prodotto da tutte le prenotazioni in cui è presente sul database
     * @param idProdotto
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeProdottoFromAllPrenotazioni(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        int rowCount = 0;

        String sqlDeletePrenProd = "DELETE FROM myshop.prenotazione_has_prodotto WHERE Prodotto_Articolo_idArticolo = '" + idProdotto + "';";
        IDbOperation removePrenProd = new WriteOperation(sqlDeletePrenProd);
        rowCount += executor.executeOperation(removePrenProd).getRowsAffected();
        removePrenProd.close();

        return rowCount;
    }

    /**
     * Aggiunge una nuova prenotazione sul database
     * @param prenotazione
     * @return numero di righe modificate
     */
    @Override
    public int addPrenotazione(Prenotazione prenotazione) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlPrenotazione = "INSERT INTO myshop.prenotazione (Cliente_Utente_idUtente, PuntoVendita_idPuntoVendita, data_prenotazione, data_arrivo, stato) VALUES (?,?,?,?,?);";

        IDbOperation add = new WriteByteOperation(sqlPrenotazione);
        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGen = -1;
            try {
                if (statement!=null){
                    statement.setInt(1, prenotazione.getIdCliente());
                    statement.setInt(2, prenotazione.getIdPuntoVendita());
                    statement.setDate(3, prenotazione.getDataPrenotazione());
                    statement.setDate(4, prenotazione.getDataArrivo());
                    statement.setString(5, prenotazione.getStatoPrenotazione().toString());
                    rowCount = statement.executeUpdate(); //aggiungo prenotazione
                }
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

            for (IProdotto prodotto : prenotazione.getProdottiPrenotati().keySet()) {
                String sqlProdPren = "INSERT INTO myshop.prenotazione_has_prodotto (Prenotazione_idPrenotazione ,Prodotto_Articolo_idArticolo, quantita) VALUES " +
                        "('"+ idGen +"', '" + prodotto.getId() + "', '"+ prenotazione.getProdottiPrenotati().get(prodotto) +"');";
                add = new WriteOperation(sqlProdPren);
                rowCount += executor.executeOperation(add).getRowsAffected();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;
    }


    /**
     * Aggiorna una particolare prenotazione
     * @param prenotazione
     * @return numero di righe modificate sul database
     */
    @Override
    public int updatePrenotazione(Prenotazione prenotazione) {
        int rowCount = 0;
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlPrenotazione = "UPDATE myshop.prenotazione SET PuntoVendita_idPuntoVendita=?, data_arrivo=? ,stato=? WHERE idPrenotazione=?;";

        IDbOperation writeOpPren = new WriteByteOperation(sqlPrenotazione);

            try {
                PreparedStatement prenotazioneStmt = executor.executeOperation(writeOpPren).getPreparedStatement();
                prenotazioneStmt.setInt(1, prenotazione.getIdPuntoVendita());
                prenotazioneStmt.setDate(2, prenotazione.getDataArrivo());
                prenotazioneStmt.setString(3,prenotazione.getStatoPrenotazione().toString());
                prenotazioneStmt.setInt(4,prenotazione.getId());
                rowCount = prenotazioneStmt.executeUpdate() ;
                prenotazioneStmt.close();

                for (IProdotto prod : prenotazione.getProdottiPrenotati().keySet()) {
                    String sql = "UPDATE prenotazione_has_prodotto SET quantita = '"+ prenotazione.getProdottiPrenotati().get(prod) +"' WHERE (Prenotazione_idPrenotazione = "+ prenotazione.getId() +") AND (Prodotto_Articolo_idArticolo = "+ prod.getId() +");";
                    IDbOperation writeOp = new WriteOperation(sql);
                    rowCount+= executor.executeOperation(writeOp).getRowsAffected();
                    writeOp.close();
                }
                return rowCount;

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpPren.close();
            }
            return 0;
    }

    /**
     * Elimina una prenotazione dal database
     * @param idPrenotazione
     * @return numero di righe modificate sul database
     */
    @Override
    public int removePrenotazione(int idPrenotazione) {

        int rowCountProd = removeProdottiFromPrenotazione(idPrenotazione);

        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlDeletePren = "DELETE FROM myshop.prenotazione WHERE idPrenotazione = '" + idPrenotazione + "';";
        IDbOperation removePrenotazione = new WriteOperation(sqlDeletePren);
        int rowCountPrenotazione =  executor.executeOperation(removePrenotazione).getRowsAffected();
        removePrenotazione.close();

        return rowCountProd + rowCountPrenotazione;
    }

    /**
     * Verifica se la lista è arrivata
     * @param idPrenotazione
     * @return true se lo stato della lista è ARRIVATA, false altrimenti
     */
    @Override
    public boolean isArrivata(int idPrenotazione) {
        String sql = "SELECT stato FROM myshop.prenotazione WHERE idPrenotazione ='"+idPrenotazione+"';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                Prenotazione.StatoPrenotazione stato = Prenotazione.StatoPrenotazione.valueOf(rs.getString("stato"));
                return stato == Prenotazione.StatoPrenotazione.ARRIVATA;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }

    /**
     * Modifica lo stato di una prenotazione sul database
     * @param idPrenotazione
     * @param stato
     * @return numero di righe modificate sul database
     */
    @Override
    public int changePrenotazioneStatus(int idPrenotazione, Prenotazione.StatoPrenotazione stato) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlChangePrenotazioneStatus = "UPDATE myshop.prenotazione SET stato = '" + stato + "' WHERE idPrenotazione = '" + idPrenotazione + "';";
        IDbOperation changeStatus = new WriteOperation(sqlChangePrenotazioneStatus);
        int rowCount = executor.executeOperation(changeStatus).getRowsAffected();
        changeStatus.close();
        return rowCount;
    }

    /**
     * Imposta la chiave esterna riferita a prodotto al valore di defautl
     * @param idProdotto
     * @return numero di righe modificate sul database
     */
    @Override
    public int setFKProdottoToDefault(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.prenotazione_has_prodotto SET Prodotto_Articolo_idArticolo = '" + ArticoloDAO.ARTICOLO_DEFAULT_ID +
                "' WHERE `Prodotto_Articolo_idArticolo1` = '" + idProdotto + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }
}
