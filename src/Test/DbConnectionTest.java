package Test;

import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectionTest {
    @Test
    public void ConnTest(){

        IDbConnection conn = DbConnection.getInstance();

        ResultSet rs = conn.executeQuery("SELECT * FROM myshop.articolo;");
        try{
            while(rs.next()){
                System.out.println(rs.getString("nome") + " ");
                System.out.println(rs.getString("idarticolo") + " ");
                System.out.println(rs.getString("prezzo") + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }
}
