package ua.tifoha.search.indexer.crawler;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface CrawlerInfo {
    long getTotalProcessedPages();

    long getTotalLinks();

    long getTotalDataSize();

    void add(CrawlerInfo that);
}
