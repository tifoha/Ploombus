package ua.tifoha.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.SearchEngineException;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.indexer.crawler.Crawler;
import ua.tifoha.search.searcher.Searcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneSearchEngine extends AbstractConfigurableClass<SearchEngineConfiguration> implements SearchEngine {
    protected Directory directory;
    protected Analyzer analyzer;
    protected Indexer indexer;
    protected Searcher searcher;
    protected Crawler crawler;

    public LuceneSearchEngine(SearchEngineConfiguration config) {
        super(config);

        Path indexDirectoryPath = Paths.get(config.getIndexDirectoryPath());
        try {
            directory = FSDirectory.open(indexDirectoryPath);
        } catch (IOException e) {
            throw new IndexDirectoryException(e);
        }

        analyzer = new StandardAnalyzer();
    }

    @Override
    public Searcher getSearcher() {
        return searcher;
    }

    @Override
    public Indexer getIndexer() {
        return indexer;
    }

    @Override
    public Crawler getCrawler() {
        return crawler;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    @Override
    public void close() throws SearchEngineException{
        crawler.close();
        analyzer.close();
        indexer.close();
        searcher.close();
        try {
            directory.close();
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
    }

    public Directory openNewIndexDirectory(String path) {
        Path indexDirectoryPath = Paths.get(path);
        try {
            return FSDirectory.open(indexDirectoryPath);
        } catch (IOException e) {
            throw new IndexDirectoryException(e);
        }
    }

    public Analyzer createNewAnalyzer() {
        return new StandardAnalyzer();
    }
}
