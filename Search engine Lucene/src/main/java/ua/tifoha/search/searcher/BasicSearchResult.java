package ua.tifoha.search.searcher;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class BasicSearchResult implements SearchResult {
    private final int pageNumber;
    private final int pageSize;
    private final int totalHits;
    private final int totalPages;
    private final Collection<SearchResultRow> resultRows;

    public BasicSearchResult(int pageNumber, int pageSize, int totalHits, Collection<SearchResultRow> resultRows) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalHits = totalHits;
        this.totalPages = (int) Math.ceil((double) totalHits / pageSize);
        this.resultRows = new ArrayList<>(resultRows);
    }

    @Override
    public Collection<SearchResultRow> getRows() {
        return resultRows;
    }

    @Override
    public Stream<SearchResultRow> getRowStream() {
        return resultRows.stream();
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
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getTotalHits() {
        return totalHits;
    }

    public static class BasicSearchResultRow implements SearchResultRow {
        private String url;
        private String title;
        private String content;

        public BasicSearchResultRow() {
        }

        public BasicSearchResultRow(String url, String title, String content) {
            this.url = url;
            this.title = title;
            this.content = content;
        }

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getContent() {
            return content;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder()
                    .append("link='").append(url).append('\'')
                    .append('\n')
                    .append(", title='").append(title).append('\'')
                    .append('\n')
                    .append(", content='").append(content).append('\'');

            return sb.toString();
        }
    }
}
