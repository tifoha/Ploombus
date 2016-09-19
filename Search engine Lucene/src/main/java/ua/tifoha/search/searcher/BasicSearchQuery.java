package ua.tifoha.search.searcher;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class BasicSearchQuery implements SearchQuery {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_FRAGMENT_LENGTH = 200;

    private String queryString;
    private int pageNumber = DEFAULT_PAGE_NUMBER;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int fragmentLength = DEFAULT_FRAGMENT_LENGTH;

    public BasicSearchQuery() {
    }

    public BasicSearchQuery(String queryString) {
        this.queryString = queryString;
    }

    public BasicSearchQuery(String queryString, int pageNumber, int pageSize, int fragmentLength) {
        this(queryString);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.fragmentLength = fragmentLength;
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

    @Override
    public int getFragmentLength() {
        return fragmentLength;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setFragmentLength(int fragmentLength) {
        this.fragmentLength = fragmentLength;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
