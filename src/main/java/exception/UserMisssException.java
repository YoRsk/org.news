package exception;


public class UserMisssException extends UserException {
    public UserMisssException(String message) {
        super(message);
    }

    public UserMisssException(String message, Throwable cause) {
        super(message, cause);
    }
}
