package Business;

public class ExecuteResult {
    public enum ResultStatement { OK, NOT_OK, OK_WITH_WARNINGS, WRONG_ACCESS }

    private ResultStatement result;
    private String message;

    public ResultStatement getResult() {
        return result;
    }

    public void setResult(ResultStatement result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
