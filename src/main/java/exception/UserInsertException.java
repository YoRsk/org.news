package exception;


public class UserInsertException extends UserException {
    public UserInsertException(String message) {
        super(message);
    }

    public UserInsertException(String message, Throwable cause) {
        super(message, cause);
    }
}
