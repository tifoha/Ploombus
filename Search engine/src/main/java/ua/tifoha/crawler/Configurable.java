package ua.tifoha.crawler;

/**
 * Created by Vitaly on 04.09.2016.
 */
public abstract class Configurable<T extends Configuration> {
    protected final T config;

    public Configurable(T config) {
        config.validate();
        this.config = config;
    }

    public T getConfig() {
        return config;
    }

}
