package ua.tifoha.search.indexer.crawler;

import ua.tifoha.search.Configurable;
import ua.tifoha.search.exception.CrawlerClosingException;
import ua.tifoha.search.indexer.CrawlRequest;

import java.util.List;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface Crawler extends Configurable<CrawlerConfiguration>, CrawlResponse, AutoCloseable {
    CrawlResponse addSeed(CrawlRequest request);

    void waitUntilFinish();

    @Override
    void close() throws CrawlerClosingException;

    List<CrawlResponse> getCrawlerResponseList();
}
