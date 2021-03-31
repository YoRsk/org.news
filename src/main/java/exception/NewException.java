package exception;


public class NewException extends RuntimeException {
    public NewException(String message) {
        super(message);
    }

    public NewException(String message, Throwable cause) {
        super(message, cause);
    }
}
