package ua.tifoha.crawler;

import junit.framework.TestCase;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class HttpPageFetcherTest extends TestCase {
    public void testFetch() throws Exception {
        PageResponse responseData;
        PageFetcherConfig config = new PageFetcherConfig();
        PageFetcher<?> fetcher = new HttpPageFetcher(config);
        responseData = fetcher.fetch(new Link("http://stackoverflow.com/questions/1311199/finding-the-position-of-search-hits-from-lucene", 1));
        responseData = fetcher.fetch(new Link("sdfs", 4));
    }

}