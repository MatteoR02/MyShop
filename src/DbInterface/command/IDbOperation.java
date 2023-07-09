package DbInterface.command;

public interface IDbOperation {

    DbOperationResult execute();
    void close();
}
