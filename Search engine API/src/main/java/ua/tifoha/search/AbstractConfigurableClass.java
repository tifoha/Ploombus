package ua.tifoha.search;

/**
 * Created by Vitaly on 11.09.2016.
 */
public abstract class AbstractConfigurableClass<T extends Configuration> implements Configurable<T>{
    protected T config;

    public AbstractConfigurableClass(T config) {
        config.validate();
        this.config = config;
    }

    public T getConfig() {
        return config;
    }

    @Override
    public void setConfig(T config) {
        this.config = config;
    }
}
