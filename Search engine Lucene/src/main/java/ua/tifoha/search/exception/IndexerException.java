package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class IndexerException extends SearchEngineException {
    public IndexerException() {
    }

    public IndexerException(String message) {
        super(message);
    }

    public IndexerException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexerException(Throwable cause) {
        super(cause);
    }
}
