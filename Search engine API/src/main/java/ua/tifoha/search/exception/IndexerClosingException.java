package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class IndexerClosingException extends IndexerException {
    public IndexerClosingException() {
    }

    public IndexerClosingException(String message) {
        super(message);
    }

    public IndexerClosingException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexerClosingException(Throwable cause) {
        super(cause);
    }
}
