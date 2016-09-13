package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class SearcherClosingException extends SearcherException {
    public SearcherClosingException() {
    }

    public SearcherClosingException(String message) {
        super(message);
    }

    public SearcherClosingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearcherClosingException(Throwable cause) {
        super(cause);
    }
}
