package ua.tifoha.search.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.search.AbstractConfigurableClass;
import ua.tifoha.search.Field;
import ua.tifoha.search.LuceneSearchEngineEnvironment;
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.QueryParseException;
import ua.tifoha.search.exception.SearchEngineException;
import ua.tifoha.search.exception.SearcherClosingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ua.tifoha.search.Field.*;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneSearcher extends AbstractConfigurableClass<SearcherConfiguration> implements Searcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneSearcher.class);

    protected class SearchResultRowMapper implements Function<ScoreDoc, SearchResultRow> {
        private String contentFieldName = Field.CONTENT.name();
        private final Highlighter highlighter;
        private final IndexSearcher searcher;


        public SearchResultRowMapper(IndexSearcher searcher, Query luceneQuery, int fragmentLength) {
            this.searcher = searcher;
            QueryScorer queryScorer = new QueryScorer(luceneQuery, contentFieldName);
            Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer, fragmentLength);
            this.highlighter = new Highlighter(queryScorer); // Set the best scorer fragments
            this.highlighter.setTextFragmenter(fragmenter); // Set fragment to highlight
        }

        @Override
        public SearchResultRow apply(ScoreDoc scoreDoc) {
            try {
                Document doc = searcher.doc(scoreDoc.doc);
                String text = doc.get(contentFieldName);
                TokenStream tokenStream = TokenSources.getTokenStream(contentFieldName, text, analyzer);
                String fragment = highlighter.getBestFragment(tokenStream, text);
                BasicSearchResult.BasicSearchResultRow searchResultRow = new BasicSearchResult.BasicSearchResultRow();
                searchResultRow.setUrl(doc.get(Field.URL.name()));
                searchResultRow.setTitle(doc.get(Field.TITLE.name()));
                searchResultRow.setContent(fragment);
                return searchResultRow;
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            } catch (InvalidTokenOffsetsException e) {
                LOGGER.error(e.getMessage());
            }
            return null;
        }
    }

    private final Analyzer analyzer;
    private final SearcherManager searcherManager;

    public LuceneSearcher(LuceneSearchEngineEnvironment env) throws IndexDirectoryException {
        super(env.getConfig());
        this.analyzer = env.getAnalyzer();
        try {
            searcherManager = new SearcherManager(env.getIndexWriter(), new SearcherFactory());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IndexDirectoryException(e);
        }
    }

    @Override
    public SearchResult find(SearchQuery query) {
        int pageNumber = query.getPageNumber();
        int pageSize = query.getPageSize();
        int maxNumberOfResults = Math.min(config.getMaxNumberOfResults(), (pageNumber + 1) * pageSize);
        String queryString = query.getQueryString();
        IndexSearcher searcher = null;
        try {
            searcherManager.maybeRefresh();
            searcher = searcherManager.acquire();
            Query luceneQuery = getQuery(queryString);


            TopScoreDocCollector collector = TopScoreDocCollector.create(maxNumberOfResults);
            searcher.search(luceneQuery, collector);

            int startIndex = pageNumber * pageSize;
            TopDocs topDocs = collector.topDocs(startIndex, pageSize);
            Function<ScoreDoc, SearchResultRow> mapper = new SearchResultRowMapper(searcher, luceneQuery, query.getFragmentLength());

            List<SearchResultRow> resultRows = Arrays.stream(topDocs.scoreDocs)
                    .map(mapper).filter(row -> row != null)
                    .collect(Collectors.toList());
            return new BasicSearchResult(pageNumber, pageSize, collector.getTotalHits(), resultRows);

        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            throw new QueryParseException(e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IndexDirectoryException(e);
        } finally {
            if (searcher != null) {
                try {
                    searcherManager.release(searcher);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    throw new SearchEngineException();
                }
            }
        }
    }

    private Query getQuery(String queryString) throws ParseException {
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        queryBuilder.add(new QueryParser(URL.toString(), analyzer).parse(queryString), BooleanClause.Occur.SHOULD);
        queryBuilder.add(new QueryParser(TITLE.toString(), analyzer).parse(queryString), BooleanClause.Occur.SHOULD);
        queryBuilder.add(new QueryParser(CONTENT.toString(), analyzer).parse(queryString), BooleanClause.Occur.SHOULD);
        return queryBuilder.build();
    }

    public void setConfig(SearcherConfiguration config) {

    }

    @Override
    public void close() throws SearcherClosingException {
        LOGGER.info("Closing searcher...");
        try {
            LOGGER.info("Closing searcher manager...");
            searcherManager.close();
            LOGGER.info("Index reader closed.");
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
        LOGGER.info("Searcher closed.");
    }
}
