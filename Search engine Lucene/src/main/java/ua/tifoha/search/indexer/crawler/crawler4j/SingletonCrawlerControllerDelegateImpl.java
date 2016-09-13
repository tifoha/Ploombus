package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.AbstractConfigurableClass;
import ua.tifoha.search.exception.CrawlerException;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.indexer.CrawlRequest;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.indexer.crawler.*;

import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class SingletonCrawlerControllerDelegateImpl extends AbstractConfigurableClass<CrawlerConfiguration> implements Crawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonCrawlerControllerDelegateImpl.class);
    public static final BiPredicate<WebPage, Link> ONLY_HTML_FILTER = new BiPredicate<WebPage, Link>() {
        private final Pattern FILTERS = Pattern.compile(
                ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                        "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        @Override
        public boolean test(WebPage webPage, Link link) {
            return !FILTERS.matcher(link.getURL()).matches();
        }
    };
    public static final BiPredicate<WebPage, Link> ONLY_ONE_DOMAIN = new BiPredicate<WebPage, Link>() {
        @Override
        public boolean test(WebPage webPage, Link link) {
            Link parentLink = link.getParent();
            if (parentLink != null) {
                String parentLinkDomain = parentLink.getDomain();
                String linkDomain = link.getDomain();
                return parentLinkDomain.equalsIgnoreCase(linkDomain);
            }
            return true;
        }
    };


    private class CrawlerFactory implements CrawlController.WebCrawlerFactory<WebCrawler> {

        @Override
        public WebCrawler newInstance() throws Exception {
            return new IndexerCrawler(indexer, pageFilter);
        }
    }

    private final CrawlController crawlController;
    private final Indexer indexer;
    private final BiPredicate<WebPage, Link> pageFilter;

    public SingletonCrawlerControllerDelegateImpl(CrawlerConfiguration config, Indexer indexer, BiPredicate<WebPage, Link> pageFilter) throws CrawlerException {
        super(config);
        this.indexer = indexer;
        this.pageFilter = pageFilter;
        CrawlConfig crawlConfig = new ImmutableCrawlConfigAdapter(this.config);

        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        CrawlController.WebCrawlerFactory<WebCrawler> crawlerFactory = new CrawlerFactory();
        RobotstxtConfig robotstxtConfig = new ImmutableRobotstxtConfigAdapter(config);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try {
            crawlController = new BugFixedCrawlController(crawlConfig, pageFetcher, robotstxtServer) {
            };
        } catch (Exception e) {
            LOGGER.error(e.toString());
            pageFetcher.shutDown();
            throw new CrawlerException(e);
        }

        crawlController.startNonBlocking(crawlerFactory, config.getNumberOfCralwers());
        LOGGER.info("Web crawler controller started with %d crawlers", config.getNumberOfCralwers());
    }

    public SingletonCrawlerControllerDelegateImpl(CrawlerConfiguration config, Indexer indexer) throws CrawlerException {
        this(config, indexer, ONLY_HTML_FILTER.and(ONLY_ONE_DOMAIN));

    }

    @Override
    public CrawlResponse addSeed(CrawlRequest request) {
        crawlController.addSeed(request.getUrl());
        return this;
    }

    @Override
    public boolean isFinished() {
        return crawlController.isFinished();
    }

    @Override
    public boolean isShuttingDown() {
        return crawlController.isShuttingDown();
    }

    @Override
    public void shutdown() {
        crawlController.shutdown();
    }

    @Override
    public BasicCrawlerInfo waitUntilEnd() {
        crawlController.waitUntilFinish();
        return getStatus();
    }

    @Override
    public void waitUntilFinish() {
        crawlController.waitUntilFinish();
    }

    @Override
    public BasicCrawlerInfo getStatus() {
        BasicCrawlerInfo result = new BasicCrawlerInfo();
        List<Object> crawlersLocalData = crawlController.getCrawlersLocalData();

        for (Object localData : crawlersLocalData) {
            BasicCrawlerInfo stat = (BasicCrawlerInfo) localData;
            result.add(stat);
        }

        return result;
    }

    @Override
    public void close() {
        crawlController.shutdown();
    }

    @Override
    public List<CrawlResponse> getCrawlerResponseList() {
        return Collections.singletonList(this);
    }
}

