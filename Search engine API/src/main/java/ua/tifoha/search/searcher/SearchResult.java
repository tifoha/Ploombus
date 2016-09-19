package ua.tifoha.search.searcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface SearchResult {
    Collection<SearchResultRow> getRows();

    Stream<SearchResultRow> getRowStream();

    int getPageNumber();

    int getPageSize();

    int getTotalPages();

    int getTotalHits();
}
