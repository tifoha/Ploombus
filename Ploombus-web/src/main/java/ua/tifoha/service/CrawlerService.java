package ua.tifoha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tifoha.search.crawler.crawler4j.MultiCrawlerControllerDelegate;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.indexer.CrawlRequest;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.crawler.CrawlResponse;
import ua.tifoha.search.crawler.Crawler;

import java.util.List;

/**
 * Created by Vitaly on 13.09.2016.
 */
@Service
public class CrawlerService {
    @Autowired
    private MultiCrawlerControllerDelegate crawler;

    public void index(String url) {
        CrawlResponse response = crawler.addSeed(new BasicCrawlRequest(url));
    }

    public List<CrawlResponse> getAllIndexTasks() {
        List<CrawlResponse> taskList = crawler.getCrawlerResponseList();
        return taskList;
    }


    public void shutdown(CrawlRequest request) {
        MultiCrawlerControllerDelegate multiCrawlerControllerDelegate = crawler;
        multiCrawlerControllerDelegate.shutdown(request);
    }

    public CrawlResponse index(CrawlRequest crawlRequest) {
        return crawler.addSeed(crawlRequest);
    }
}
