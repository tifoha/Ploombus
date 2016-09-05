package ua.tifoha.mock;

import org.springframework.stereotype.Service;
import ua.tifoha.core.IndexCandidate;
import ua.tifoha.core.SearchEngine;
import ua.tifoha.core.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaly on 04.09.2016.
 */
@Service
public class MockSearchEngine implements SearchEngine{
    private List<SearchResult> mockResults;

    public MockSearchEngine() {
        mockResults = new ArrayList<>();
        mockResults.add(new MockSearchResult("https://www.jetbrains.com/help/idea/2016.2/configuring-behavior-of-the-editor-tabs.html"));
        mockResults.add(new MockSearchResult("http://forum.spring.io/forum/spring-projects/web/58269-how-to-access-the-current-locale-in-jstl-view"));
        mockResults.add(new MockSearchResult("http://www.tutorialspoint.com/tika/tika_content_extraction.htm"));
        mockResults.add(new MockSearchResult("http://www.cubeia.com/firebase-from-scratch-pt-iii-a-maven-interlude/"));
    }

    @Override
    public List<SearchResult> find(String queryString) {
        return new ArrayList<>(mockResults);
    }

    @Override
    public void index(IndexCandidate candidate) {

    }

    public static void main(String[] args) {
        new MockSearchEngine();
    }
}
