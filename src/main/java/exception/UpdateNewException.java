package exception;


public class UpdateNewException extends NewException{
    public UpdateNewException(String message) {
        super(message);
    }

    public UpdateNewException(String message, Throwable cause) {
        super(message, cause);
    }
}
