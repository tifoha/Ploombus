package ua.tifoha.search;

import ua.tifoha.search.indexer.IndexerConfiguration;
import ua.tifoha.search.indexer.crawler.CrawlerConfiguration;
import ua.tifoha.search.searcher.SearcherConfiguration;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface SearchEngineConfiguration extends Configuration, CrawlerConfiguration, IndexerConfiguration, SearcherConfiguration {
    String getIndexDirectoryPath();
}
