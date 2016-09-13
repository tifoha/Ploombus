package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class BugFixedCrawlController extends CrawlController{
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlController.class);

    public BugFixedCrawlController(CrawlConfig config, PageFetcher pageFetcher, RobotstxtServer robotstxtServer) throws Exception {
        super(config, pageFetcher, robotstxtServer);
    }

    @Override
    public void addSeed(String pageUrl, int docId) {
        String canonicalUrl = URLCanonicalizer.getCanonicalURL(pageUrl);
        if (canonicalUrl == null) {
            LOGGER.error("Invalid seed URL: {}", pageUrl);
        } else {
            if (docId < 0) {
                docId = docIdServer.getDocId(canonicalUrl);
                if (docId > 0) {
                    LOGGER.trace("This URL is already seen.");
                    return;
                }
                docId = docIdServer.getNewDocID(canonicalUrl);
            } else {
                try {
                    docIdServer.addUrlAndDocId(canonicalUrl, docId);
                } catch (Exception e) {
                    LOGGER.error("Could not add seed: {}", e.getMessage());
                }
            }

            WebURL webUrl = new WebURL();
            webUrl.setURL(canonicalUrl);
            webUrl.setDocid(docId);
            webUrl.setDepth((short) 0);
            if (robotstxtServer.allows(webUrl)) {
                //этот метод будит очередь
                frontier.scheduleAll(Collections.singletonList(webUrl));
            } else {
                LOGGER.warn("Robots.txt does not allow this seed: {}",
                        pageUrl); // using the WARN level here, as the user specifically asked to add this seed
            }
        }
    }
}
