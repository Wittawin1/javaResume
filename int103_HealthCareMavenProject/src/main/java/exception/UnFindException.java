package exception;

public class UnFindException extends NullPointerException{
    public UnFindException() {
        super();
    }

    public UnFindException(String s) {
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
