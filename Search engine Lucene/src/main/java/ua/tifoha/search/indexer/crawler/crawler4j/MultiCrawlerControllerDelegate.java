package ua.tifoha.search.indexer.crawler.crawler4j;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.frontier.DocIDServer;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.util.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.AbstractConfigurableClass;
import ua.tifoha.search.exception.CrawlerException;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.indexer.CrawlRequest;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.indexer.crawler.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiPredicate;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class MultiCrawlerControllerDelegate extends AbstractConfigurableClass<CrawlerConfiguration> implements Crawler {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SingletonCrawlerControllerDelegateImpl.class);

    private final Map<CrawlRequest, CrawlResponse> crawlControllers = new HashMap<>();
    private final Indexer indexer;
    private final DocIDServer docIdServer;
    private final Environment env;
    private final PageFetcher pageFetcher;
    private final RobotstxtServer robotstxtServer;


//    private final BiPredicate<WebPageAdapterImpl, LinkAdapterImpl> pageFilter;

    public MultiCrawlerControllerDelegate(CrawlerConfiguration config, Indexer indexer) throws CrawlerException {
        super(config);
        this.indexer = indexer;

        boolean resumable = config.isResumableCrawling();

        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(resumable);
        envConfig.setLocking(resumable);

        Path envHome = Paths.get(config.getCrawlStorageFolder() + "/docIdServer");
        if (!Files.exists(envHome)) {
            try {
                Files.createDirectories(envHome);
                LOGGER.debug("Created folder: " + envHome.toAbsolutePath());
            } catch (IOException e) {
                throw new CrawlerException("Failed creating the frontier folder: " + envHome.toAbsolutePath(), e);
            }
        }

        if (!resumable) {
            IO.deleteFolderContents(envHome.toFile());
            LOGGER.info("Deleted contents of: " + envHome + " ( as you have configured resumable crawling to false )");
        }

        env = new Environment(envHome.toFile(), envConfig);
        CrawlConfig docIdConfig = new CrawlConfig();
        docIdConfig.setResumableCrawling(config.isResumableCrawling());
        docIdServer = new DocIDServer(env, docIdConfig);

        pageFetcher = new PageFetcher(new ImmutableCrawlConfigAdapter(config));
        robotstxtServer = new RobotstxtServer(new ImmutableRobotstxtConfigAdapter(config), pageFetcher);
    }

//    public MultiCrawlerControllerDelegateImpl(CrawlerConfigurationImpl config, IndexerImpl indexer) throws CrawlerException {
//        this(config, indexer,  ONLY_HTML_FILTER.and(ONLY_ONE_DOMAIN));
//
//    }

    @Override
    public CrawlResponse addSeed(CrawlRequest request) {
        return crawlControllers.computeIfAbsent(request, this::addCrawlController);
    }

    private SharedCrawlController addCrawlController(CrawlRequest request) {
        CrawlConfig crawlConfig = Utils.convert(this.config);
        crawlConfig.setMaxDepthOfCrawling(request.getMaxDepthOfCrawling());
        crawlConfig.setMaxOutgoingLinksToFollow(request.getMaxOutgoingLinksToFollow());
        crawlConfig.setMaxPagesToFetch(request.getMaxPagesToFetch());
        crawlConfig.setShutdownOnEmptyQueue(true);
        crawlConfig.setCrawlStorageFolder(config.getCrawlStorageFolder() + "/" + crawlControllers.size());
        final BiPredicate<WebPage, Link> pageFilter = request.getPageFilter();
        CrawlController.WebCrawlerFactory<WebCrawler> crawlerFactory = () -> new IndexerCrawler(indexer, pageFilter);

        try {
            SharedCrawlController crawlController = new SharedCrawlController(crawlConfig, docIdServer, pageFetcher, robotstxtServer, config.getDelayBeforeShutdownCrawl());
            crawlController.addSeed(request.getUrl());
            crawlController.startNonBlocking(crawlerFactory, config.getNumberOfCralwers());
            LOGGER.info("Web crawler controller started with %d crawlers", config.getNumberOfCralwers());
            return crawlController;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            pageFetcher.shutDown();
            throw new CrawlerException(e);
        }
    }

    //    @Override
    public boolean isFinished() {
        return crawlControllers.values().stream().allMatch(CrawlResponse::isFinished);
    }

    //    @Override
    public boolean isShuttingDown() {
        return crawlControllers.values().stream().allMatch(CrawlResponse::isShuttingDown);
    }

    //    @Override
    public void shutdown() {
        crawlControllers.forEach((request, crawlController) -> crawlController.shutdown());

    }

    @Override
    public BasicCrawlerInfo waitUntilEnd() {
        BasicCrawlerInfo status = new BasicCrawlerInfo();

        for (CrawlResponse crawlController : crawlControllers.values()) {
            status.add(crawlController.waitUntilEnd());
        }

        return status;
    }

    @Override
    public void waitUntilFinish() {
        crawlControllers.forEach((request, crawlController) -> crawlController.waitUntilEnd());
    }

    @Override
    public BasicCrawlerInfo getStatus() {
        BasicCrawlerInfo result = new BasicCrawlerInfo();
        for (CrawlResponse crawlController :crawlControllers.values()) {
                CrawlerInfo stat = crawlController.getStatus();
                result.add(stat);
        }

        return result;
    }

    public CrawlerInfo getStatus(BasicCrawlRequest request) {
        CrawlerInfo status = null;
        CrawlResponse crawlController = crawlControllers.get(request);

        if (crawlController != null) {
            status = crawlController.getStatus();
        }

        return status;
    }

    @Override
    public void close() {
        shutdown();
        waitUntilFinish();
        pageFetcher.shutDown();
        docIdServer.close();
        env.close();
    }

    @Override
    public List<CrawlResponse> getCrawlerResponseList() {
        Collection<CrawlResponse> values = crawlControllers.values();
        return new ArrayList<>(values);
    }
}
