package ua.tifoha.mock;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import ua.tifoha.core.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vitaly on 04.09.2016.
 */
public class MockSearchResult implements SearchResult {
    private final String url;
    private final String title;
    private final String snippet;

    public MockSearchResult(String url) {
        this.url = url;
        try (InputStream input = new URL(url).openStream();) {
            ContentHandler textHandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(input, textHandler, metadata);
            this.title = metadata.get("title");
            this.snippet = textHandler.toString().substring(0, 500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MockSearchResult(String url, String title, String snippet) {
        this.url = url;
        this.title = title;
        this.snippet = snippet;
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
    public String getSnippet() {
        return snippet;
    }
}
