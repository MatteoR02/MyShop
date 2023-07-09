package DAO;

import DbInterface.IDbConnection;
import DbInterface.command.*;
import Model.Articolo;
import Model.Categoria;
import Model.Foto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FotoDAO implements IFotoDAO{

    private static FotoDAO instance = new FotoDAO();
    private Foto foto;
    private static ResultSet rs;

    private FotoDAO(){
        foto = null;
        rs = null;
    }
    public static FotoDAO getInstance() {
        return instance;
    }

    @Override
    public Foto loadFoto(int idFoto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.foto WHERE idFoto = '" + idFoto + "';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                foto = new Foto();
                foto.setId(rs.getInt("idFoto"));
                foto.setImmagine(rs.getBlob("immagine"));
                return foto;
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
    public List<Foto> loadAllFoto() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.foto;";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Foto> fotos = new ArrayList<>();

        try {
            while(rs.next()){
                foto = new Foto();
                foto.setId(rs.getInt("idFoto"));
                foto.setImmagine(rs.getBlob("immagine"));
                fotos.add(foto);
            } return fotos;
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
    public List<Foto> loadAllFotoOfRecensione(int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.foto WHERE Recensione_idRecensione='"+ idRecensione +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Foto> fotos = new ArrayList<>();

        try {
            while(rs.next()){
                foto = new Foto();
                foto.setId(rs.getInt("idFoto"));
                foto.setImmagine(rs.getBlob("immagine"));
                fotos.add(foto);
            } return fotos;
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
    public List<Foto> loadAllFotoOfArticolo(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo_has_foto WHERE Articolo_idArticolo ='"+ idArticolo +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Foto> fotos = new ArrayList<>();
        try {
            while(rs.next()) {
                foto = loadFoto(rs.getInt("Foto_idFoto"));
                fotos.add(foto);
            }return fotos;
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
    public int addFoto(Foto foto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.foto (immagine) VALUES (?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setBlob(1, foto.getImmagine());
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
    public int addFotoToRecensione(Foto foto, int idRecensione) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.foto (immagine, Recensione_idRecensione) VALUES (?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setBlob(1, foto.getImmagine());
                preparedStatement.setInt(2, idRecensione);
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
    public int updateFoto(Foto foto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.foto SET immagine = ? WHERE idFoto = ?;";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setBlob(1, foto.getImmagine());
                preparedStatement.setInt(2, foto.getId());
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
    public int updateFotoOfRecensione(Foto foto, int idRecensione) {
        return 0;
    }


    @Override
    public int removeFoto(int idFoto) {
        DbOperationExecutor executor = new DbOperationExecutor();
        int rowCountArtFoto = 0;

        if(isFotoOfArticolo(idFoto)){
            String sqlArtFoto = "DELETE FROM myshop.articolo_has_foto WHERE Foto_idFoto='"+idFoto+"';";
            IDbOperation removeArtFoto = new WriteOperation(sqlArtFoto);
            rowCountArtFoto = executor.executeOperation(removeArtFoto).getRowsAffected();
            removeArtFoto.close();
        }
        String sqlFoto = "DELETE FROM myshop.foto WHERE idFoto = '" + idFoto + "';";
        IDbOperation removeFoto = new WriteOperation(sqlFoto);
        int rowCountFoto = executor.executeOperation(removeFoto).getRowsAffected();
        removeFoto.close();
        return rowCountFoto + rowCountArtFoto;
    }

    @Override
    public boolean isFotoOfArticolo(int idFoto) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT count(*) AS count FROM myshop.articolo_has_foto WHERE Foto_idFoto='" + idFoto +"';";

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
    public int addFotoToArticolo(Foto foto, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "INSERT INTO myshop.articolo_has_foto (Articolo_idArticolo, Foto_idFoto) VALUES (?,?);";

        IDbOperation addByte = new WriteByteOperation(sql);
        PreparedStatement preparedStatement = executor.executeOperation(addByte).getPreparedStatement();
        int rowCount;
        try{
            if(preparedStatement!=null) {
                preparedStatement.setInt(1, idArticolo);
                preparedStatement.setInt(2, foto.getId());
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
    public int setFKArticoloHasFotoToDefault(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "UPDATE myshop.articolo_has_foto SET Articolo_idArticolo = '" + ArticoloDAO.ARTICOLO_DEFAULT_ID +
                "' WHERE `Articolo_idArticolo` = '" + idArticolo + "';";
        IDbOperation update = new WriteOperation(sql);
        int rowCount = executor.executeOperation(update).getRowsAffected();
        update.close();
        return rowCount;
    }


}
