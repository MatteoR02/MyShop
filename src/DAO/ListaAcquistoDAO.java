package DAO;

import DbInterface.IDbConnection;
import DbInterface.command.DbOperationExecutor;
import DbInterface.command.IDbOperation;
import DbInterface.command.ReadOperation;
import DbInterface.command.WriteOperation;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaAcquistoDAO implements IListaAcquistoDAO {

    private static ListaAcquistoDAO instance = new ListaAcquistoDAO();
    private ListaAcquisto listaAcquisto;
    private static ResultSet rs;


    private final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();


    private ListaAcquistoDAO() {
        listaAcquisto = null;
        rs = null;
    }

    public static ListaAcquistoDAO getInstance() {
        return instance;
    }

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

    @Override
    public List<ListaAcquisto> loadAllListaAcquisto() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<ListaAcquisto> listeAcquisto = new ArrayList<>();
        try {
            while(rs.next()) {
                listaAcquisto = new ListaAcquisto();
                listaAcquisto.setId(rs.getInt("idListaAcquisto"));
                listaAcquisto.setNome(rs.getString("nome"));
                listaAcquisto.setStatoPagamento(ListaAcquisto.StatoPagamentoType.valueOf(rs.getString("stato_pagamento")));
                listaAcquisto.setDataPagamento(rs.getTimestamp("data_pagamento"));
                listaAcquisto.setArticoli(getArticoliFromLista(rs.getInt("idListaAcquisto")));
                listeAcquisto.add(listaAcquisto);
            }return listeAcquisto;
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
    public int addListaAcquisto(String nome, int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO `myshop`.`listaacquisto` (`nome`, `Cliente_Utente_idUtente`) VALUES " +
                "('" + nome + "', '" +  idCliente + "');";
        IDbOperation add = new WriteOperation(sql);
        int rowCount = executor.executeOperation(add).getRowsAffected();
        add.close();
        return rowCount;
    }

    @Override
    public int updateListaAcquisto(ListaAcquisto listaAcquisto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sqlLista = "UPDATE `myshop`.`listaacquisto` SET `nome` = '" + listaAcquisto.getNome() +
                "', stato_pagamento ='" + listaAcquisto.getStatoPagamento() + "', data_pagamento ='"+listaAcquisto.getDataPagamento()+
                "' WHERE `idListaAcquisto` = '" + listaAcquisto.getId() +"';";

        IDbOperation update = new WriteOperation(sqlLista);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();

        for ( Articolo item : listaAcquisto.getArticoli().keySet() ) {
            String sql;
            if (isInLista(listaAcquisto.getId(), item.getId())){
                sql = "UPDATE listaacquisto_has_articolo SET ListaAcquisto_idListaAcquisto = "+ listaAcquisto.getId() +", Articolo_idArticolo = '"+ item.getId() +"', quantita = '"+ listaAcquisto.getArticoli().get(item) +"' WHERE (ListaAcquisto_idListaAcquisto = "+ listaAcquisto.getId() +") AND (Articolo_idArticolo = "+ item.getId() +");";
            } else {
                sql = "INSERT INTO listaacquisto_has_articolo (ListaAcquisto_idListaAcquisto, Articolo_idArticolo, quantita) VALUES ('"+listaAcquisto.getId()+"','"+item.getId()+"','"+listaAcquisto.getArticoli().get(item)+"');";
            }
            IDbOperation write = new WriteOperation(sql);
            rowCount += executor.executeOperation(write).getRowsAffected();
            write.close();
        }
        return rowCount;
    }

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

    @Override
    public HashMap<Articolo, Integer> getArticoliFromLista(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto ='"+ idLista +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        HashMap<Articolo, Integer> articoliInLista = new HashMap<>();
        try {
            while(rs.next()) {
                if (articoloDAO.isProdotto(rs.getInt("Articolo_idArticolo"))){
                    articoliInLista.put(articoloDAO.loadProdotto(rs.getInt("Articolo_idArticolo")),rs.getInt("quantita"));
                } else if (articoloDAO.isProdottoComposito(rs.getInt("Articolo_idArticolo"))){
                    articoliInLista.put(articoloDAO.loadProdottoComposito(rs.getInt("Articolo_idArticolo")),rs.getInt("quantita"));
                } else if (articoloDAO.isServizio(rs.getInt("Articolo_idArticolo"))) {
                    articoliInLista.put(articoloDAO.loadServizio(rs.getInt("Articolo_idArticolo")), rs.getInt("quantita"));
                }
            }return articoliInLista;
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
    public List<ListaAcquisto> getListeOfCliente(int idCliente) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.listaacquisto WHERE Cliente_Utente_idUtente = '" + idCliente + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<ListaAcquisto> listeAcquisto = new ArrayList<>();
        try {
            while(rs.next()) {
                listaAcquisto = new ListaAcquisto();
                listaAcquisto.setId(rs.getInt("idCategoria"));
                listaAcquisto.setNome(rs.getString("nome"));
                listaAcquisto.setStatoPagamento(ListaAcquisto.StatoPagamentoType.valueOf(rs.getString("stato_pagamento")));
                listaAcquisto.setDataPagamento(rs.getTimestamp("data_pagamento"));
                listeAcquisto.add(listaAcquisto);
            }return listeAcquisto;
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

    @Override
    public int removeAllArticoliInLista(int idLista) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.listaacquisto_has_articolo WHERE ListaAcquisto_idListaAcquisto = '" + idLista + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

    @Override
    public int removeArticoloFromListe(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "DELETE FROM myshop.listaacquisto_has_articolo WHERE Articolo_idArticolo = '" + idArticolo + "';";
        IDbOperation remove = new WriteOperation(sql);
        int rowCount = executor.executeOperation(remove).getRowsAffected();
        remove.close();
        return rowCount;
    }

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
