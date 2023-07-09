package DbInterface.command;

import DbInterface.DbConnection;
import DbInterface.IDbConnection;

public class WriteByteOperation implements IDbOperation{

    private IDbConnection conn = DbConnection.getInstance();
    private String sql;

    public WriteByteOperation(String sql) {
        this.sql = sql;
    }

    @Override
    public DbOperationResult execute() {
        DbOperationResult result = new DbOperationResult();
        result.setPreparedStatement(conn.executeUpdate(sql,true));
        return result;
    }

    @Override
    public void close() {
        conn.close();
    }
}
