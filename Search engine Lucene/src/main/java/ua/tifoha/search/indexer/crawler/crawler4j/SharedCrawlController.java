package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.frontier.DocIDServer;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.indexer.crawler.BasicCrawlerInfo;
import ua.tifoha.search.indexer.crawler.CrawlResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class SharedCrawlController extends BugFixedCrawlController implements CrawlResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlController.class);
    protected int delayBeforeShutdownCrawl;

    public SharedCrawlController(CrawlConfig config, DocIDServer docIdServer, PageFetcher pageFetcher, RobotstxtServer robotstxtServer, int delayBeforeShutdownCrawl) throws Exception {
        super(config, pageFetcher, robotstxtServer);
        this.docIdServer = docIdServer;
        this.delayBeforeShutdownCrawl = delayBeforeShutdownCrawl;
    }

    @Override
    public BasicCrawlerInfo waitUntilEnd() {
        waitUntilFinish();
        return getStatus();
    }

    @Override
    public BasicCrawlerInfo getStatus() {
        BasicCrawlerInfo result = new BasicCrawlerInfo();
        List<Object> crawlersLocalData = getCrawlersLocalData();

        for (Object localData : crawlersLocalData) {
            BasicCrawlerInfo stat = (BasicCrawlerInfo) localData;
            result.add(stat);
        }

        return result;
    }

    @Override
    public void shutdown() {
        LOGGER.info("Shutting down...");
        this.shuttingDown = true;
        frontier.finish();
    }

    @Override
    protected <T extends WebCrawler> void start(WebCrawlerFactory<T> crawlerFactory, int numberOfCrawlers, boolean isBlocking) {
        try {
            finished = false;
            crawlersLocalData.clear();
            final List<Thread> threads = new ArrayList<>();
            final List<T> crawlers = new ArrayList<>();

            for (int i = 1; i <= numberOfCrawlers; i++) {
                T crawler = crawlerFactory.newInstance();
                Thread thread = new Thread(crawler, "Crawler " + i);
                crawler.setThread(thread);
                crawler.init(i, this);
                thread.start();
                crawlers.add(crawler);
                threads.add(thread);
                LOGGER.info("Crawler {} started", i);
            }

            final CrawlController controller = this;

            Thread monitorThread = new Thread(() -> {
                try {
                    synchronized (waitingLock) {

                        while (true) {
                            sleep(delayBeforeShutdownCrawl);
                            boolean someoneIsWorking = false;
                            for (int i = 0; i < threads.size(); i++) {
                                Thread thread = threads.get(i);
                                if (!thread.isAlive()) {
                                    if (!shuttingDown) {
                                        LOGGER.info("Thread {} was dead, I'll recreate it", i);
                                        T crawler = crawlerFactory.newInstance();
                                        thread = new Thread(crawler, "Crawler " + (i + 1));
                                        threads.remove(i);
                                        threads.add(i, thread);
                                        crawler.setThread(thread);
                                        crawler.init(i + 1, controller);
                                        thread.start();
                                        crawlers.remove(i);
                                        crawlers.add(i, crawler);
                                    }
                                } else if (crawlers.get(i).isNotWaitingForNewURLs()) {
                                    someoneIsWorking = true;
                                }
                            }
                            boolean shut_on_empty = config.isShutdownOnEmptyQueue();
                            if (!someoneIsWorking && (shut_on_empty || shuttingDown)) {
                                // Make sure again that none of the threads
                                // are
                                // alive.
                                LOGGER.info("It looks like no thread is working, waiting for {} millliseconds to make sure...", delayBeforeShutdownCrawl);
                                sleep(delayBeforeShutdownCrawl);

                                someoneIsWorking = false;
                                for (int i = 0; i < threads.size(); i++) {
                                    Thread thread = threads.get(i);
                                    if (thread.isAlive() && crawlers.get(i).isNotWaitingForNewURLs()) {
                                        someoneIsWorking = true;
                                    }
                                }
                                if (!someoneIsWorking) {
                                    if (!shuttingDown) {
                                        long queueLength = frontier.getQueueLength();
                                        if (queueLength > 0) {
                                            continue;
                                        }
                                        LOGGER.info(
                                                "No thread is working and no more URLs are in queue waiting for another {} millliseconds to make " +
                                                        "sure...", delayBeforeShutdownCrawl);
                                        sleep(delayBeforeShutdownCrawl);
                                        queueLength = frontier.getQueueLength();
                                        if (queueLength > 0) {
                                            continue;
                                        }
                                    }

                                    LOGGER.info("All of the crawlers are stopped. Finishing the process...");
                                    // At this step, frontier notifies the threads that were waiting for new URLs and they should stop
                                    frontier.finish();
                                    for (T crawler : crawlers) {
                                        crawler.onBeforeExit();
                                        crawlersLocalData.add(crawler.getMyLocalData());
                                    }

                                    LOGGER.info("Waiting for {} millliseconds before final clean up...", delayBeforeShutdownCrawl);
                                    sleep(delayBeforeShutdownCrawl);

                                    frontier.close();
//                                    controlled by crawl manager
//                                    docIdServer.close();
//                                    pageFetcher.shutDown();

                                    finished = true;
                                    waitingLock.notifyAll();
//                                    controlled by crawl manager
//                                    env.close();

                                    return;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Unexpected Error", e);
                }
            });

            monitorThread.start();

            if (isBlocking) {
                waitUntilFinish();
            }

        } catch (Exception e) {
            LOGGER.error("Error happened", e);
        }
    }

    @Override
    public void addSeed(String pageUrl, int docId) {
        super.addSeed(pageUrl, docId);
    }

    protected static void sleep(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException ignored) {
            // Do nothing
        }
    }


}
