package ua.tifoha.core;

import java.util.List;

/**
 * Created by Vitaly on 04.09.2016.
 */
public interface SearchEngine {
    List<SearchResult> find(String queryString);

    void index(IndexCandidate candidate);
}
