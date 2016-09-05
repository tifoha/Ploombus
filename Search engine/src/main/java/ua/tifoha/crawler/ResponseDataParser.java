package ua.tifoha.crawler;

/**
 * Created by Vitaly on 06.09.2016.
 */
public interface ResponseDataParser {
    Page parse(PageResponse responseData);
}
