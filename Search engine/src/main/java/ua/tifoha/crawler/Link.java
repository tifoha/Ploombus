package ua.tifoha.crawler;

import ua.tifoha.util.UrlUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class Link {
    private String url;
    private Link parent;
    private int depth;
//    private String domain;
//    private String subDomain;

    public Link(String url, int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Url should be greater than -1");
        }

        this.url = url;


        this.depth = depth;
//        domain = url.getHost();
//        subDomain = "";
//        String[] parts = domain.split("\\.", 2);
//        if (parts.length == 2) {
//            domain = parts[1];
//            subDomain = parts[0];
//        }
//        path = url.getPath();
    }

//    public Link(String urlString, int depth) {
//        this(toUrl(urlString), depth);
//
//    }

    public Link(Link parent, String path) {
        this(UrlUtils.getCanonicalURL(parent.getUrl(), path), parent.depth - 1);
        this.parent = parent;
    }

    private static URL toUrl(URL context, String path) {
        if (path == null) {
            throw new IllegalArgumentException("Url shouldn't be null");
        }

        try {
            return new URL(context, path);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Incorrect url " + path);
        }
    }

    private static URL toUrl(String urlString) {
        if (urlString == null) {
            throw new IllegalArgumentException("Url shouldn't be null");
        }

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Incorrect url " + urlString);
        }
    }

    public Link subLink(String url) {
        return new Link(this, url);
    }

    public String getUrl() {
        return url;
    }
    public String getAddress() {
        return url.toString();
    }

    public Link getParent() {
        return parent;
    }

    /**
     * @return crawl depth at which this Url is first observed. Seed Urls
     * are at depth 0. Urls that are extracted from seed Urls are at depth 1, etc.
     */
    public int getDepth() {
        return depth;
    }

//    public String getDomain() {
//        return domain;
//    }
//
//    public String getSubDomain() {
//        return subDomain;
//    }

//    /**
//     * @return path of this Url. For 'http://www.example.com/sample.htm?param1=4&param2=asdfa', domain will be 'sample.htm'
//     */
//    public String getPath() {
//        return url.getPath();
//    }

//    public String getProtocol() {
//        return url.getProtocol();
//    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }

        Link that = (Link) o;
        return url.equals(that.url);

    }

    @Override
    public String toString() {
        return "(" + url.toString() + ", " + depth + ")";
    }

//    public URI getUri() {
//        try {
//            return url.toURI();
//        } catch (URISyntaxException e) {
//            // TODO: 05.09.2016 handle exception
//            throw new RuntimeException(e);
//        }
//    }

}
