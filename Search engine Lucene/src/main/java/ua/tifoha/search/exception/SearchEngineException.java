package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class SearchEngineException extends RuntimeException {
    public SearchEngineException() {
    }

    public SearchEngineException(String message) {
        super(message);
    }

    public SearchEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchEngineException(Throwable cause) {
        super(cause);
    }
}
