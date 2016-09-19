package ua.tifoha.search.crawler.crawler4j;

import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import ua.tifoha.search.crawler.CrawlerConfiguration;

public class ImmutableRobotstxtConfigAdapter extends RobotstxtConfig {
    private final CrawlerConfiguration config;

    ImmutableRobotstxtConfigAdapter(CrawlerConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean isEnabled() {
        return config.isEnabledRobotstxt();
    }

    @Override
    public void setEnabled(boolean enabled) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public String getUserAgentName() {
        return config.getUserAgentNameRobotstxt();
    }

    @Override
    public void setUserAgentName(String userAgentName) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getCacheSize() {
        return config.getCacheSizeRobotstxt();
    }

    @Override
    public void setCacheSize(int cacheSize) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }
}
