package exception;

public class UnCheckAmountException extends ArithmeticException{
    public UnCheckAmountException() {
        super();
    }

    public UnCheckAmountException(String s) {
        super(s);
    }
}
