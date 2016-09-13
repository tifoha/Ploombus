package ua.tifoha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.indexer.CrawlRequest;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.indexer.crawler.CrawlResponse;
import ua.tifoha.search.indexer.crawler.Crawler;

import java.util.List;

/**
 * Created by Vitaly on 13.09.2016.
 */
@Service
public class IndexService {
    @Autowired
    private Indexer indexer;

    @Autowired
    private Crawler crawler;

    public void index(String url) {
        CrawlResponse response = crawler.addSeed(new BasicCrawlRequest(url));
    }

    public List<CrawlResponse> getAllIndexTasks() {
        List<CrawlResponse> taskList = crawler.getCrawlerResponseList();
        return taskList;
    }

    public CrawlResponse index(CrawlRequest crawlRequest) {
        return crawler.addSeed(crawlRequest);
    }
}
