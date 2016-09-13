package ua.tifoha.search.indexer;

import ua.tifoha.search.indexer.crawler.Link;
import ua.tifoha.search.indexer.crawler.WebPage;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class BasicCrawlRequest implements CrawlRequest {
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
    public static final BiPredicate<WebPage, Link> DEFAULT_PAGE_FILTER = ONLY_HTML_FILTER.and(ONLY_ONE_DOMAIN);
//    public static final Consumer<CrawlerInfo> DEFAULT_CALLBACK = crawlerStatus -> {};

    private final String url;
    private int maxDepthOfCrawling = -1;
    private int maxPagesToFetch = -1;
    private int maxOutgoingLinksToFollow = 5000;
    private BiPredicate<WebPage, Link> pageFilter = DEFAULT_PAGE_FILTER;
//    private Consumer<CrawlerInfo> callback = DEFAULT_CALLBACK;

    public BasicCrawlRequest(String url) {
        Objects.requireNonNull(url);
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
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
    public int getMaxOutgoingLinksToFollow() {
        return maxOutgoingLinksToFollow;
    }

    public void setMaxOutgoingLinksToFollow(int maxOutgoingLinksToFollow) {
        this.maxOutgoingLinksToFollow = maxOutgoingLinksToFollow;
    }

    @Override
    public BiPredicate<WebPage, Link> getPageFilter() {
        return pageFilter;
    }

    public void setPageFilter(BiPredicate<WebPage, Link> pageFilter) {
        Objects.requireNonNull(pageFilter);
        this.pageFilter = pageFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicCrawlRequest)) return false;

        BasicCrawlRequest that = (BasicCrawlRequest) o;

        if (maxDepthOfCrawling != that.maxDepthOfCrawling) return false;
        if (maxPagesToFetch != that.maxPagesToFetch) return false;
        if (maxOutgoingLinksToFollow != that.maxOutgoingLinksToFollow) return false;
        return url.equals(that.url);

    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + maxDepthOfCrawling;
        result = 31 * result + maxPagesToFetch;
        result = 31 * result + maxOutgoingLinksToFollow;
        return result;
    }

//    public void setCallback(Consumer<CrawlerInfo> callback) {
//        Objects.requireNonNull(callback);
//        this.callback = callback;
//    }
}
