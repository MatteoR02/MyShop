package DAO;

import DbInterface.IDbConnection;
import DbInterface.command.*;
import Model.Articolo;
import Model.Categoria;
import Model.Foto;
import Model.IProdotto;

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

    public final static int FOTO_DEFAULT_ID = 1;

    /**
     * Carica una foto dal database
     * @param idFoto della foto da caricare
     * @return foto caricata dal database
     */
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

    /**
     * Carica tutte le foto dal database
     * @return list di tutte le foto caricate
     */
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

    /**
     * Carica tutte le foto di una particolare recensione
     * @param idRecensione della recensione di cui si vuole avere le foto
     * @return list di foto della recensione
     */
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

    /**
     * Carica tutte le foto di un particolare articolo
     * @param idArticolo dell'articolo di cui si vuole avere le foto
     * @return list di foto dell'articolo
     */
    @Override
    public List<Foto> loadAllFotoOfArticolo(int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.articolo_has_foto WHERE Articolo_idArticolo ='"+ idArticolo +"';";
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Foto> fotos = new ArrayList<>();
        ArrayList<Integer> idFoto = new ArrayList<>();
        try {
            while(rs.next()) {
                idFoto.add(rs.getInt("Foto_idFoto"));
            }
            for (int id : idFoto) {
                fotos.add(loadFoto(id));
            }
            return fotos;
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
     * Aggiunge una foto al database
     * @param foto da aggiungere al database
     * @return numero di righe modificate sul database
     */
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

    /**
     * Aggiorna i dati di una foto sul database
     * @param foto aggiornata
     * @return numero di righe modificate sul database
     */
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


    /**
     * Elimina una foto dal database
     * @param idFoto della foto da eliminare
     * @return numero di righe modificate sul database
     */
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

    /**
     *
     * @param idFoto
     * @return true se la foto è di un articolo, false altrimenti
     */
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

    /**
     * Aggiunge sul database una foto e la assegna ad un articolo
     * @param foto nuova da aggiungere al database
     * @param idArticolo a cui assegnare la foto
     * @return numero di righe modificate sul database
     */
    @Override
    public int addNewFotoToArticolo(Foto foto, int idArticolo) {
        DbOperationExecutor executor = new DbOperationExecutor();

        String sqlFoto = "INSERT INTO myshop.foto (immagine) VALUES (?);";

        IDbOperation add = new WriteByteOperation(sqlFoto);
        int rowCount = 0;
        try {
            PreparedStatement statement = executor.executeOperation(add).getPreparedStatement();

            int idGen = -1;
            try {
                if (statement!=null){
                    statement.setBlob(1, foto.getImmagine());
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

                String sqlArtFoto = "INSERT INTO myshop.articolo_has_foto (Articolo_idArticolo ,Foto_idFoto) VALUES " +
                        "('"+ idArticolo +"', '" + idGen + "');";
                add = new WriteOperation(sqlArtFoto);
                rowCount += executor.executeOperation(add).getRowsAffected();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            add.close();
        }
        return rowCount;
    }

    /**
     * Carica la foto di default dal database
     * @return foto di default
     */
    @Override
    public Foto loadDefaultFoto() {
        DbOperationExecutor executor = new DbOperationExecutor();
        String sql = "SELECT * FROM myshop.foto WHERE idFoto = '" + FOTO_DEFAULT_ID +"';";
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

    /**
     * Imposta sul database la chiave esterna riferita ad articolo a quella di default
     * @param idArticolo chiave esterna da impostare a valore di default
     * @return numero di righe modificate sul database
     */
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
