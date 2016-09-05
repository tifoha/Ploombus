package ua.tifoha.crawler;

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

    /**
     * Politeness delay in milliseconds (delay between sending two requests to
     * the same host).
     */
    private int politenessDelay = 200;

    /*
    * Filtering urls*/
    private UrlFilter urlFilter;
    private long delayBeforeEnding;
    private int numberOfSnoopDogs = Runtime.getRuntime().availableProcessors();;

    @Override
    public void validate() {
        if (politenessDelay < 0) {
            throw new RuntimeException("Invalid value for politeness delay: " + politenessDelay);
        }
        if (maxDepthOfCrawling < -1) {
            throw new RuntimeException("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
        }
        if (maxDepthOfCrawling > Short.MAX_VALUE) {
            throw new RuntimeException("Maximum value for crawl depth is " + Short.MAX_VALUE);
        }
        if (numberOfSnoopDogs < 1) {
            throw new RuntimeException("Minimum number of snoop dogs should be greater than 1");
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
}
