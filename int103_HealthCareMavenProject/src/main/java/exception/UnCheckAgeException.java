package exception;

public class UnCheckAgeException extends ArithmeticException{
    public UnCheckAgeException() {
        super();
    }

    public UnCheckAgeException(String s) {
        super(s);
    }
}
