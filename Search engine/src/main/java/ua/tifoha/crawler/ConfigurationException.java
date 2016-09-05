package ua.tifoha.crawler;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
