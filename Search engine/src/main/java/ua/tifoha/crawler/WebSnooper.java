package ua.tifoha.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.util.UrlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.UnaryOperator;

/**
 * Created by Vitaly on 04.09.2016.
 */
public class WebSnooper extends Configurable<SnooperConfig> implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(WebSnooper.class);

    private Set<Link> visitedUrls = new ConcurrentSkipListSet<>();
    private BlockingDeque<Link> urlsToVisit = new LinkedBlockingDeque<>();
    private ExecutorService exec;
//    private LinkFactory linkFactory = new LinkFactory(config);

    public WebSnooper(SnooperConfig config) {
        super(config);
        logger.debug("Config loaded");

        int numberOfSnoopDogs = config.getNumberOfSnoopDogs();
        logger.debug("Creating new instance with {} snoop dogs", numberOfSnoopDogs);
        exec = Executors.newFixedThreadPool(numberOfSnoopDogs);

        for (int i = 0; i < numberOfSnoopDogs; i++) {
            exec.submit(new SnoopDog(String.valueOf(i)));
        }

    }

    private void addLink_0(String url) {
        if (exec.isShutdown()) {
            logger.error("Unable to add URL: {}", url);
            throw new RuntimeException("Shooper is shuted down");
        }
        try {
            String canonicalUrl = UrlUtils.getCanonicalURL(url);
            if (canonicalUrl != null) {
                Link link = new Link(canonicalUrl, config.getMaxDepthOfCrawling());
                this.urlsToVisit.put(link);
                logger.error("URL: {} putted in the queue", url);
            } else {
                logger.error("Invalid URL: {}", url);
            }
        } catch (InterruptedException e) {
            logger.error("Unable to add URL: {}", url);
        }
    }

    private Link getNextLink() throws InterruptedException {
        Link nextLink = null;
        do {
            nextLink = urlsToVisit.take();
        } while (this.visitedUrls.contains(nextLink));
        visitedUrls.add(nextLink);

        return nextLink;
    }

    public void shutdown() {
        exec.shutdown();
        logger.info("Shutdown");
    }

    public boolean isShutdown() {
        return exec.isShutdown();
    }

    public boolean isTerminated() {
        logger.info("Terminating...");
        boolean terminated = exec.isTerminated();
        logTermination(terminated);
        return terminated;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        logger.info("Awaiting termination...");
        boolean terminated = exec.awaitTermination(timeout, unit);
        logTermination(terminated);
        return terminated;
    }

    private void logTermination(boolean terminated) {
        if (terminated) {
            logger.info("Terminated");
        } else {
            logger.info("Not terminated");
        }
    }

    public List<Link> shutdownNow() {
        exec.shutdownNow();
        logger.info("Shutdown now");
        return new ArrayList<>(urlsToVisit);
    }

    @Override
    public void close() throws Exception {
        logger.info("Shutdown");
        exec.shutdown();
    }

    public void addUrls(Collection<String> urls) {
        urls.stream().forEach(this::addLink_0);
    }

    public void addUrl(String url) {
        addLink_0(url);
    }

    private class SnoopDog implements Runnable {
        private String name;
        private PageFetcher fetcher;
        private PageIndexer indexer;

        public SnoopDog(String name) {
            this.name = name;
            fetcher = config.getPageFetcher();
            indexer = config.getIndexer();
        }

        public void snoop() throws InterruptedException {
            Link nextLink = getNextLink();
            logger.info("Taking url \"{}\" from the queue", nextLink);
            UnaryOperator<Link> linkHandler = config.getLinkHandler();
            Link link = linkHandler.apply(nextLink);
            if (config.geLinkFilter().test(link))
            try {
                PageResponse responseData = fetcher.fetch(link);
            } catch (IOException e) {
                logger.error("Unable to load page by link {}", link);
            }
//            Page page = fetcher.fetch(nextLink);
//            List<Link> links = page.getLinks();
//            addLinks(links);
//
//            indexer.index(page);
        }

        @Override
        public void run() {
            onStart();
            try {
                while (!Thread.interrupted()) {
                    snoop();
                }
            } catch (InterruptedException e) {
                afterInterrupt();
            } finally {
                beforeEnd();
            }
        }

        public void beforeEnd() {
            logger.error("SnoopDog {} died.", this);
            //Lazy by default
        }

        public void afterInterrupt() {
            logger.error("SnoopDog {} Interrupted.", this);
            //Lazy by default
        }

        public void onStart() {
            logger.error("SnoopDog {} started.", this);
            //Lazy by default
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private void addLinks(List<Link> links) {

    }

//    private class LinkFactory {
//        private int maxDepthOfCrawling;
//
//
//        public LinkFactory(SnooperConfig config) {
//            maxDepthOfCrawling = config.getMaxDepthOfCrawling();
//        }
//
//        public Link newLink(String url) {
////            URL url = null;
////            try {
////                url = new URL(urlString);
////            } catch (MalformedURLException e) {
////                e.printStackTrace();
////            }
//            return new Link(url, maxDepthOfCrawling);
//        }
//    }



}

