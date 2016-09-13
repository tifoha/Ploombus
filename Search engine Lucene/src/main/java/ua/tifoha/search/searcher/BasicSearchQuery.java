package ua.tifoha.search.searcher;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class BasicSearchQuery implements SearchQuery {
    private final String queryString;
    private final int pageNumber;
    private final int pageSize;

    public BasicSearchQuery(String queryString) {
        this(queryString, 0, 100);
    }

    public BasicSearchQuery(String queryString, int pageSize) {
        this(queryString, 0, pageSize);
    }

    public BasicSearchQuery(String queryString, int pageNumber, int pageSize) {
        this.queryString = queryString;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
