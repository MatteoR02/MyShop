package DbInterface.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbOperationResult {

    private int rowsAffected;
    private ResultSet resultSet;
    private int idAdded;
    private PreparedStatement preparedStatement;

    public int getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public int getIdAdded() {
        return idAdded;
    }

    public void setIdAdded(int idAdded) {
        this.idAdded = idAdded;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
}
