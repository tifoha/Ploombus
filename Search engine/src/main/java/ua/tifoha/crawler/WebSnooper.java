package ua.tifoha.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.util.UrlUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Vitaly on 04.09.2016.
 */
public class WebSnooper extends Configurable<SnooperConfig> implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(WebSnooper.class);

    private Set<String> visitedUrls = new ConcurrentSkipListSet<>();
    private BlockingDeque<String> urlsToVisit = new LinkedBlockingDeque<>();
    private ExecutorService exec;

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

    @Override
    public SnooperConfig getConfig() {
        return config;
    }

    private void addUrl_0(String url) {
        try {
            String canonicalUrl = UrlUtils.getCanonicalURL(url);
            if (canonicalUrl == null) {
                logger.error("Invalid URL: {}", url);
            } else {
                this.urlsToVisit.put(canonicalUrl);
            }
        } catch (InterruptedException e) {
            logger.error("Unable to add URL: {}", url);
        }
    }

    private String getNextUrl() throws InterruptedException {
        String nextUrl;
        do {
            nextUrl = urlsToVisit.take();
        } while (this.visitedUrls.contains(nextUrl));
        visitedUrls.add(nextUrl);

        return nextUrl;
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
        return exec.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        logger.info("Awaiting termination...");
        return exec.awaitTermination(timeout, unit);
    }

    public List<String> shutdownNow() {
        exec.shutdownNow();
        logger.info("Shutdown now");
        return new ArrayList<>(urlsToVisit);
    }


    //    public void crawl(String url) {
//        while (this.visitedUrls.size() <= config.getMaxPagesToFetch()) {
//            String currentUrl;
//
//            PageFetcher fetcher = new PageFetcher();
//            if (this.urlsToVisit.isEmpty()) {
//                currentUrl = url;
//                this.visitedUrls.add(currentUrl);
//            } else {
////                currentUrl = getNextUrl()
////                        new ForkJoinPool().
//            }
//        }
//    }

//    private synchronized void waitForUrl() {
//        long waitTime = config.getDelayBeforeEnding();
//        if (waitTime > 0) {
//            try {
//                wait(waitTime);
//
//                //aborting crawler
//            } catch (InterruptedException e) {
//                e.printStackTrace();
////                todo handle exception and store state
//            }
//        }
//    }

    @Override
    public void close() throws Exception {
        exec.shutdown();
    }

    public void addUrls(Collection<String> urls) {
        urls.stream().forEach(this::addUrl_0);
    }

    public void addUrl(String url) {
        addUrl_0(url);
    }

    private class SnoopDog implements Runnable {
        private String name;

        public SnoopDog(String name) {
            this.name = name;
        }

        public void snoop() throws InterruptedException {
            String nextUrl = getNextUrl();
            logger.info("Taking url \"{}\" from the queue", nextUrl);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    snoop();
                }
            } catch (InterruptedException e) {
                logger.error("SnoopDog {} Interrupted.", this);
                //// TODO: 05.09.2016 handle interruption of the snoop dog
            } finally {
                logger.error("SnoopDog {} died.", this);
                //// TODO: 05.09.2016 finalizing snoop dog
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

