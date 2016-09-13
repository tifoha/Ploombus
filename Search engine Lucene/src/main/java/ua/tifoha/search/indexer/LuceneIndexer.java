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
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.IndexerClosingException;
import ua.tifoha.search.exception.IndexerException;
import ua.tifoha.search.util.AbstractWorker;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static ua.tifoha.search.Field.*;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneIndexer extends AbstractConfigurableClass<IndexerConfiguration> implements Indexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneIndexer.class);
    private static final Runnable DEFAULT_CALLBACK = () -> {};

    private final Analyzer analyzer;
    private final Directory directory;
    private final IndexWriter indexWriter;
    private final ExecutorService indexTaskExecutor = Executors.newSingleThreadExecutor();

    public LuceneIndexer(IndexerConfiguration config, Directory directory, Analyzer analyzer) throws IndexDirectoryException {
        super(config);
        this.analyzer = analyzer;
        this.directory = directory;

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(this.analyzer);
        try {
            indexWriter = new IndexWriter(this.directory, indexWriterConfig);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            throw new IndexDirectoryException(e);
        }
    }

    @Override
    public IndexerResponse indexAsync(IndexerDocument request) {
        LuceneIndexWorker luceneIndexWorker = new LuceneIndexWorker(request);
        indexTaskExecutor.submit(luceneIndexWorker);

        return luceneIndexWorker;
    }

    @Override
    public void index(IndexerDocument request) {
        Document doc = convertToDocument(request);
        try {
            indexWriter.addDocument(doc);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            throw new IndexerException(e);
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

            } catch (InterruptedException e) {

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

    @Override
    public void flush() {
        try {
            indexWriter.flush();
        } catch (IOException e) {
            throw new IndexerException(e);
        }
    }

    private Document convertToDocument(IndexerDocument request) {
        Document doc = new Document();
        // use a string field for course_code because we don't want it tokenized
        doc.add(new StringField(URL.name(), request.getUrl(), Field.Store.YES));
        doc.add(new TextField(TITLE.name(), request.getTitle(), Field.Store.YES));
        doc.add(new TextField(CONTENT.name(), request.getText(), Field.Store.YES));

        return doc;
    }

    private class LuceneIndexWorker extends AbstractWorker implements IndexerResponse<Object> {
        private final Object RESULT = new Object();
        private final Runnable callback;
        private final IndexerDocument request;
        private final ReentrantLock lock = new ReentrantLock();

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
        public void doWork() throws InterruptedException, Exception {
            lock.lock();
            try {
                Document doc = convertToDocument(request);
                indexWriter.addDocument(doc);
                indexWriter.commit();
                LOGGER.info("Page \"{}\" was successfully indexed", request.getUrl());
                stop();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void beforeEnd() {
            super.beforeEnd();
            callback.run();
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            stop();
            return true;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            lock.lock();
            try {
                return RESULT;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            try {
                if (!lock.tryLock(timeout, unit)) {
                    throw new TimeoutException();
                }
                return RESULT;
            } finally {
                lock.unlock();
            }
        }
    }
}
