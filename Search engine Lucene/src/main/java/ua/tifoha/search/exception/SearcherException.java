package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class SearcherException extends SearchEngineException {
    public SearcherException() {
    }

    public SearcherException(String message) {
        super(message);
    }

    public SearcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearcherException(Throwable cause) {
        super(cause);
    }
}
