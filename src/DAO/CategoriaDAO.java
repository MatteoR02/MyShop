package DAO;

import DbInterface.command.DbOperationExecutor;
import DbInterface.command.IDbOperation;
import DbInterface.command.ReadOperation;
import DbInterface.command.WriteOperation;
import Model.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {

    private static CategoriaDAO instance = new CategoriaDAO();
    private Categoria categoria;
    private static ResultSet rs;

    public static int CATEGORIA_DEFAULT_ID = 1;

    private final ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    private CategoriaDAO() {
        categoria = null;
        rs = null;
    }

    public static CategoriaDAO getInstance() {
        return instance;
    }

    @Override
    public Categoria loadCategoria(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria WHERE idCategoria = '" + idCategoria + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        int idCat = 0;

        try {
            rs.next();
            categoria = new Categoria();
            if (rs.getRow() == 1) {
                idCat = rs.getInt("idCategoria");
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
            }
            if (isSottoCategoria(idCat)) {
                return loadSottoCategoria(idCat);
            } else if(isMacroCategoria(idCat)){
                return loadMacroCategoria(idCat);
            } else {
                return categoria;
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
    public Categoria loadSottoCategoria(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria AS C WHERE C.idCategoria = '" + idCategoria + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        Categoria sottoCategoria = new Categoria();
        try {
            rs.next();
            if (rs.getRow() == 1){
                sottoCategoria = new Categoria();
                sottoCategoria.setId(rs.getInt("idCategoria"));
                sottoCategoria.setNome(rs.getString("nome"));
            }return sottoCategoria;
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
    public Categoria loadMacroCategoria(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria AS C INNER JOIN myshop.categoria_has_categoria AS CC ON C.idCategoria = CC.Categoria_idCategoria WHERE C.idCategoria = '" + idCategoria + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            categoria = new Categoria();
            ArrayList<Integer> idSottoProdotti = new ArrayList<>();
            while (rs.next()){
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                idSottoProdotti.add(rs.getInt("Categoria_idCategoria1"));
            }
            for (Integer id : idSottoProdotti) {
                categoria.aggiungiSottoCategoria(loadSottoCategoria(id));
            }
            return categoria;
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
    public List<Categoria> loadAllCategorie() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Categoria> categorie = new ArrayList<>();

        try {

            while(rs.next()) {
                categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                categorie.add(categoria);
            }return categorie;
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
    public int addCategoria(Categoria categoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.categoria (nome) VALUES " +
                "('" + categoria.getNome() + "');";
        IDbOperation add = new WriteOperation(sql);
        int rowCount = executor.executeOperation(add).getRowsAffected();
        add.close();
        return rowCount;
    }

    @Override
    public int updateCategoria(Categoria categoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.categoria SET `nome` = '" + categoria.getNome() +
                "' WHERE `idCategoria` = '" + categoria.getId() + "';";

        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }

    @Override
    public int removeCategoria(int idCategoria) {

        articoloDAO.setFKCategoriaToDefault(idCategoria);

        String sql = "DELETE FROM myshop.categoria WHERE `idCategoria` = '" + idCategoria + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation write = new WriteOperation(sql);

        int rowCount = executor.executeOperation(write).getRowsAffected();
        write.close();
        return rowCount;
    }

    @Override
    public List<Categoria> loadAllMacroCategorie() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria AS C INNER JOIN myshop.categoria_has_categoria AS CC ON C.idCategoria = CC.Categoria_idCategoria;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Categoria> categorie = new ArrayList<>();

        try {
            while(rs.next()){
                categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                categorie.add(categoria);
            } return categorie;
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
    public List<Categoria> loadAllSottoCategorie() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria AS C INNER JOIN myshop.categoria_has_categoria AS CC ON C.idCategoria = CC.Categoria_idCategoria1;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Categoria> categorie = new ArrayList<>();

        try {
            while(rs.next()){
                categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                categorie.add(categoria);
            } return categorie;
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
    public List<Categoria> loadAllSottoCategoriaOfID(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.categoria AS C INNER JOIN myshop.categoria_has_categoria AS CC ON C.idCategoria = CC.Categoria_idCategoria1 WHERE CC.Categoria_idCategoria = '" + idCategoria + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Categoria> categorie = new ArrayList<>();

        try {
            while(rs.next()){
                categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                categorie.add(categoria);
            } return categorie;
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
    public boolean isSottoCategoria(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.categoria as C INNER JOIN myshop.categoria_has_categoria as CC on C.idCategoria = CC.Categoria_idCategoria1 WHERE C.idCategoria ='" + idCategoria + "';";

        IDbOperation read = new ReadOperation(sql);
        rs = executor.executeOperation(read).getResultSet();

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
            read.close();
        }
    }

    @Override
    public boolean isMacroCategoria(int idCategoria) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.categoria as C INNER JOIN myshop.categoria_has_categoria as CC on C.idCategoria = CC.Categoria_idCategoria WHERE C.idCategoria ='" + idCategoria + "';";

        IDbOperation read = new ReadOperation(sql);
        rs = executor.executeOperation(read).getResultSet();

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
            read.close();
        }
    }
}
