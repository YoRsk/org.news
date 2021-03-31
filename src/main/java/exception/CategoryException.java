package exception;

import entity.Category;

public class CategoryException extends RuntimeException{
    CategoryException(String message){ super(message); }
    CategoryException(String message, Throwable cause){super(message,cause);}
}
