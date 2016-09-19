package ua.tifoha.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.SearchEngineException;
import ua.tifoha.search.indexer.Indexer;
import ua.tifoha.search.crawler.Crawler;
import ua.tifoha.search.indexer.LuceneIndexer;
import ua.tifoha.search.searcher.Searcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneSearchEngineEnvironment extends AbstractConfigurableClass<SearchEngineConfiguration> implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneSearchEngineEnvironment.class);

    protected final IndexWriter indexWriter;
    protected Directory directory;
    protected Analyzer analyzer;

    public LuceneSearchEngineEnvironment(SearchEngineConfiguration config) {
        super(config);

        Path indexDirectoryPath = Paths.get(config.getIndexDirectoryPath());
        try {
            this.analyzer = new StandardAnalyzer();
            this.directory = FSDirectory.open(indexDirectoryPath);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(this.analyzer);
            this.indexWriter = new IndexWriter(this.directory, indexWriterConfig);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IndexDirectoryException(e);
        }

        analyzer = new StandardAnalyzer();
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

    @Override
    public void close() throws SearchEngineException{
        analyzer.close();
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

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }
}
