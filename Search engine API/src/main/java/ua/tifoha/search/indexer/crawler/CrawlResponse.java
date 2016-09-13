package ua.tifoha.search.indexer.crawler;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface CrawlResponse {

    boolean isFinished();

    boolean isShuttingDown();

    void shutdown();

    CrawlerInfo waitUntilEnd();

    CrawlerInfo getStatus();
}
