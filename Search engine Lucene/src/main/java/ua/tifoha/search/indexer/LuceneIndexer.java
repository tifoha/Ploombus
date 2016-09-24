package ua.tifoha.search.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.AbstractConfigurableClass;
import ua.tifoha.search.LuceneSearchEngineEnvironment;
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.IndexerClosingException;
import ua.tifoha.search.exception.IndexerException;
import ua.tifoha.search.util.AbstractWorker;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static ua.tifoha.search.Field.*;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneIndexer extends AbstractConfigurableClass<IndexerConfiguration> implements Indexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneIndexer.class);
    private static final Runnable DEFAULT_CALLBACK = () -> {};

    private final CyclicCommitter committer;
    private final IndexWriter indexWriter;
    private final ExecutorService indexTaskExecutor = Executors.newCachedThreadPool();

    public LuceneIndexer(LuceneSearchEngineEnvironment env) throws IndexDirectoryException {
        super(env.getConfig());
        committer = new CyclicCommitter(config.getAutoCommitDocCount());
        this.indexWriter = env.getIndexWriter();
    }

    @Override
    public void indexAsync(IndexerDocument request) {
        LuceneIndexWorker luceneIndexWorker = new LuceneIndexWorker(request);
        indexTaskExecutor.submit(luceneIndexWorker);
    }

    @Override
    public void index(IndexerDocument request) {
        Document doc = convertToDocument(request);
        try {
            indexWriter.addDocument(doc);
            committer.arrive();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IndexerException(e);
        }
    }

    @Override
    public void commit() {
        try {
            indexWriter.commit();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void close() throws IndexerClosingException {
        LOGGER.info("Closing indexer...");
        LOGGER.info("Shutdown task executor...");
        indexTaskExecutor.shutdown();
        if (!indexTaskExecutor.isTerminated()) {
            try {
                LOGGER.info("Waiting {}mil until aborting...", config.getMaxTimeBeforeTermination());
                indexTaskExecutor.awaitTermination(config.getMaxTimeBeforeTermination(), TimeUnit.MILLISECONDS);

            } catch (InterruptedException ignore) {

            } finally {
                List<Runnable> tasks = indexTaskExecutor.shutdownNow();
                LOGGER.error("Aborting all({}) tasks!", tasks.size());
            }
        }
        LOGGER.info("Crawler tasks finished.");


        try {
            LOGGER.info("Closing index writer...");
            indexWriter.close();
            LOGGER.info("Index writer closed.");
        } catch (IOException e) {
            throw new IndexerClosingException(e);
        }
        LOGGER.info("Indexer closed.");
    }

    private Document convertToDocument(IndexerDocument request) {
        Document doc = new Document();
        // use a string field for course_code because we don't want it tokenized
        doc.add(new StringField(URL.name(), request.getUrl(), Field.Store.YES));
        doc.add(new TextField(TITLE.name(), request.getTitle(), Field.Store.YES));
        doc.add(new TextField(CONTENT.name(), request.getText(), Field.Store.YES));

        return doc;
    }

    protected class LuceneIndexWorker extends AbstractWorker {
        private final Runnable callback;
        private final IndexerDocument request;

        public LuceneIndexWorker(String name, IndexerDocument request, Runnable callback) {
            super(name);
            Objects.requireNonNull(callback);
            this.callback = callback;
            this.request = request;
        }

        public LuceneIndexWorker(IndexerDocument request, Runnable callback) {
            this(request.getUrl(), request, callback);
        }

        public LuceneIndexWorker(IndexerDocument request) {
            this(request, DEFAULT_CALLBACK);
        }

        @Override
        public void doWork() throws Exception {
            index(request);
            LOGGER.info("Page \"{}\" was successfully added to index", request.getUrl());
            stop();
        }

        @Override
        public void beforeEnd() {
            super.beforeEnd();
            callback.run();
        }

    }

    protected class CyclicCommitter extends Phaser {
        public CyclicCommitter(int minDocCount) {
            super(minDocCount);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            commit();
            LOGGER.info("{} documents successfully committed.", registeredParties);
            return false;
        }
    }
}
