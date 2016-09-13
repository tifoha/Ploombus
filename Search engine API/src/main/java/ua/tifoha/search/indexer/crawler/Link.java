package ua.tifoha.search.indexer.crawler;

/**
 * Created by Vitaly on 12.09.2016.
 */
public interface Link {
    Link getParent();

    String getURL();

    short getDepth();

    String getDomain();

    String getSubDomain();

    String getPath();

    String getAnchor();

    byte getPriority();

    String getTag();
}
