package DbInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IDbConnection {
    ResultSet executeQuery(String sqlStatement);
    int executeUpdate(String sqlStatement);
    PreparedStatement executeUpdate(String sqlStatement, boolean isPrepared);
    public PreparedStatement executeQuery(String sqlStatement, boolean isPrepared);
    void close();
}
