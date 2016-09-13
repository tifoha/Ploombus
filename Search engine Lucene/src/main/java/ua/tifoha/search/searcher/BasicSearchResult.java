package ua.tifoha.search.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.TokenSources;
import ua.tifoha.search.exception.SearchEngineException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Spliterator.SIZED;
import static ua.tifoha.search.Field.CONTENT;
import static ua.tifoha.search.Field.TITLE;
import static ua.tifoha.search.Field.URL;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class BasicSearchResult implements SearchResult {
    private final TopScoreDocCollector collector;
    private final IndexSearcher searcher;
    private final Highlighter highlighter;
    private final Analyzer analyzer;

    public BasicSearchResult(IndexSearcher searcher, TopScoreDocCollector collector, Highlighter highlighter, Analyzer analyzer) {
        this.searcher = searcher;
        this.collector = collector;
        this.highlighter = highlighter;
        this.analyzer = analyzer;
    }

    @Override
    public List<SearchResultRow> getPortion(int pageNumber, int pageSize) {
        int start = collector.getTotalHits() / pageSize * pageNumber;
        List<SearchResultRow> result = getSearchResults(start, pageSize);

        return result;
    }

    @Override
    public List<SearchResultRow> getAll() {
        List<SearchResultRow> result = getSearchResults(0, collector.getTotalHits());

        return result;
    }

    @Override
    public int size() {
        return collector.getTotalHits();
    }

    private List<SearchResultRow> getSearchResults(int start, int howMany) {
        TopDocs topDocs = collector.topDocs(start, howMany);
//        Function<ScoreDoc, SearchResulRow> resultMapper =

        return Arrays.stream(topDocs.scoreDocs).map(this::getSearchRow).collect(Collectors.toList());
    }

    private SearchResultRow getSearchRow(ScoreDoc scoreDoc) {
        try {
            Document doc = searcher.doc(scoreDoc.doc);
            return new BasicSearchResultRow(doc);
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
    }

    @Override
    public Iterator<SearchResultRow> iterator() {
        return new Iterator<SearchResultRow>() {
            int index;

            @Override
            public boolean hasNext() {
                return index < collector.getTotalHits();
            }

            @Override
            public SearchResultRow next() {
                ScoreDoc scoreDoc = collector.topDocs().scoreDocs[index];
                return getSearchRow(scoreDoc);
            }
        };
    }

    @Override
    public Spliterator<SearchResultRow> spliterator() {
        return Spliterators.spliterator(iterator(), collector.getTotalHits(), SIZED);
    }

    /**
     * Created by Vitaly on 11.09.2016.
     */
    private class BasicSearchResultRow implements SearchResultRow {
        private String url;
        private String title;
        private String content;

        public BasicSearchResultRow(String url, String title, String content) {
            this.url = url;
            this.title = title;
            this.content = content;
        }

        public BasicSearchResultRow(Document doc) {
            url = doc.get(URL.name());
            title = doc.get(TITLE.name());
            String text = doc.get(CONTENT.name());
            TokenStream tokenStream = TokenSources.getTokenStream(CONTENT.name(), text, analyzer);
            try {
                content = highlighter.getBestFragment(tokenStream, text);
            } catch (Exception e) {
                content = text;
            }
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
