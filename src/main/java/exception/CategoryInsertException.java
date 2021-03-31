package exception;

public class CategoryInsertException extends CategoryException{
    public CategoryInsertException(String message){super(message);}
    CategoryInsertException(String message,Throwable cause){super(message,cause);}
}
