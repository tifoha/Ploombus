package ua.tifoha.crawler;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class DataFetcherException extends RuntimeException {
    public DataFetcherException() {
    }

    public DataFetcherException(String message) {
        super(message);
    }

    public DataFetcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFetcherException(Throwable cause) {
        super(cause);
    }

}
