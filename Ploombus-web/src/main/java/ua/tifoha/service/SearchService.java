package ua.tifoha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tifoha.search.searcher.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vitaly on 12.09.2016.
 */
@Service
public class SearchService {
    @Autowired
    private Searcher searcher;

    public SearchResult find(String queryString) {
        SearchResult result = searcher.find(new BasicSearchQuery(queryString));
        return result;
    }

    public SearchResult find(SearchQuery searchQuery) {
        SearchResult result = searcher.find(searchQuery);
        return result;
    }
}
