package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import ua.tifoha.search.indexer.crawler.CrawlerConfiguration;

public class ImmutableCrawlConfigAdapter extends CrawlConfig {
    private final CrawlerConfiguration config;

    ImmutableCrawlConfigAdapter(CrawlerConfiguration config) {
        this.config = config;
    }

    @Override
    public void validate() throws Exception {
        config.validate();
    }

    @Override
    public String getCrawlStorageFolder() {
        return config.getCrawlStorageFolder();
    }

    @Override
    public void setCrawlStorageFolder(String crawlStorageFolder) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isResumableCrawling() {
        return config.isResumableCrawling();
    }

    @Override
    public void setResumableCrawling(boolean resumableCrawling) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxDepthOfCrawling() {
        return config.getMaxDepthOfCrawling();
    }

    @Override
    public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxPagesToFetch() {
        return config.getMaxPagesToFetch();
    }

    @Override
    public void setMaxPagesToFetch(int maxPagesToFetch) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public String getUserAgentString() {
        return config.getUserAgentString();
    }

    @Override
    public void setUserAgentString(String userAgentString) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getPolitenessDelay() {
        return config.getPolitenessDelay();
    }

    @Override
    public void setPolitenessDelay(int politenessDelay) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isIncludeHttpsPages() {
        return config.isIncludeHttpsPages();
    }

    @Override
    public void setIncludeHttpsPages(boolean includeHttpsPages) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isIncludeBinaryContentInCrawling() {
        return config.isIncludeBinaryContent();
    }

    @Override
    public void setIncludeBinaryContentInCrawling(boolean includeBinaryContentInCrawling) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isProcessBinaryContentInCrawling() {
        return config.isIncludeBinaryContent();
    }

    @Override
    public void setProcessBinaryContentInCrawling(boolean processBinaryContentInCrawling) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxConnectionsPerHost() {
        return config.getMaxConnectionsPerHost();
    }

    @Override
    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxTotalConnections() {
        return config.getMaxTotalConnections();
    }

    @Override
    public void setMaxTotalConnections(int maxTotalConnections) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getSocketTimeout() {
        return config.getSocketTimeout();
    }

    @Override
    public void setSocketTimeout(int socketTimeout) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getConnectionTimeout() {
        return config.getConnectionTimeout();
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxOutgoingLinksToFollow() {
        return config.getMaxOutgoingLinksToFollow();
    }

    @Override
    public void setMaxOutgoingLinksToFollow(int maxOutgoingLinksToFollow) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getMaxDownloadSize() {
        return config.getMaxDownloadSize();
    }

    @Override
    public void setMaxDownloadSize(int maxDownloadSize) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isFollowRedirects() {
        return config.isFollowRedirects();
    }

    @Override
    public void setFollowRedirects(boolean followRedirects) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isShutdownOnEmptyQueue() {
        return false;
    }

    @Override
    public void setShutdownOnEmptyQueue(boolean shutdown) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public boolean isOnlineTldListUpdate() {
        return config.isOnlineTldListUpdate();
    }

    @Override
    public void setOnlineTldListUpdate(boolean online) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public String getProxyHost() {
        return config.getProxyHost();
    }

    @Override
    public void setProxyHost(String proxyHost) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public int getProxyPort() {
        return config.getProxyPort();
    }

    @Override
    public void setProxyPort(int proxyPort) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public String getProxyUsername() {
        return config.getProxyUsername();
    }

    @Override
    public void setProxyUsername(String proxyUsername) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }

    @Override
    public String getProxyPassword() {
        return config.getProxyPassword();
    }

    @Override
    public void setProxyPassword(String proxyPassword) {
        throw new UnsupportedOperationException("This CrawlConfigAdapter is immutable");
    }
}
