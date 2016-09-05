package ua.tifoha.crawler;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class ResponseDataParserException extends RuntimeException {
    public ResponseDataParserException() {
    }

    public ResponseDataParserException(String message) {
        super(message);
    }

    public ResponseDataParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseDataParserException(Throwable cause) {
        super(cause);
    }

}
