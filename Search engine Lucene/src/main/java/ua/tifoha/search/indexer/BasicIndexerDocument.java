package ua.tifoha.search.indexer;

/**
 * Created by Vitaly on 12.09.2016.
 */
public class BasicIndexerDocument implements IndexerDocument {
    private final String url;
    private final String title;
    private final String text;

    public BasicIndexerDocument(String url, String title, String text) {
        this.url = url;
        this.title = title;
        this.text = text;
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
    public String getText() {
        return text;
    }
}
