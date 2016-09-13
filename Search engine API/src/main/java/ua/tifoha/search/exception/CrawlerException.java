package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class CrawlerException extends SearchEngineException {
    public CrawlerException() {
    }

    public CrawlerException(String message) {
        super(message);
    }

    public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlerException(Throwable cause) {
        super(cause);
    }
}
