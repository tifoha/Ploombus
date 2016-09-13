package ua.tifoha.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.FSDirectory;
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.indexer.LuceneIndexer;
import ua.tifoha.search.indexer.crawler.crawler4j.MultiCrawlerControllerDelegate;
import ua.tifoha.search.searcher.LuceneSearcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class TestSearchEngineImpl extends LuceneSearchEngine {
    public TestSearchEngineImpl(SearchEngineConfiguration config) {
        super(config);

        Path indexDirectoryPath = Paths.get(config.getIndexDirectoryPath());
        try {
            directory = FSDirectory.open(indexDirectoryPath);
        } catch (IOException e) {
            throw new IndexDirectoryException(e);
        }

        analyzer = new StandardAnalyzer();
        indexer = new LuceneIndexer(config, directory, analyzer);
        searcher = new LuceneSearcher(config, directory, analyzer);
        crawler = new MultiCrawlerControllerDelegate(config, indexer);
    }
}
