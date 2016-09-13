package ua.tifoha.search.exception;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class ConfigurationException extends SearchEngineException {
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
