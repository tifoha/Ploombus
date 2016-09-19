package ua.tifoha.search.searcher;

import ua.tifoha.search.Configurable;
import ua.tifoha.search.exception.SearcherClosingException;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface Searcher extends Configurable<SearcherConfiguration>, AutoCloseable {
    SearchResult find(SearchQuery query);

    @Override
    void close() throws SearcherClosingException;
}
