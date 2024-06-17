package exception;

public class InfoRequireException extends RuntimeException{
    public InfoRequireException() {
        super();
    }

    public InfoRequireException(String message) {
        super(message);
    }

    public InfoRequireException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoRequireException(Throwable cause) {
        super(cause);
    }

    protected InfoRequireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
