package ua.tifoha.search;

import ua.tifoha.search.exception.ConfigurationException;

/**
 * Created by Vitaly on 11.09.2016.
 */
public interface Configuration {
    void validate() throws ConfigurationException;
}
