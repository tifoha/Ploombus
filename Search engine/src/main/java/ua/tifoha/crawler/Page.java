package ua.tifoha.crawler;

import java.util.List;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class Page {
    private List<Link> externalLinks;
    private Link pageLink;
    private String tittle;

    public List<Link> getExternalLinks() {
        return externalLinks;
    }

    public Link getPageLink() {
        return pageLink;
    }

    public String getTittle() {
        return tittle;
    }
}
