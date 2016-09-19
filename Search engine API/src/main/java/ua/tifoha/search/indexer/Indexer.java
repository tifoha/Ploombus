package ua.tifoha.search.indexer;

import ua.tifoha.search.Configurable;
import ua.tifoha.search.exception.IndexerClosingException;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface Indexer extends Configurable<IndexerConfiguration>, AutoCloseable {
    void indexAsync(IndexerDocument request);

    void index(IndexerDocument request);

    void commit();

    @Override
    void close() throws IndexerClosingException;

}
