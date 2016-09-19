package ua.tifoha.search.crawler;

import java.util.concurrent.atomic.AtomicLong;

public class BasicCrawlerInfo implements CrawlerInfo {
    private AtomicLong totalProcessedPages;
    private AtomicLong totalLinks;
    private AtomicLong totalDataSize;

    public BasicCrawlerInfo(long totalLinks, long totalDataSize, long totalProcessedPages) {
        this.totalLinks = new AtomicLong(totalLinks);
        this.totalProcessedPages = new AtomicLong(totalProcessedPages);
        this.totalDataSize = new AtomicLong(totalDataSize);
    }

    public BasicCrawlerInfo() {
        this(0, 0, 0);
    }

    @Override
    public long getTotalProcessedPages() {
        return totalProcessedPages.longValue();
    }

    public void setTotalProcessedPages(long totalProcessedPages) {
        this.totalProcessedPages.set(totalProcessedPages);
    }

    public void incProcessedPages() {
        this.totalProcessedPages.incrementAndGet();
    }

    @Override
    public long getTotalLinks() {
        return totalLinks.get();
    }

    public void setTotalLinks(long totalLinks) {
        this.totalLinks.set(totalLinks);
    }

    @Override
    public long getTotalDataSize() {
        return totalDataSize.get();
    }

    public void setTotalDataSize(long totalDataSize) {
        this.totalDataSize.set(totalDataSize);
    }

    public void incTotalLinks(long count) {
        this.totalLinks.addAndGet(count);
    }

    public void incTotalDataSize(long count) {
        this.totalDataSize.addAndGet(count);
    }

    @Override
    public void add(CrawlerInfo that) {
        this.totalProcessedPages.addAndGet(that.getTotalProcessedPages());
        this.totalLinks.addAndGet(that.getTotalLinks());
        this.totalDataSize.addAndGet(that.getTotalDataSize());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CrawlerStatusImpl{");
        sb.append("totalProcessedPages=").append(totalProcessedPages);
        sb.append(", totalLinks=").append(totalLinks);
        sb.append(", totalDataSize=").append(totalDataSize);
        sb.append('}');
        return sb.toString();
    }
}