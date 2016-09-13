package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class IndexDirectoryException extends SearchEngineException {
    public IndexDirectoryException() {
    }

    public IndexDirectoryException(String message) {
        super(message);
    }

    public IndexDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexDirectoryException(Throwable cause) {
        super(cause);
    }
}
