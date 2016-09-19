package ua.tifoha.search;

import ua.tifoha.search.exception.CrawlerException;
import ua.tifoha.search.crawler.Link;
import ua.tifoha.search.crawler.WebPage;

import java.util.function.BiPredicate;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class BasicConfiguration implements SearchEngineConfiguration {
    ///////////////////////////////////////////////SEARCH ENGINE CONFIG///////////////////////////////////////////////////////////
    //path to index DB
    private String indexDirectoryPath = "ploombus/indexes";

    ///////////////////////////////////////////////SEARCHER CONFIG////////////////////////////////////////////////////////////////
    //restriction to max result count
    private int maxNumberOfResults = 1000;

    ///////////////////////////////////////////////CRAWLER CONFIG/////////////////////////////////////////////////////////////////
    /**
     * The folder which will be used by crawler for storing the intermediate
     * crawl data. The content of this folder should not be modified manually.
     */
    private String crawlStorageFolder = "ploombus/crawlers";

    /**
     * The number of concurrent threads which will be used by crawler for crawling the web
     */
    private int numberOfCralwers = Runtime.getRuntime().availableProcessors();

    /**
     * If this feature is enabled, you would be able to resume a previously
     * cancelled/crashed crawl. However, it makes crawling slightly slower
     */
    private boolean resumableCrawling = false;

    /**
     * Maximum depth of crawling For unlimited depth this parameter should be
     * set to -1
     */
    private int maxDepthOfCrawling = -1;

    /**
     * Maximum number of pages to fetch For unlimited number of pages, this
     * parameter should be set to -1
     */
    private int maxPagesToFetch = -1;

    /**
     * user-agent string that is used for representing your crawler to web
     * servers. See http://en.wikipedia.org/wiki/User_agent for more details
     */
    private String userAgentString = "Ploombus Crawler4j Bot";

    /**
     * Politeness delay in milliseconds (delay between sending two requests to
     * the same host).
     */
    private int politenessDelay = 200;

    /**
     * Should we also crawl https pages?
     */
    private boolean includeHttpsPages = true;

    /**
     * Should we fetch and process binary content such as images, audio, ...using TIKA?
     */
    private boolean includeBinaryContent = false;

    /**
     * Maximum Connections per host
     */
    private int maxConnectionsPerHost = 100;

    /**
     * Maximum total connections
     */
    private int maxTotalConnections = 100;

    /**
     * Socket timeout in milliseconds
     */
    private int socketTimeout = 20000;

    /**
     * Connection timeout in milliseconds
     */
    private int connectionTimeout = 30000;

    /**
     * Max number of outgoing links which are processed from a page
     */
    private int maxOutgoingLinksToFollow = 5000;

    /**
     * Max allowed size of a page. Pages/Binary content larger than this size will not be
     * fetched.
     */
    private int maxDownloadSize = 1048576;

    /**
     * Should we follow redirects?
     */
    private boolean followRedirects = true;

    /**
     * Should the TLD list be updated automatically on each run? Alternatively,
     * it can be loaded from the embedded tld-names.zip file that was obtained from
     * https://publicsuffix.org/list/effective_tld_names.dat
     */
    private boolean onlineTldListUpdate = false;

    /**
     * If crawler should run behind a proxy, this parameter can be used for
     * specifying the proxy host.
     */
    private String proxyHost = null;

    /**
     * If crawler should run behind a proxy, this parameter can be used for
     * specifying the proxy port.
     */
    private int proxyPort = 80;

    /**
     * If crawler should run behind a proxy and user/pass is needed for
     * authentication in proxy, this parameter can be used for specifying the
     * username.
     */
    private String proxyUsername = null;

    /**
     * If crawler should run behind a proxy and user/pass is needed for
     * authentication in proxy, this parameter can be used for specifying the
     * password.
     */
    private String proxyPassword = null;

    // filtering pages
    private BiPredicate<WebPage, Link> pageFilter = (webPageAdapter, linkAdapter) -> true;


///////////////////////////////////////////////////////////////////////////////////////////////////////
//    RobotstxtConfig

    /**
     * Should the crawler obey Robots.txt protocol? More info on Robots.txt is
     * available at http://www.robotstxt.org/
     */
    private boolean enabledRobotstxt = true;

    /**
     * user-agent name that will be used to determine whether some servers have
     * specific rules for this agent name.
     */
    private String userAgentNameRobotstxt = userAgentString;

    /**
     * The maximum number of hosts for which their robots.txt is cached.
     */
    private int cacheSizeRobotstxt = 500;

    // if crawl queue is empty, wait few seconds before shutdown crawler controller in milliseconds
    private int delayBeforeShutdownCrawl = 1000;

    /////////////////////////////////////////////////////////INDEXER CONFIG////////////////////////////////////////////////////
    private long maxTimeBeforeTermination = 3000;

    //when should commit changes
    private int autoCommitDocCount = 200;

    @Override
    public void validate() {
        if (crawlStorageFolder == null) {
            throw new CrawlerException("Crawl storage folder is not set in the CrawlConfig.");
        }
        if (politenessDelay < 0) {
            throw new CrawlerException("Invalid value for politeness delay: " + politenessDelay);
        }
        if (maxDepthOfCrawling < -1) {
            throw new CrawlerException("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
        }
        if (maxDepthOfCrawling > Short.MAX_VALUE) {
            throw new CrawlerException("Maximum value for crawl depth is " + Short.MAX_VALUE);
        }
        if (numberOfCralwers < 1) {
            throw new CrawlerException("Minimum value for number of crawlers is 1");
        }
    }

    @Override
    public String getCrawlStorageFolder() {
        return crawlStorageFolder;
    }

    public void setCrawlStorageFolder(String crawlStorageFolder) {
        this.crawlStorageFolder = crawlStorageFolder;
    }

    @Override
    public boolean isResumableCrawling() {
        return resumableCrawling;
    }

    public void setResumableCrawling(boolean resumableCrawling) {
        this.resumableCrawling = resumableCrawling;
    }

    @Override
    public int getMaxDepthOfCrawling() {
        return maxDepthOfCrawling;
    }

    public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        this.maxDepthOfCrawling = maxDepthOfCrawling;
    }

    @Override
    public int getMaxPagesToFetch() {
        return maxPagesToFetch;
    }

    public void setMaxPagesToFetch(int maxPagesToFetch) {
        this.maxPagesToFetch = maxPagesToFetch;
    }

    @Override
    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    @Override
    public int getPolitenessDelay() {
        return politenessDelay;
    }

    public void setPolitenessDelay(int politenessDelay) {
        this.politenessDelay = politenessDelay;
    }

    @Override
    public boolean isIncludeHttpsPages() {
        return includeHttpsPages;
    }

    public void setIncludeHttpsPages(boolean includeHttpsPages) {
        this.includeHttpsPages = includeHttpsPages;
    }

    @Override
    public boolean isIncludeBinaryContent() {
        return includeBinaryContent;
    }

    public void setIncludeBinaryContent(boolean includeBinaryContent) {
        this.includeBinaryContent = includeBinaryContent;
    }

    @Override
    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    @Override
    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    @Override
    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public int getMaxOutgoingLinksToFollow() {
        return maxOutgoingLinksToFollow;
    }

    public void setMaxOutgoingLinksToFollow(int maxOutgoingLinksToFollow) {
        this.maxOutgoingLinksToFollow = maxOutgoingLinksToFollow;
    }

    @Override
    public int getMaxDownloadSize() {
        return maxDownloadSize;
    }

    public void setMaxDownloadSize(int maxDownloadSize) {
        this.maxDownloadSize = maxDownloadSize;
    }

    @Override
    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    @Override
    public boolean isOnlineTldListUpdate() {
        return onlineTldListUpdate;
    }

    public void setOnlineTldListUpdate(boolean onlineTldListUpdate) {
        this.onlineTldListUpdate = onlineTldListUpdate;
    }

    @Override
    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    @Override
    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    @Override
    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    @Override
    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    @Override
    public boolean isEnabledRobotstxt() {
        return enabledRobotstxt;
    }

    public void setEnabledRobotstxt(boolean enabledRobotstxt) {
        this.enabledRobotstxt = enabledRobotstxt;
    }

    @Override
    public String getUserAgentNameRobotstxt() {
        return userAgentNameRobotstxt;
    }

    public void setUserAgentNameRobotstxt(String userAgentNameRobotstxt) {
        this.userAgentNameRobotstxt = userAgentNameRobotstxt;
    }

    @Override
    public int getCacheSizeRobotstxt() {
        return cacheSizeRobotstxt;
    }

    public void setCacheSizeRobotstxt(int cacheSizeRobotstxt) {
        this.cacheSizeRobotstxt = cacheSizeRobotstxt;
    }

    @Override
    public int getNumberOfCralwers() {
        return numberOfCralwers;
    }

    public void setNumberOfCralwers(int numberOfCralwers) {
        this.numberOfCralwers = numberOfCralwers;
    }

    @Override
    public BiPredicate<WebPage, Link> getPageFilter() {
        return pageFilter;
    }

    public void setPageFilter(BiPredicate<WebPage, Link> pageFilter) {
        this.pageFilter = pageFilter;
    }


    public int getDelayBeforeShutdownCrawl() {
        return delayBeforeShutdownCrawl;
    }

    public void setDelayBeforeShutdownCrawl(int delayBeforeShutdownCrawl) {
        this.delayBeforeShutdownCrawl = delayBeforeShutdownCrawl;
    }

    @Override
    public long getMaxTimeBeforeTermination() {
        return maxTimeBeforeTermination;
    }

    @Override
    public int getAutoCommitDocCount() {
        return autoCommitDocCount;
    }

    public void setAutoCommitDocCount(int autoCommitDocCount) {
        this.autoCommitDocCount = autoCommitDocCount;
    }

    public void setMaxTimeBeforeTermination(long maxTimeBeforeTermination) {
        this.maxTimeBeforeTermination = maxTimeBeforeTermination;
    }

    @Override
    public String getIndexDirectoryPath() {
        return indexDirectoryPath;
    }

    public void setIndexDirectoryPath(String indexDirectoryPath) {
        this.indexDirectoryPath = indexDirectoryPath;
    }

    @Override
    public int getMaxNumberOfResults() {
        return maxNumberOfResults;
    }

    public void setMaxNumberOfResults(int maxNumberOfResults) {
        this.maxNumberOfResults = maxNumberOfResults;
    }
}
