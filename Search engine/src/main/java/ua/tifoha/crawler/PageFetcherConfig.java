package ua.tifoha.crawler;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class PageFetcherConfig implements Configuration {
    private int socketTimeout = 20000;
    private int connectionTimeout = 30000;
    private boolean includeHttpsPages = true;
    private int maxTotalConnections = 100;
    private int maxConnectionsPerHost = 100;
    private int requestDelay = 100;
    private long maxContentSize = 1048576;

    @Override
    public void validate() {
        if (requestDelay < 0) {
            throw new RuntimeException("Invalid value for politeness delay: " + requestDelay);
        }
        // TODO: 05.09.2016 validate configuration
//        throw new ConfigurationException();
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public boolean isIncludeHttpsPages() {
        return includeHttpsPages;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public int getRequestDelay() {
        //upgrade to Time object
        return requestDelay;
    }

    public long getMaxContentSize() {
        return maxContentSize;
    }
}
