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


    @Override
    public int updatePrenotazione(Prenotazione prenotazione) {
        int rowCount = 0;
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlPrenotazione = "UPDATE myshop.prenotazione SET PuntoVendita_idPuntoVendita=?, data_arrivo=? ,stato=?, WHERE idPrenotazione=?;";

        IDbOperation writeOpPren = new WriteByteOperation(sqlPrenotazione);

            try {
                PreparedStatement prenotazioneStmt = executor.executeOperation(writeOpPren).getPreparedStatement();
                prenotazioneStmt.setInt(1, prenotazione.getIdPuntoVendita());
                prenotazioneStmt.setDate(2, prenotazione.getDataArrivo());
                prenotazioneStmt.setString(3,prenotazione.getStatoPrenotazione().toString());
                prenotazioneStmt.setInt(4,prenotazione.getId());
                rowCount = prenotazioneStmt.executeUpdate() ;
                prenotazioneStmt.close();
                return rowCount;

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpPren.close();
            }
            return 0;
    }

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
}