package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class CrawlerClosingException extends CrawlerException {
    public CrawlerClosingException() {
    }

    public CrawlerClosingException(String message) {
        super(message);
    }

    public CrawlerClosingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlerClosingException(Throwable cause) {
        super(cause);
    }
}
