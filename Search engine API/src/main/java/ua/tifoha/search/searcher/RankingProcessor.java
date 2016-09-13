package ua.tifoha.search.searcher;

/**
 * Created by Vitaly on 11.09.2016.
 */
public interface RankingProcessor {
    public SearchResult rank(SearchResult unrankedResultSet);
}
