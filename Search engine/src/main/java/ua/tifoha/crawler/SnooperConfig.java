package ua.tifoha.crawler;

import java.util.function.*;

/**
 * Created by Vitaly on 04.09.2016.
 */
public class SnooperConfig implements Configuration{
    /**
     * Maximum depth of crawling For unlimited depth this parameter should be
     * set to -1. Maximum value for crawl depth is 32767
     */
    private int maxDepthOfCrawling = -1;

    /**
     * Maximum number of pages to fetch For unlimited number of pages, this
     * parameter should be set to -1
     */
    private int maxPagesToFetch = -1;

    private long delayBeforeEnding = 10000;
    private int numberOfSnoopDogs = Runtime.getRuntime().availableProcessors();;
    private PageFetcher pageFetcher;
    private PageIndexer pageIndexer;
    private UnaryOperator<Link> linkHandler = UnaryOperator.identity();
    private Predicate<Link> linkFilter = link -> true;

    /*Filtering urls*/
    private UrlFilter urlFilter;

    @Override
    public void validate() {
        if (maxDepthOfCrawling < -1) {
            throw new RuntimeException("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
        }
        if (maxDepthOfCrawling > Short.MAX_VALUE) {
            throw new RuntimeException("Maximum value for crawl depth is " + Short.MAX_VALUE);
        }
        if (numberOfSnoopDogs < 1) {
            throw new RuntimeException("Minimum number of snoop dogs should be greater than 1");
        }
        if (pageFetcher == null) {
            throw new RuntimeException("Page fetcher shouldn't be null");
        }
        if (pageIndexer == null) {
            throw new RuntimeException("Page indexer shouldn't be null");
        }

    }

    public long getDelayBeforeEnding() {
        return delayBeforeEnding;
    }

    public int getMaxPagesToFetch() {
        return maxPagesToFetch;
    }

    public int getNumberOfSnoopDogs() {
        return numberOfSnoopDogs;
    }

    public PageFetcher getPageFetcher() {
        return pageFetcher;
    }

    public int getMaxDepthOfCrawling() {
        return maxDepthOfCrawling;
    }

    public PageIndexer getIndexer() {
        return pageIndexer;
    }

    public UnaryOperator<Link> getLinkHandler() {
        return linkHandler;
    }

    public  Predicate<Link> geLinkFilter() {
        return linkFilter;
    }
}
