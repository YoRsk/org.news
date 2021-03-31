package exception;

public class UpdateCategoryException extends CategoryException{
    public UpdateCategoryException(String message) {
        super(message);
    }

    public UpdateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
