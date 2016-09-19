package ua.tifoha.search.indexer;

import ua.tifoha.search.crawler.CrawlerInfo;
import ua.tifoha.search.crawler.Link;
import ua.tifoha.search.crawler.WebPage;

import java.util.function.BiPredicate;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface CrawlRequest {
    String getUrl();

    int getMaxDepthOfCrawling();

    int getMaxPagesToFetch();

    int getMaxOutgoingLinksToFollow();

    BiPredicate<WebPage, Link> getPageFilter();

}
