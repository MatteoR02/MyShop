package DAO;

import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticoloDAO implements IArticoloDAO {

    private static final ArticoloDAO instance = new ArticoloDAO();
    private Articolo articolo;
    private Prodotto prodotto;
    private Servizio servizio;
    private ProdottoComposito prodottoComposito;
    private static ResultSet rs;

    private static final IErogatoreDAO erogatoreDAO = ErogatoreDAO.getInstance();
    private static final ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    private static final IFotoDAO fotoDAO = FotoDAO.getInstance();
    private static final IRecensioneDAO recensioneDAO = RecensioneDAO.getInstance();
    private static final IMagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IPrenotazioneDAO prenotazioneDAO = PrenotazioneDAO.getInstance();

    public static int ARTICOLO_DEFAULT_ID = 1;

    private ArticoloDAO() {
        rs = null;
    }

    public static ArticoloDAO getInstance() {
        return instance;
    }

    /**
     *
     * @param idArticolo
     * @return true se l'articolo è un prodotto, false altrimenti
     */
    @Override
    public boolean isProdotto(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo WHERE A.idArticolo='" + idArticolo + "';";

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
     *
     * @param idArticolo
     * @return true se l'articolo è un servizio, false altrimenti
     */
    @Override
    public boolean isServizio(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.articolo as A INNER JOIN myshop.servizio as S on A.idArticolo = S.Articolo_idArticolo WHERE A.idArticolo='" + idArticolo + "';";

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
     *
     * @param idProdotto
     * @return restituisce il prodotto caricato da database
     */
    @Override
    public Prodotto loadProdotto(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo WHERE A.idArticolo='" + idProdotto + "';";
        //"INNER JOIN myshop.magazzino_has_prodotto AS MP on A.idArticolo = MP.Prodotto_Articolo_idArticolo"
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("idArticolo"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setPrezzo(rs.getFloat("prezzo"));
                prodotto.setErogatore(erogatoreDAO.loadErogatore(rs.getInt("Erogatore_idErogatore")));
                prodotto.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                prodotto.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                prodotto.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                prodotto.setCollocazione(magazzinoDAO.loadCollocazioneOfProdotto(rs.getInt("idArticolo")));
                //prodotto.setCollocazione(new Collocazione(rs.getInt("quantita"), rs.getString("corsia"), rs.getInt("scaffale"), magazzinoDAO.loadMagazzino(rs.getInt("Magazzino_idMagazzino"))));
                return prodotto;
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
     *
     * @return restituisce un arraylist di tutti i prodotti caricati da database
     */
    @Override
    public ArrayList<Articolo> loadAllProdotti() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Articolo> prodotti = new ArrayList<>();
        ArrayList<Integer> idProdotti = new ArrayList<>();

        try {
            while (rs.next()) {
                idProdotti.add(rs.getInt("idArticolo"));
            }
            for (int id : idProdotti) {
                if (isProdottoComposito(id)){
                    prodottoComposito = loadProdottoComposito(id);
                    prodotti.add(prodottoComposito);
                } else {
                    prodotto = loadProdotto(id);
                    prodotti.add(prodotto);
                }
            } return prodotti;

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
     *
     * @param idProdottoComposito
     * @return restituisce il prodotto composito caricato da database
     */
    @Override
    public ProdottoComposito loadProdottoComposito(int idProdottoComposito) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo  INNER JOIN myshop.prodotto_has_prodotto as PP on A.idArticolo = PP.Prodotto_Articolo_idArticolo WHERE A.idArticolo ='" + idProdottoComposito + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            prodottoComposito = new ProdottoComposito();
            ArrayList<Integer> idProdotti = new ArrayList<>();
            while (rs.next()) {
                prodottoComposito.setId(rs.getInt("idArticolo"));
                prodottoComposito.setNome(rs.getString("nome"));
                prodottoComposito.setPrezzo(rs.getFloat("prezzo"));
                prodottoComposito.setDescrizione(rs.getString("descrizione"));
                prodottoComposito.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                prodottoComposito.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                prodottoComposito.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                prodottoComposito.setErogatore(erogatoreDAO.loadErogatore(rs.getInt("Erogatore_idErogatore")));
                idProdotti.add(rs.getInt("Prodotto_Articolo_idArticolo1"));
            }
            for (int idProdotto : idProdotti) {
                prodottoComposito.addSottoProdotti(loadProdotto(idProdotto));
            }
            return prodottoComposito;

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
     *
     * @param idProdottoComposito
     * @return true se l'id corrisponde ad un prodotto composito, false altrimenti
     */
    @Override
    public boolean isProdottoComposito(int idProdottoComposito) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.articolo as A INNER JOIN myshop.prodotto_has_prodotto as PP on A.idArticolo = PP.Prodotto_Articolo_idArticolo WHERE A.idArticolo='" + idProdottoComposito + "';";

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
     *
     * @param idProdotto
     * @return true se l'id corrisponde ad un sottoprodotto, false altrimenti
     */
    @Override
    public boolean isSottoProdotto(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.articolo as A INNER JOIN myshop.prodotto_has_prodotto as PP on A.idArticolo = PP.Prodotto_Articolo_idArticolo1 WHERE A.idArticolo='" + idProdotto + "';";

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
     * TODO aggiungere in prodotto_has_prodotto gli id
     * Aggiunge il prodotto al database
     * @param prodotto
     * @return il numero di righe modificate sul database
     */
    @Override
    public int addProdotto(Articolo prodotto, boolean returnID) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlArticolo = "INSERT INTO myshop.articolo (nome, descrizione, prezzo, Categoria_idCategoria, Erogatore_idErogatore) VALUES (?,?,?,?,?);";

        IDbOperation add = new WriteByteOperation(sqlArticolo);

        int rowCount = 0;
        int idGenArt = -1;
        if (prodotto instanceof ProdottoComposito) {
            ProdottoComposito prodComp = (ProdottoComposito) prodotto;
            try {
                PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();
                if (statement != null) {
                    statement.setString(1, prodotto.getNome());
                    statement.setString(2, prodotto.getDescrizione());
                    statement.setFloat(3, prodotto.getPrezzo());
                    statement.setInt(4, prodotto.getCategoria().getId());
                    statement.setInt(5, prodotto.getErogatore().getId());
                    rowCount = statement.executeUpdate(); //aggiungo prenotazione
                }
                try (ResultSet generatedID = statement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGenArt = generatedID.getInt(1);
                    }
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                statement.close();

                String sqlProdotto = "INSERT INTO `myshop`.`prodotto` (`Articolo_idArticolo`) VALUES " +
                        "('" + idGenArt + "');";
                add = new WriteOperation(sqlProdotto);
                rowCount += executor.executeOperation(add).getRowsAffected();

                for (IProdotto sottoProdotto : prodComp.getSottoProdotti()) {
                    String sqlProdComp = "INSERT INTO `myshop`.`prodotto_has_prodotto` (`Prodotto_Articolo_idArticolo`, `Prodotto_Articolo_idArticolo1`) VALUES " +
                            "('" + idGenArt + "', '"+ sottoProdotto.getId()+"');";
                    IDbOperation prodCompOperation = new WriteByteOperation(sqlProdComp);
                    add = new WriteOperation(sqlProdComp);
                    rowCount += executor.executeOperation(add).getRowsAffected();
                }

                for (Foto foto: prodotto.getImmagini()   ) {
                    fotoDAO.addNewFotoToArticolo(foto,idGenArt);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                add.close();
            }

        } else if (prodotto instanceof Prodotto){
            Prodotto prod = (Prodotto) prodotto;
            try {
                PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

                if (statement != null) {
                    statement.setString(1, prodotto.getNome());
                    statement.setString(2, prodotto.getDescrizione());
                    statement.setFloat(3, prodotto.getPrezzo());
                    statement.setInt(4, prodotto.getCategoria().getId());
                    statement.setInt(5, prodotto.getErogatore().getId());
                    rowCount = statement.executeUpdate(); //aggiungo prenotazione
                }
                try (ResultSet generatedID = statement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGenArt = generatedID.getInt(1);
                    }
                statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                statement.close();

                String sqlProdotto = "INSERT INTO myshop.prodotto (Articolo_idArticolo)" +
                        "VALUES " +
                        "('" + idGenArt + "');";
                add = new WriteOperation(sqlProdotto);
                rowCount += executor.executeOperation(add).getRowsAffected();

                String sqlCollocazione = "INSERT INTO myshop.magazzino_has_prodotto (Magazzino_idMagazzino, Prodotto_Articolo_idArticolo, quantita, corsia, scaffale)" +
                        "VALUES " +
                        "('"+prod.getCollocazione().getMagazzino().getId() +"','" + idGenArt + "', '" + prod.getCollocazione().getQuantita() +"', '" + prod.getCollocazione().getCorsia() + "', '" + prod.getCollocazione().getScaffale() + "');";

                add = new WriteOperation(sqlCollocazione);
                rowCount += executor.executeOperation(add).getRowsAffected();

                for (Foto foto: prodotto.getImmagini()   ) {
                    fotoDAO.addNewFotoToArticolo(foto,idGenArt);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                add.close();
            }
            if (returnID){
                return idGenArt;
            } else {
                return rowCount;
            }
        }
        return 0;
    }

   /*
    @Override
    public int createComposition(List<Integer> idProdotti, String nomeComp, String descrizioneComp, int idCategoria){

        ProdottoComposito prodComp = new ProdottoComposito();
        for (int idProdotto : idProdotti) {
            prodComp.addSottoProdotti(loadProdotto(idProdotto));
        }

        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlArticolo = "INSERT INTO `myshop`.`articolo` (`nome`, `descrizione`, `prezzo`, `Categoria_idCategoria`) VALUES " +
                "('" + nomeComp + "', '"+ descrizioneComp +"', '" + prodComp.getPrezzo() + "', '"+ idCategoria+"');";

        IDbOperation add = new WriteByteOperation(sqlArticolo);
        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGenProdComp = -1;
            try {
                rowCount = statement.executeUpdate(); //aggiungo articolo
                try (ResultSet generatedID = statement.getGeneratedKeys()) {
                    if (generatedID.next()) {
                        idGenProdComp = generatedID.getInt(1);
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();

            String sqlProdotto = "INSERT INTO `myshop`.`prodotto` (`Articolo_idArticolo`) VALUES " +
                    "('" + idGenProdComp + "');";
            add = new WriteOperation(sqlProdotto);
            rowCount += executor.executeOperation(add).getRowsAffected();

            for (int idProdotto : idProdotti) {
                String sqlProdComp = "INSERT INTO `myshop`.`prodotto_has_prodotto` (`Prodotto_Articolo_idArticolo`, `Prodotto_Articolo_idArticolo1`) VALUES " +
                        "('" + idGenProdComp + "', '"+ idProdotto +"');";
                add = new WriteOperation(sqlProdComp);
                rowCount += executor.executeOperation(add).getRowsAffected();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;
    }*/

    /**
     * Aggiorna le informazioni del prodotto
     * @param prodotto
     * @return restituisce il numero di righe modificate sul database
     */
    @Override
    public int updateProdotto(Articolo prodotto) {
        int rowCount = 0;
        DbOperationExecutor executor = new DbOperationExecutor();

        if (!isProdottoComposito(prodotto.getId())){
            //Se è un prodotto normale le uniche cose da aggiornare sono in articolo e in collocazione
            String sqlArticolo = "UPDATE myshop.articolo SET nome=?, descrizione=?, prezzo=?, Categoria_idCategoria=?, Erogatore_idErogatore=? WHERE idArticolo=?;";
           // String sqlProdotto = "UPDATE myshop.prodotto SET Produttore_idProduttore=? WHERE Articolo_idArticolo=?;";
            String sqlCollocazione = "UPDATE myshop.magazzino_has_prodotto SET Magazzino_idMagazzino=?, quantita=?, corsia=?, scaffale=? WHERE Prodotto_Articolo_idArticolo=?;";

            IDbOperation writeOpArt = new WriteByteOperation(sqlArticolo);
            //IDbOperation writeOpProd = new WriteByteOperation(sqlProdotto);
            IDbOperation writeOpColl = new WriteByteOperation(sqlCollocazione);

            try {
                PreparedStatement articoloStmt = executor.executeOperation(writeOpArt).getPreparedStatement();
                articoloStmt.setString(1, prodotto.getNome());
                articoloStmt.setString(2, prodotto.getDescrizione());
                articoloStmt.setFloat(3, prodotto.getPrezzo());
                articoloStmt.setInt(4, prodotto.getCategoria().getId());
                articoloStmt.setInt(5, prodotto.getErogatore().getId());
                articoloStmt.setInt(6, prodotto.getId());
                rowCount = articoloStmt.executeUpdate() ;
                articoloStmt.close();

               /* PreparedStatement prodottoStmt = executor.executeOperation(writeOpProd).getPreparedStatement();
                prodottoStmt.setInt(1, ((Prodotto)prodotto).getErogatore().getId() );
                prodottoStmt.setInt(2, prodotto.getId());
                rowCount += prodottoStmt.executeUpdate() ;
                prodottoStmt.close();*/

                PreparedStatement collStmt = executor.executeOperation(writeOpColl).getPreparedStatement();
                collStmt.setInt(1, ((Prodotto) prodotto).getCollocazione().getMagazzino().getId());
                collStmt.setInt(2, ((Prodotto) prodotto).getCollocazione().getQuantita());
                collStmt.setString(3, ((Prodotto) prodotto).getCollocazione().getCorsia());
                collStmt.setString(4, ((Prodotto) prodotto).getCollocazione().getScaffale());
                collStmt.setInt(5, prodotto.getId());
                rowCount += collStmt.executeUpdate();
                collStmt.close();

                return rowCount;

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpArt.close();
                //writeOpProd.close();
                writeOpColl.close();
            }
            return 0;

        } else if (isProdottoComposito(prodotto.getId())){

            String sqlArticolo = "UPDATE articolo SET nome=? , descrizione=?, prezzo=?, Categoria_idCategoria=?, Erogatore_idErogatore=? WHERE idArticolo=?;";
           // String sqlProdotto = "UPDATE myshop.prodotto SET Produttore_idProduttore=? WHERE Articolo_idArticolo=?;";
            IDbOperation writeOpArt = new WriteByteOperation(sqlArticolo);
            //IDbOperation writeOpProd = new WriteByteOperation(sqlProdotto);

            try {
                PreparedStatement articoloStmt = executor.executeOperation(writeOpArt).getPreparedStatement();
                articoloStmt.setString(1, prodotto.getNome());
                articoloStmt.setString(2, prodotto.getDescrizione());
                articoloStmt.setFloat(3, prodotto.getPrezzo());
                articoloStmt.setInt(4, prodotto.getCategoria().getId());
                articoloStmt.setInt(5, prodotto.getErogatore().getId());
                articoloStmt.setInt(6, prodotto.getId());
                rowCount = articoloStmt.executeUpdate();
                articoloStmt.close();

               /* PreparedStatement prodottoStmt = executor.executeOperation(writeOpProd).getPreparedStatement();
                prodottoStmt.setInt(1, ((Prodotto)prodotto).getErogatore().getId() );
                prodottoStmt.setInt(2, prodotto.getId());
                rowCount += prodottoStmt.executeUpdate() ;
                prodottoStmt.close();*/

                //Modifico ricorsivamente anche gli eventuali sotto articoli. Se un sotto articolo viene rimosso va chiamato il remove non da qui
                for (IProdotto p: ((ProdottoComposito)prodotto).getSottoProdotti()) {
                    updateProdotto((Prodotto)p);
                }
                return rowCount;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpArt.close();
                //writeOpProd.close();
            }
            return rowCount;
        }
        return rowCount;
    }

    /**
     * Rimuove il prodotto dal database
     * @param idProdotto
     * @return restituisce il numero di righe modificate
     */
    @Override
    public int removeProdotto(int idProdotto) {

        fotoDAO.setFKArticoloHasFotoToDefault(idProdotto);
        prenotazioneDAO.removeProdottoFromAllPrenotazioni(idProdotto);
        listaAcquistoDAO.removeArticoloFromListe(idProdotto);


        for (Recensione rec : loadProdotto(idProdotto).getRecensioni()) {
            recensioneDAO.removeRecensione(rec.getId());
        }


        String sqlCollocazione = "DELETE FROM myshop.magazzino_has_prodotto WHERE Prodotto_Articolo_idArticolo='" + idProdotto + "';";
        String sqlProdotto = "DELETE FROM myshop.prodotto WHERE Articolo_idArticolo='" + idProdotto + "';";
        String sqlArticolo = "DELETE FROM myshop.articolo WHERE idArticolo='" + idProdotto + "';";
        String sqlPV = "DELETE FROM myshop.puntovendita_has_articolo WHERE Articolo_idArticolo='" + idProdotto +"';";


        int rowCount = 0;

        try {
            if (isProdottoComposito(idProdotto)){

                ProdottoComposito prod = loadProdottoComposito(idProdotto);

                for (IProdotto prodot: prod.getSottoProdotti()) {
                    removeSottoProdotto(idProdotto,prodot.getId());
                }

                IDbOperation prodottoRem = new WriteOperation(sqlProdotto);
                IDbOperation articoloRem = new WriteOperation(sqlArticolo);
                IDbOperation pvRem = new WriteOperation(sqlPV);

                DbOperationExecutor executor = new DbOperationExecutor();
                rowCount = executor.executeOperation(prodottoRem).getRowsAffected()
                        + executor.executeOperation(pvRem).getRowsAffected()
                        + executor.executeOperation(articoloRem).getRowsAffected();


            }  else if (!isProdottoComposito(idProdotto)) {

                IDbOperation collocazioneRem = new WriteOperation(sqlCollocazione);
                IDbOperation prodottoRem = new WriteOperation(sqlProdotto);
                IDbOperation articoloRem = new WriteOperation(sqlArticolo);
                IDbOperation pvRem = new WriteOperation(sqlPV);

                DbOperationExecutor executor = new DbOperationExecutor();

                rowCount = executor.executeOperation(collocazioneRem).getRowsAffected()
                        + executor.executeOperation(prodottoRem).getRowsAffected()
                        + executor.executeOperation(pvRem).getRowsAffected()
                        + executor.executeOperation(articoloRem).getRowsAffected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    @Override
    public int removeSottoProdotto(int idProdComp, int idSottoProd) {

        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlComposizione = "DELETE FROM myshop.prodotto_has_prodotto WHERE Prodotto_Articolo_idArticolo='"+idProdComp+"' AND Prodotto_Articolo_idArticolo1='" + idSottoProd + "';";
        IDbOperation composizioneRem = new WriteOperation(sqlComposizione);

        int rowCount = executor.executeOperation(composizioneRem).getRowsAffected();

        return rowCount;
    }

    @Override
    public int addSottoProdotto(int idProdComp, IProdotto prodotto) {
        return 0;
    }

    /**
     * Carica il servizio dal database
     * @param idServizio
     * @return restituisce il servizio caricato dal database
     */
    @Override
    public Servizio loadServizio(int idServizio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.servizio as S on A.idArticolo = S.Articolo_idArticolo WHERE A.idArticolo='" + idServizio + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                servizio = new Servizio();
                servizio.setId(rs.getInt("idArticolo"));
                servizio.setNome(rs.getString("nome"));
                servizio.setDescrizione(rs.getString("descrizione"));
                servizio.setPrezzo(rs.getFloat("prezzo"));
                servizio.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                servizio.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                servizio.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                servizio.setErogatore(erogatoreDAO.loadErogatore(rs.getInt("Erogatore_idErogatore")));
                return servizio;
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
     * Carica tutti i servizi dal database
     * @return restituisce un arraylist di tutti i servizi caricati dal database
     */
    @Override
    public ArrayList<Servizio> loadAllServizi() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.servizio as S on A.idArticolo = S.Articolo_idArticolo;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Servizio> servizi = new ArrayList<>();

        try {
            while (rs.next()) {
                servizio = new Servizio();
                servizio.setId(rs.getInt("idArticolo"));
                servizio.setNome(rs.getString("nome"));
                servizio.setDescrizione(rs.getString("descrizione"));
                servizio.setPrezzo(rs.getFloat("prezzo"));
                servizio.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                servizio.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                servizio.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                servizio.setErogatore(erogatoreDAO.loadErogatore(rs.getInt("Erogatore_idErogatore")));
                servizi.add(servizio);
            }
            return servizi;
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
     * Aggiunge un servizio al database
     * @param servizio
     * @return restituisce il numero di righe modificate
     */
    @Override
    public int addServizio(Servizio servizio, boolean returnID) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlArticolo = "INSERT INTO `myshop`.`articolo` (`nome`, `descrizione`, `prezzo`, `Categoria_idCategoria` , `Erogatore_idErogatore `) VALUES " +
                "('" + servizio.getNome() + "', '"+ servizio.getDescrizione() + "', '" + servizio.getPrezzo() + "', '"+ servizio.getCategoria().getId()+"', '" + servizio.getErogatore().getId() +"');";

        IDbOperation add = new WriteByteOperation(sqlArticolo);

        int rowCount = 0;
        int idGen = -1;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();


            try {
                rowCount = statement.executeUpdate(); //aggiungo articolo
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

            String sqlServizio = "INSERT INTO `myshop`.`servizio` (`Articolo_idArticolo`)" +
                    "VALUES " +
                    "('" + idGen + "');";
            add = new WriteOperation(sqlServizio);
            rowCount += executor.executeOperation(add).getRowsAffected();

            for (Foto foto: prodotto.getImmagini()   ) {
                fotoDAO.addNewFotoToArticolo(foto,idGen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        if (returnID){
            return idGen;
        } else {
            return rowCount;
        }
    }

    /**
     * Aggiorna le informazioni sul database del servizio
     * @param servizio
     * @return restituisce il numero di righe modificate
     */
    @Override
    public int updateServizio(Servizio servizio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlUpdateArticolo = "UPDATE `myshop`.`articolo` SET `nome` = '" + servizio.getNome() +
                "', `descrizione` = '" + servizio.getDescrizione() +
                "', `prezzo` = '" + servizio.getPrezzo() +
                "', `Categoria_idCategoria` = '" + servizio.getCategoria().getId() +
                "', `Erogatore_idErogatore` = '" + servizio.getErogatore().getId() +
                "' WHERE `idArticolo` = '" + servizio.getId() + "';";

        /*String sqlUpdateServizio = "UPDATE `myshop`.`cliente` SET `professione` = '" + cliente.getProfessione() +
                "', `canale_preferito` = '" + cliente.getCanalePreferito() +
                "', `data_abilitazione` = '" + cliente.getDataAbilitazione() +
                "', `stato` = '" + cliente.getStato() + "' WHERE `Utente_idUtente` = '" + cliente.getId() + "';";*/

        IDbOperation updateArticolo = new WriteOperation(sqlUpdateArticolo);
        int rowCountArticolo = executor.executeOperation(updateArticolo).getRowsAffected();
        updateArticolo.close();

        /*IDbOperation updateServizio = new WriteOperation(sqlUpdateServizio);
        int rowCountServizio = executor.executeOperation(updateServizio).getRowsAffected();
        updateServizio.close();*/

        return rowCountArticolo;
    }

    @Override
    public int removeServizio(int idServizio) {

        fotoDAO.setFKArticoloHasFotoToDefault(idServizio);
        listaAcquistoDAO.removeArticoloFromListe(idServizio);

        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlDeleteServFromPv = "DELETE FROM myshop.puntovendita_has_articolo WHERE Articolo_idArticolo = '" + idServizio + "';";
        IDbOperation removeServizioPV = new WriteOperation(sqlDeleteServFromPv);
        int rowCountPV = executor.executeOperation(removeServizioPV).getRowsAffected();
        removeServizioPV.close();

        String sqlDeleteServizio = "DELETE FROM `myshop`.`servizio` WHERE `Articolo_idArticolo` = '" + idServizio + "';";
        IDbOperation removeServizio = new WriteOperation(sqlDeleteServizio);
        int rowCountServizio = executor.executeOperation(removeServizio).getRowsAffected();
        removeServizio.close();

        for ( Recensione item : loadServizio(idServizio).getRecensioni()) {
            recensioneDAO.removeRecensione(item.getId());
        }

        String sqlDeleteArticolo = "DELETE FROM `myshop`.`articolo` WHERE `idArticolo` = '" + idServizio + "';";
        IDbOperation removeArticolo = new WriteOperation(sqlDeleteArticolo);
        int rowCountArticolo =  executor.executeOperation(removeArticolo).getRowsAffected();
        removeArticolo.close();

        return rowCountPV +rowCountServizio + rowCountArticolo;
    }

    /**
     * Carica tutti gli articoli di uno specifico punto vendita dal database
     * @param idPuntoVendita
     * @return restituisce un arraylist di tutti gli articoli di uno specifico punto vendita caricati dal database
     */
    @Override
    public ArrayList<Articolo> loadAllArticoliFromPuntoVendita(int idPuntoVendita) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT A.idArticolo FROM myshop.articolo as A INNER JOIN myshop.puntovendita_has_articolo as PV on A.idArticolo = PV.Articolo_idArticolo WHERE PV.PuntoVendita_idPuntoVendita = '"+idPuntoVendita+"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Integer> idArticoli = new ArrayList<>();
        ArrayList<Articolo> articoliOfPV = new ArrayList<>();

        try {
            while (rs.next()) {
                idArticoli.add(rs.getInt("idArticolo"));
            }
            for (int id  : idArticoli ) {
                if (isProdotto(id)) {
                    articoliOfPV.add(loadProdotto(id));
                } else if (isProdottoComposito(id)){
                    articoliOfPV.add(loadProdottoComposito(id));
                } else if (isServizio(id)){
                    articoliOfPV.add(loadServizio(id));
                }
            } return articoliOfPV;
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
     * Carica tutti gli articoli di una specifica lista d'acquisto dal database
     * @param idLista
     * @return restituisce un hashmap di articoli e le loro relative quantità di una specifica lista d'acquisto
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
                if (isProdotto(id)){
                    articoliInLista.put(loadProdotto(id),idArticoliQuant.get(id));
                } else if (isProdottoComposito(id)){
                    articoliInLista.put(loadProdottoComposito(id),idArticoliQuant.get(id));
                } else if (isServizio(id)) {
                    articoliInLista.put(loadServizio(id), idArticoliQuant.get(id));
                }
            }
            return articoliInLista;
        }
        catch (SQLException e) {
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
     * Imposta la chiave esterna riferente a "categoria" nella tabella "articolo" al valore preimpostato di default
     * @param idCategoria
     * @return restituisce il numero di righe modificate
     */
    @Override
    public int setFKCategoriaToDefault(int idCategoria){
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.articolo SET Categoria_idCategoria = '" + CategoriaDAO.CATEGORIA_DEFAULT_ID +
                "' WHERE `Categoria_idCategoria` = '" + idCategoria + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

    /**
     * Imposta la chiave esterna riferente a "magazzino" nella tabella "magazzino_has_prodotto" al valore preimpostato di default
     * @param idMagazzino
     * @return restituisce il numero di righe modificate
     */
    @Override
    public int setFKMagazzinoToDefault(int idMagazzino){
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.magazzino_has_prodotto SET Magazzino_idMagazzino = '" + MagazzinoDAO.MAGAZZINO_DEFAULT_ID +
                "' WHERE `Magazzino_idMagazzino` = '" + idMagazzino + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

    /**
     * Imposta la chiave esterna riferente a "erogatore" nella tabella "articolo" al valore preimpostato di default
     * @param idErogatore
     * @return
     */
    @Override
    public int setFKErogatoreToDefault(int idErogatore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.articolo SET Erogatore_idErogatore = '" + ErogatoreDAO.PRODUTTORE_DEFAULT_ID +
                "' WHERE `Erogatore_idErogatore` = '" + idErogatore + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }
}
