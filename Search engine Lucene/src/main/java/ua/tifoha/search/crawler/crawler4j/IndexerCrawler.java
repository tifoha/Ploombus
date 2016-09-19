package ua.tifoha.search.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import ua.tifoha.search.indexer.BasicIndexerDocument;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.crawler.BasicCrawlerInfo;
import ua.tifoha.search.crawler.Link;
import ua.tifoha.search.crawler.WebPage;

import java.util.Set;
import java.util.function.BiPredicate;

public class IndexerCrawler extends WebCrawler {

    private final Indexer indexer;
    private BasicCrawlerInfo status;
    private final BiPredicate<WebPage, Link> pageFilter;
    public IndexerCrawler(Indexer indexer, BiPredicate<WebPage, Link> pageFilter) {
        this.pageFilter = pageFilter;
        this.status = new BasicCrawlerInfo();
        this.indexer = indexer;
    }


    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        Link link = new ImmutableLinkAdapter(url);
        WebPage page = new ImmutableWebPageAdapter(referringPage);
        return pageFilter.test(page, link);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("Visited: {}", url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData parseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = parseData.getOutgoingUrls();
            status.incProcessedPages();
            status.incTotalLinks(links.size());
            status.incTotalDataSize(page.getContentData().length);
            String title = parseData.getTitle();
            String text = parseData.getText();
            BasicIndexerDocument indexRequest = new BasicIndexerDocument(url, title, text);
            indexer.indexAsync(indexRequest);
        }
        // We dump this crawler statistics after processing every 50 pages
        if ((status.getTotalProcessedPages() % 50) == 0) {
            dumpMyData();
        }
    }

    /**
     * This function is called by controller to get the local data of this crawler when job is done
     */
    @Override
    public Object getMyLocalData() {
        return status;
    }

    /**
     * This function is called by controller before finishing the job.
     * You can put whatever stuff you need here.
     */
    @Override
    public void onBeforeExit() {
        dumpMyData();
    }

    public void dumpMyData() {
        int id = getMyId();
        // You can configure the log to output to file
        logger.info("Crawler {} > Processed Pages: {}", id, status.getTotalProcessedPages());
        logger.info("Crawler {} > Total Links Found: {}", id, status.getTotalLinks());
        logger.info("Crawler {} > Total Text Size: {}", id, status.getTotalDataSize());
    }

}
