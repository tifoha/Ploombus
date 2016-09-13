package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class QueryParseException extends SearchEngineException {
    public QueryParseException() {
    }

    public QueryParseException(String message) {
        super(message);
    }

    public QueryParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryParseException(Throwable cause) {
        super(cause);
    }
}
