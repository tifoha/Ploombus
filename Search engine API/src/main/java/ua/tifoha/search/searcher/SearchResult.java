package ua.tifoha.search.searcher;

import java.util.List;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface SearchResult extends Iterable<SearchResultRow> {
    List<SearchResultRow> getPortion(int pageNumber, int pageSize);

    List<SearchResultRow> getAll();

    int size();
}
