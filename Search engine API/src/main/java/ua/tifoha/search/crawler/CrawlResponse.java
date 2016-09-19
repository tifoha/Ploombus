package ua.tifoha.search.crawler;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface CrawlResponse {
    String getUrl();

    boolean isFinished();

    boolean isShuttingDown();

    void shutdown();

    CrawlerInfo waitUntilEnd();

    CrawlerInfo getStatus();
}
