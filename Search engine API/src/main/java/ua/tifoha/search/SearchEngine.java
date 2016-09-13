package ua.tifoha.search;

import ua.tifoha.search.exception.SearchEngineException;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.indexer.crawler.Crawler;
import ua.tifoha.search.searcher.Searcher;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface SearchEngine extends Configurable<SearchEngineConfiguration>, AutoCloseable {
    Searcher getSearcher();

    Indexer getIndexer();

    Crawler getCrawler();

    @Override
    void close() throws SearchEngineException;
}
