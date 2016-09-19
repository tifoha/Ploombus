package ua.tifoha.search.crawler;

import ua.tifoha.search.Configuration;

import java.util.function.BiPredicate;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface CrawlerConfiguration extends Configuration, Cloneable {
    String getCrawlStorageFolder();

    boolean isResumableCrawling();

    int getMaxDepthOfCrawling();

    int getMaxPagesToFetch();

    String getUserAgentString();

    int getPolitenessDelay();

    boolean isIncludeHttpsPages();

    boolean isIncludeBinaryContent();

    int getMaxConnectionsPerHost();

    int getMaxTotalConnections();

    int getSocketTimeout();

    int getConnectionTimeout();

    int getMaxOutgoingLinksToFollow();

    int getMaxDownloadSize();

    boolean isFollowRedirects();

    boolean isOnlineTldListUpdate();

    String getProxyHost();

    int getProxyPort();

    String getProxyUsername();

    String getProxyPassword();

    boolean isEnabledRobotstxt();

    String getUserAgentNameRobotstxt();

    int getCacheSizeRobotstxt();

    int getNumberOfCralwers();

    BiPredicate<WebPage, Link> getPageFilter();

    int getDelayBeforeShutdownCrawl();
}
