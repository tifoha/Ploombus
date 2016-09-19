package ua.tifoha.search.indexer;

import ua.tifoha.search.Configuration;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface IndexerConfiguration extends Configuration {
    long getMaxTimeBeforeTermination();

    int getAutoCommitDocCount();
}
