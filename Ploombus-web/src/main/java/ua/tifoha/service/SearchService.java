package ua.tifoha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tifoha.search.searcher.BasicSearchQuery;
import ua.tifoha.search.searcher.SearchResultRow;
import ua.tifoha.search.searcher.SearchResult;
import ua.tifoha.search.searcher.Searcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaly on 12.09.2016.
 */
@Service
public class SearchService {
    @Autowired
    private Searcher searcher;

    public List<SearchResultRow> find(String queryString) {
        List<SearchResultRow> resulRowList = new ArrayList<>();
        SearchResult result = searcher.find(new BasicSearchQuery(queryString));
        resulRowList = result.getAll();
        return resulRowList;
    }
}
