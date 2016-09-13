package ua.tifoha.search;


/**
 * Created by Vitaly on 11.09.2016.
 */
public interface Configurable<T extends Configuration> {
    T getConfig();

    void setConfig(T config);
}
