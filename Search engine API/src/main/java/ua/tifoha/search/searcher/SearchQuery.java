package ua.tifoha.search.searcher;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface SearchQuery {
    String getQueryString();

    int getPageNumber();

    int getPageSize();

    int getFragmentLength();
}
