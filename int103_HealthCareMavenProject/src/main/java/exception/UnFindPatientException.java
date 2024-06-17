package exception;

public class UnFindPatientException extends NullPointerException{
    public UnFindPatientException() {
        super();
    }

    public UnFindPatientException(String s) {
        super(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
