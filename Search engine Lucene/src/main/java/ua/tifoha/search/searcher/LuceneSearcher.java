package ua.tifoha.search.searcher;

import org.apache.lucene.analysis.Analyzer;
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
import ua.tifoha.search.exception.IndexDirectoryException;
import ua.tifoha.search.exception.QueryParseException;
import ua.tifoha.search.exception.SearchEngineException;
import ua.tifoha.search.exception.SearcherClosingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.tifoha.search.Field.*;

/**
 * Created by Vitaly on 11.09.2016.
 */
public class LuceneSearcher extends AbstractConfigurableClass<SearcherConfiguration> implements Searcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneSearcher.class);

    private final Analyzer analyzer;
    private final Directory directory;
    private final IndexReader indexReader;

    public LuceneSearcher(SearcherConfiguration config, Directory directory, Analyzer analyzer) throws IndexDirectoryException {
        super(config);
        this.directory = directory;
        this.analyzer = analyzer;
        try {
            indexReader = DirectoryReader.open(directory);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            throw new IndexDirectoryException(e);
        }
    }

    //    @Override
//    public List<SearchResultImpl> findAll(SearchQuery query) {
//        List<SearchResultImpl> result = find(query, 0, config.getMaxNumberOfResults());
//        return result;
//    }

    @Override
    public SearchResult find(SearchQuery query) {
        List<SearchResultRow> result = new ArrayList<>(config.getMaxNumberOfResults());
        String queryString = query.getQueryString();
        try {
            Query luceneQuery = getQuery(queryString);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            String field = Field.CONTENT.toString();
            QueryScorer queryScorer = new QueryScorer(luceneQuery, field);
            Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer, 500);
            Highlighter highlighter = new Highlighter(queryScorer); // Set the best scorer fragments
            highlighter.setTextFragmenter(fragmenter); // Set fragment to highlight

            TopScoreDocCollector collector = TopScoreDocCollector.create(config.getMaxNumberOfResults());
            searcher.search(luceneQuery, collector);

//            int start = collector.getTotalHits() / query.getPageSize() * query.getPageNumber();
//            TopDocs topDocs = collector.topDocs(start, query.getPageSize());

//            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
//                Document doc = searcher.doc(scoreDoc.doc);
//                String text = doc.get(field);
//                TokenStream tokenStream = TokenSources.getTokenStream(field, text, analyzer);
//                String fragment = highlighter.getBestFragment(tokenStream, text);
//                BasicSearchResultRow searchResult = new BasicSearchResultRow(doc);
//                searchResult.setContent(fragment);
//                result.add(searchResult);
//            }

            return new BasicSearchResult(searcher, collector, highlighter, analyzer);
        } catch (ParseException e) {
            LOGGER.error(e.toString());
            throw new QueryParseException(e);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            throw new IndexDirectoryException(e);
//        } catch (InvalidTokenOffsetsException e) {
//            LOGGER.error(e.toString());
//            throw new SearchEngineException(e);
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
    public void close() throws SearcherClosingException{
        LOGGER.info("Closing searcher...");
        try {
            LOGGER.info("Closing index reader...");
            indexReader.close();
            LOGGER.info("Index reader closed.");
        } catch (IOException e) {
            throw new SearchEngineException(e);
        }
        LOGGER.info("Searcher closed.");
    }
}
