package DAO;

import DbInterface.IDbConnection;
import DbInterface.command.*;
import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArticoloDAO implements IArticoloDAO {

    private static ArticoloDAO instance = new ArticoloDAO();
    private Articolo articolo;
    private Prodotto prodotto;
    private Servizio servizio;
    private ProdottoComposito prodottoComposito;
    private static ResultSet rs;

    private final IProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    private final ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    private final IFotoDAO fotoDAO = FotoDAO.getInstance();
    private final IRecensioneDAO recensioneDAO = RecensioneDAO.getInstance();
    private final MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    private final ListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();

    public static int ARTICOLO_DEFAULT_ID = 1;

    private ArticoloDAO() {
        articolo = null;
        rs = null;
    }

    public static ArticoloDAO getInstance() {
        return instance;
    }

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

    @Override
    public Prodotto loadProdotto(int idProdotto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo INNER JOIN myshop.magazzino_has_prodotto AS MP on A.idArticolo = MP.Prodotto_Articolo_idArticolo WHERE A.idArticolo='" + idProdotto + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("idArticolo"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getFloat("prezzo"));
                prodotto.setProduttore(produttoreDAO.loadProduttore(rs.getInt("Produttore_idProduttore")));
                prodotto.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                prodotto.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                prodotto.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                prodotto.setCollocazione(new Collocazione(rs.getInt("quantita"), rs.getString("corsia"), rs.getInt("scaffale"), magazzinoDAO.loadMagazzino(rs.getInt("Magazzino_idMagazzino"))));
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

    @Override
    public ArrayList<Prodotto> loadAllProdotti() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo as A INNER JOIN myshop.prodotto as P on A.idArticolo = P.Articolo_idArticolo INNER JOIN myshop.magazzino_has_prodotto AS MP on A.idArticolo = MP.Prodotto_Articolo_idArticolo;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try {
            while (rs.next()) {
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("idArticolo"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getFloat("prezzo"));
                prodotto.setProduttore(produttoreDAO.loadProduttore(rs.getInt("Produttore_idProduttore")));
                prodotto.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                prodotto.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                prodotto.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
                prodotto.setCollocazione(new Collocazione(rs.getInt("quantita"), rs.getString("corsia"), rs.getInt("scaffale"), magazzinoDAO.loadMagazzino(rs.getInt("Magazzino_idMagazzino"))));
                prodotti.add(prodotto);
            }
            return prodotti;
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
                prodottoComposito.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                prodottoComposito.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                prodottoComposito.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
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
     * @param prodotto
     * @return
     */
    @Override
    public int addProdotto(Articolo prodotto) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlArticolo = "INSERT INTO `myshop`.`articolo` (`nome`, `prezzo`, `Categoria_idCategoria`) VALUES " +
                "('" + prodotto.getNome() + "', '" + prodotto.getPrezzo() + "', '"+ prodotto.getCategoria().getId()+"');";

        IDbOperation add = new WriteByteOperation(sqlArticolo);

        int rowCount = 0;
        if (prodotto instanceof ProdottoComposito) {
            ProdottoComposito prodComp = (ProdottoComposito) prodotto;
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

                for (IProdotto sottoProdotto : prodComp.getSottoProdotti()) {
                    int rowCheck = addProdotto((Prodotto)sottoProdotto);
                    ResultSet generatedID = statement.getGeneratedKeys();
                    int idGenProd = generatedID.getInt(1);
                    String sqlProdComp = "INSERT INTO `myshop`.`prodotto_has_prodotto` (`Prodotto_Articolo_idArticolo`, `Prodotto_Articolo_idArticolo1`) VALUES " +
                            "('" + idGenProdComp + "', '"+ idGenProd+"');";
                    add = new WriteOperation(sqlProdComp);
                    rowCount += executor.executeOperation(add).getRowsAffected();
                    if (rowCheck <= 0) {
                        return 0;
                    }
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

                int idGen = -1;
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

                String sqlProdotto = "INSERT INTO myshop.prodotto (Articolo_idArticolo, Produttore_idProduttore)" +
                        "VALUES " +
                        "('" + idGen + "', '" + prod.getProduttore().getId() +"');";
                add = new WriteOperation(sqlProdotto);
                rowCount += executor.executeOperation(add).getRowsAffected();

                String sqlCollocazione = "INSERT INTO myshop.magazzino_has_prodotto (Magazzino_idMagazzino, Prodotto_Articolo_idArticolo, quantita, corsia, scaffale)" +
                        "VALUES " +
                        "('"+prod.getCollocazione().getMagazzino().getId() +"','" + idGen + "', '" + prod.getCollocazione().getQuantita() +"', '" + prod.getCollocazione().getCorsia() + "', '" + prod.getCollocazione().getScaffale() + "');";
                add = new WriteOperation(sqlCollocazione);
                rowCount += executor.executeOperation(add).getRowsAffected();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                add.close();
            }
            return rowCount;
        }
        return 0;
    }

    @Override
    public int createComposition(List<Integer> idProdotti, String nomeComp, int idCategoria){
        DbOperationExecutor executor = new DbOperationExecutor();

        ProdottoComposito prodComp = new ProdottoComposito();
        for (int idProdotto : idProdotti) {
            prodComp.addSottoProdotti(loadProdotto(idProdotto));
        }

        String sqlArticolo = "INSERT INTO `myshop`.`articolo` (`nome`, `prezzo`, `Categoria_idCategoria`) VALUES " +
                "('" + nomeComp + "', '" + prodComp.getPrezzo() + "', '"+ idCategoria+"');";

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
    }

    @Override
    public int updateProdotto(Articolo prodotto) {
        int rowCount = 0;
        DbOperationExecutor executor = new DbOperationExecutor();

        if (!isProdottoComposito(prodotto.getId())){
            //Se è un prodotto normale le uniche cose da aggiornare sono in articolo e in collocazione
            String sqlArticolo = "UPDATE myshop.articolo SET nome=? , prezzo=?, Categoria_idCategoria=? WHERE idArticolo=?;";
            String sqlProdotto = "UPDATE myshop.prodotto SET Produttore_idProduttore=? WHERE Articolo_idArticolo=?;";
            String sqlCollocazione = "UPDATE myshop.magazzino_has_prodotto SET Magazzino_idMagazzino=?, quantita=?, corsia=?, scaffale=? WHERE Prodotto_Articolo_idArticolo=?;";

            IDbOperation writeOpArt = new WriteByteOperation(sqlArticolo);
            IDbOperation writeOpProd = new WriteByteOperation(sqlProdotto);
            IDbOperation writeOpColl = new WriteByteOperation(sqlCollocazione);

            try {
                PreparedStatement articoloStmt = executor.executeOperation(writeOpArt).getPreparedStatement();
                articoloStmt.setString(1, prodotto.getNome());
                articoloStmt.setFloat(2, prodotto.getPrezzo());
                articoloStmt.setInt(3, prodotto.getCategoria().getId());
                articoloStmt.setInt(4, prodotto.getId());
                rowCount = articoloStmt.executeUpdate() ;
                articoloStmt.close();

                PreparedStatement prodottoStmt = executor.executeOperation(writeOpProd).getPreparedStatement();
                prodottoStmt.setInt(1, ((Prodotto)prodotto).getProduttore().getId() );
                prodottoStmt.setInt(2, prodotto.getId());
                rowCount += prodottoStmt.executeUpdate() ;
                prodottoStmt.close();

                PreparedStatement collStmt = executor.executeOperation(writeOpColl).getPreparedStatement();
                collStmt.setInt(1, ((Prodotto) prodotto).getCollocazione().getQuantita());
                collStmt.setString(2, ((Prodotto) prodotto).getCollocazione().getCorsia());
                collStmt.setInt(3, ((Prodotto) prodotto).getCollocazione().getScaffale());
                collStmt.setInt(4, ((Prodotto) prodotto).getCollocazione().getMagazzino().getId());
                collStmt.setInt(5, prodotto.getId());
                rowCount += collStmt.executeUpdate();
                collStmt.close();

                return rowCount;

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpArt.close();
                writeOpProd.close();
                writeOpColl.close();
            }
            return 0;

        } else if (isProdottoComposito(prodotto.getId())){

            String sqlArticolo = "UPDATE articolo SET nome=? , prezzo=?, Categoria_idCategoria=? WHERE idArticolo=?;";
            String sqlProdotto = "UPDATE myshop.prodotto SET Produttore_idProduttore=? WHERE Articolo_idArticolo=?;";
            IDbOperation writeOpArt = new WriteByteOperation(sqlArticolo);
            IDbOperation writeOpProd = new WriteByteOperation(sqlProdotto);

            try {
                PreparedStatement articoloStmt = executor.executeOperation(writeOpArt).getPreparedStatement();
                articoloStmt.setString(1, prodotto.getNome());
                articoloStmt.setFloat(2, prodotto.getPrezzo());
                articoloStmt.setInt(3, prodotto.getCategoria().getId());
                articoloStmt.setInt(4, prodotto.getId());
                rowCount = articoloStmt.executeUpdate();
                articoloStmt.close();

                PreparedStatement prodottoStmt = executor.executeOperation(writeOpProd).getPreparedStatement();
                prodottoStmt.setInt(1, ((Prodotto)prodotto).getProduttore().getId() );
                prodottoStmt.setInt(2, prodotto.getId());
                rowCount += prodottoStmt.executeUpdate() ;
                prodottoStmt.close();

                //Modifico ricorsivamente anche gli eventuali sotto articoli. Se un sotto articolo viene rimosso va chiamato il remove non da qui
                for (IProdotto p: ((ProdottoComposito)prodotto).getSottoProdotti()) {
                    updateProdotto((Prodotto)p);
                }
                return rowCount;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                writeOpArt.close();
                writeOpProd.close();
            }
            return rowCount;
        }
        return rowCount;
    }

    @Override
    public int removeProdotto(int idProdotto) {

        fotoDAO.setFKArticoloHasFotoToDefault(idProdotto);
        listaAcquistoDAO.removeArticoloFromListe(idProdotto);

        for (Recensione rec : loadProdotto(idProdotto).getRecensioni()) {
            recensioneDAO.removeRecensione(rec.getId());
        }


        String sqlCollocazione = "DELETE FROM myshop.magazzino_has_prodotto WHERE Prodotto_Articolo_idArticolo='" + idProdotto + "';";
        String sqlProdotto = "DELETE FROM myshop.prodotto WHERE Articolo_idArticolo='" + idProdotto + "';";
        String sqlArticolo = "DELETE FROM myshop.articolo WHERE idArticolo='" + idProdotto + "';";

        IDbOperation collocazioneRem = new WriteOperation(sqlCollocazione);
        IDbOperation prodottoRem = new WriteOperation(sqlProdotto);
        IDbOperation articoloRem = new WriteOperation(sqlArticolo);

        DbOperationExecutor executor = new DbOperationExecutor();
        int rowCount = 0;

        if (!isProdottoComposito(idProdotto) && !isSottoProdotto(idProdotto)){

            rowCount = executor.executeOperation(collocazioneRem).getRowsAffected()
                    + executor.executeOperation(prodottoRem).getRowsAffected()
                    + executor.executeOperation(articoloRem).getRowsAffected();

        } else if(isSottoProdotto(idProdotto)){

            String sqlComposizione = "DELETE FROM myshop.prodotto_has_prodotto WHERE Prodotto_Articolo_idArticolo1='" + idProdotto + "';";
            IDbOperation composizioneRem = new WriteOperation(sqlComposizione);

            rowCount = executor.executeOperation(composizioneRem).getRowsAffected()
                    + executor.executeOperation(collocazioneRem).getRowsAffected()
                    + executor.executeOperation(prodottoRem).getRowsAffected()
                    + executor.executeOperation(articoloRem).getRowsAffected();


        }else if (isProdottoComposito(idProdotto)){

            ProdottoComposito prod = loadProdottoComposito(idProdotto);

            for (IProdotto prodot: prod.getSottoProdotti()) {
                removeProdotto(prodot.getId());
            }
            rowCount = executor.executeOperation(prodottoRem).getRowsAffected()
                    + executor.executeOperation(articoloRem).getRowsAffected();

        }

        return rowCount;
    }

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
                servizio.setPrezzo(rs.getFloat("prezzo"));
                servizio.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                servizio.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                servizio.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
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

    @Override
    public List<Servizio> loadAllServizi() {
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
                servizio.setPrezzo(rs.getFloat("prezzo"));
                servizio.setCategoria(categoriaDAO.loadCategoria(rs.getInt("Categoria_idCategoria")));
                servizio.setImmagini(fotoDAO.loadAllFotoOfArticolo(rs.getInt("idArticolo")));
                servizio.setRecensioni(recensioneDAO.loadRecensioniOfArticolo(rs.getInt("idArticolo")));
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

    @Override
    public int addServizio(Servizio servizio) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlArticolo = "INSERT INTO `myshop`.`articolo` (`nome`, `prezzo`, `Categoria_idCategoria`) VALUES " +
                "('" + servizio.getNome() + "', '" + servizio.getPrezzo() + "', '"+ servizio.getCategoria().getId()+"');";

        IDbOperation add = new WriteByteOperation(sqlArticolo);

        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGen = -1;
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;
    }

    @Override
    public int updateServizio(Servizio servizio) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlUpdateArticolo = "UPDATE `myshop`.`articolo` SET `nome` = '" + servizio.getNome() +
                "', `prezzo` = '" + servizio.getPrezzo() +
                "', `Categoria_idCategoria` = '" + servizio.getCategoria().getId() +
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

        return rowCountServizio + rowCountArticolo;
    }

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

    @Override
    public int setFKProduttoreToDefault(int idProduttore) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.prodotto SET Produttore_idProduttore = '" + ProduttoreDAO.PRODUTTORE_DEFAULT_ID +
                "' WHERE `Produttore_idProduttore` = '" + idProduttore + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }
}
