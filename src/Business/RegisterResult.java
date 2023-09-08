package Business;

public class RegisterResult {
    
    public enum Result{OK, USERNAME_TAKEN, EMAIL_TAKEN}

    private Result result;
    private String message;
    
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
