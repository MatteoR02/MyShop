package DAO;

import DbInterface.command.DbOperationExecutor;
import DbInterface.command.IDbOperation;
import DbInterface.command.ReadOperation;
import DbInterface.command.WriteOperation;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ListaAcquistoDAO implements IListaAcquistoDAO {

    private static final ListaAcquistoDAO instance = new ListaAcquistoDAO();
    private ListaAcquisto listaAcquisto;
    private static ResultSet rs;


    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static final IFotoDAO fotoDAO = FotoDAO.getInstance();


    private ListaAcquistoDAO() {
        listaAcquisto = null;
        rs = null;
    }

    public static ListaAcquistoDAO getInstance() {
        return instance;
    }

    /**
     *
     * @param idLista
     * @return true se l'id fornito si riferisce ad una lista d'acquisto, false altrimenti
     */
    @Override
    public boolean isListaAcquisto(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.listaacquisto WHERE idListaAcquisto='" + idLista + "';";

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
     * Carica una lista d'acquisto dal database
     * @param idLista da caricare
     * @return lista d'acquisto caricata
     */
    @Override
    public ListaAcquisto loadListaAcquisto(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto WHERE idListaAcquisto = '" + idLista + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                listaAcquisto = new ListaAcquisto();
                listaAcquisto.setId(rs.getInt("idListaAcquisto"));
                listaAcquisto.setNome(rs.getString("nome"));
                listaAcquisto.setStatoPagamento(ListaAcquisto.StatoPagamentoType.valueOf(rs.getString("stato_pagamento")));
                listaAcquisto.setDataPagamento(rs.getTimestamp("data_pagamento"));
                listaAcquisto.setArticoli(getArticoliFromLista(idLista));
                return listaAcquisto;
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
     * Carica tutte le liste d'acquisto dal database
     * @return arraylist di tutte le liste d'acquisto
     */
    @Override
    public ArrayList<ListaAcquisto> loadAllListaAcquisto() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<ListaAcquisto> listeAcquisto = new ArrayList<>();
        ArrayList<Integer> idListe = new ArrayList<>();
        try {
            while(rs.next()) {
                idListe.add(rs.getInt("idListaAcquisto"));
            }
            for (int id : idListe   ) {
                listeAcquisto.add(loadListaAcquisto(id));
            }
            return listeAcquisto;
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
     * Aggiunge una lista d'acquisto vuota al database
     * @param nome della nuova lista d'acquisto
     * @param idCliente a cui appartiene la lista d'acquisto
     * @return numero di righe modificate sul database
     */
    @Override
    public int addListaAcquisto(String nome, int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO `myshop`.`listaacquisto` (`nome`, `Cliente_Utente_idUtente`) VALUES " +
                "('" + nome + "', '" +  idCliente + "');";
        IDbOperation add = new WriteOperation(sql);
        int rowCount = executor.executeOperation(add).getRowsAffected();
        add.close();
        return rowCount;
    }

    /**
     * Aggiorna una particolare lista d'acquisto sul database
     * @param listaAcquisto aggiornata
     * @return numero di righe modificate sul database
     */
    @Override
    public int updateListaAcquisto(ListaAcquisto listaAcquisto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlLista = "UPDATE `myshop`.`listaacquisto` SET `nome` = '" + listaAcquisto.getNome() +
                "', stato_pagamento ='" + listaAcquisto.getStatoPagamento() + "'"+
                " WHERE `idListaAcquisto` = '" + listaAcquisto.getId() +"';";

        IDbOperation update = new WriteOperation(sqlLista);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();

        for ( Articolo item : listaAcquisto.getArticoli().keySet() ) {
            String sql;
            if (isInLista(listaAcquisto.getId(), item.getId())){
                sql = "UPDATE listaacquisto_has_articolo SET Articolo_idArticolo = '"+ item.getId() +"', quantita = '"+ listaAcquisto.getArticoli().get(item) +"' WHERE (ListaAcquisto_idListaAcquisto = "+ listaAcquisto.getId() +") AND (Articolo_idArticolo = "+ item.getId() +");";
            } else {
                sql = "INSERT INTO listaacquisto_has_articolo (ListaAcquisto_idListaAcquisto, Articolo_idArticolo, quantita) VALUES ('"+listaAcquisto.getId()+"','"+item.getId()+"','"+listaAcquisto.getArticoli().get(item)+"');";
            }
            IDbOperation write = new WriteOperation(sql);
            rowCount += executor.executeOperation(write).getRowsAffected();
            write.close();
        }
        return rowCount;
    }

    /**
     * Elimina una particolare lista d'acquisto dal database
     * @param idLista della lista da eliminare
     * @return numero di righe modificate sul database
     */
    @Override
    public int removeListaAcquisto(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlListaArt = "DELETE FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto='"+idLista+"';";
        IDbOperation removeListaArt = new WriteOperation(sqlListaArt);
        int rowCountListaArt = executor.executeOperation(removeListaArt).getRowsAffected();
        removeListaArt.close();

        String sqlLista = "DELETE FROM `myshop`.`listaacquisto` WHERE `idListaAcquisto` = '" + idLista + "';";

        IDbOperation removeLista = new WriteOperation(sqlLista);

        int rowCount = executor.executeOperation(removeLista).getRowsAffected();
        removeLista.close();
        return rowCount;
    }

    /**
     * Carica gli articoli e le loro relativa quantità di una particolare lista dal database
     * @param idLista della lista d'acquisto
     * @return un hashmap di articoli con le loro relative quantità
     */
    @Override
    public HashMap<Articolo, Integer> getArticoliFromLista(int idLista) {

        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto ='"+ idLista +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        HashMap<Articolo, Integer> articoliInLista = new HashMap<>();
        HashMap<Integer,Integer> idArticoliQuant = new HashMap<>();

        try {
            while(rs.next()) {
                idArticoliQuant.put(rs.getInt("Articolo_idArticolo"),rs.getInt("quantita"));
            }
            for (int id : idArticoliQuant.keySet()) {
                if (articoloDAO.isProdottoComposito(id)){
                    articoliInLista.put(articoloDAO.loadProdottoComposito(id),idArticoliQuant.get(id));
                } else if (articoloDAO.isProdotto(id)){
                    articoliInLista.put(articoloDAO.loadProdotto(id),idArticoliQuant.get(id));
                } else if (articoloDAO.isServizio(id)) {
                    articoliInLista.put(articoloDAO.loadServizio(id), idArticoliQuant.get(id));
                }
            }
            return articoliInLista;
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
     * Carica tutte le liste di un particolare cliente dal database
     * @param idCliente del cliente
     * @return un arraylist delle liste del cliente
     */
    @Override
    public ArrayList<ListaAcquisto> getListeOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto WHERE Cliente_Utente_idUtente = '" + idCliente + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<ListaAcquisto> listeAcquisto = new ArrayList<>();
        ArrayList<Integer> idListe = new ArrayList<>();
        try {
            while(rs.next()) {
                idListe.add(rs.getInt("idListaAcquisto"));
            }
            for (int id : idListe ) {
                listeAcquisto.add(loadListaAcquisto(id));
            }
            return listeAcquisto;

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
     * Controlla se la lista d'acquisto è pagata
     * @param idLista della lista da controllare
     * @return true se la lista d'acquisto è pagata, false altrimenti
     */
    @Override
    public boolean isPagata(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT stato_pagamento FROM myshop.listaacquisto WHERE idListaAcquisto='" + idLista +"';";

        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                return ListaAcquisto.StatoPagamentoType.valueOf(rs.getString("stato_pagamento")) == ListaAcquisto.StatoPagamentoType.PAGATO;
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
     * Aggiunge un particolare articolo con relativa quantità ad una particolare lista d'acquisto sul database
     * @param idLista della lista d'acquisto
     * @param idArticolo dell'articolo da aggiungere
     * @param quantita dell'articolo
     * @return numero di righe modificate
     */
    @Override
    public int insertArticoloInLista(int idLista, int idArticolo, int quantita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO `myshop`.`listaacquisto_has_articolo` (`ListaAcquisto_idListaAcquisto`, `Articolo_idArticolo`, `quantita`) VALUES " +
                "('" + idLista + "', '" +  idArticolo + "', '"+ quantita +"');";
        IDbOperation add = new WriteOperation(sql);
        int rowCount = executor.executeOperation(add).getRowsAffected();
        add.close();
        return rowCount;
    }

    @Override
    public int updateArticoloInLista(int idLista, int idArticolo, int quantita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE `myshop`.`listaacquisto_has_articolo` SET `quantita` = '" + quantita +
                "' WHERE `ListaAcquisto_idListaAcquisto` = '" + idLista + "' AND `Articolo_idArticolo` = '"+idArticolo+"';";

        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

    @Override
    public int removeArticoloInLista(int idLista, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto = '" + idLista + "' AND Articolo_idArticolo ='"+ idArticolo + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    /**
     * Svuota la lista d'acquisto sul database
     * @param idLista della lista d'acquisto
     * @return numero di righe modificate
     */
    @Override
    public int removeAllArticoliInLista(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto = '" + idLista + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    /**
     * Rimuove un particolare articolo da tutte le liste su cui è presente sul database
     * @param idArticolo da rimuovere dalle liste
     * @return numero di righe modificate
     */
    @Override
    public int removeArticoloFromListe(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.listaacquisto_has_articolo WHERE Articolo_idArticolo = '" + idArticolo + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    /**
     * Verifica se un particolare articolo è in una particolare lista d'acquisto sul database
     * @param idLista della lista d'acquisto
     * @param idArticolo da controllare
     * @return true se quell'articolo è in quella lista, false altrimenti
     */
    @Override
    public boolean isInLista(int idLista, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto='" + idLista +"' AND Articolo_idArticolo='"+idArticolo+"';";

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


}
