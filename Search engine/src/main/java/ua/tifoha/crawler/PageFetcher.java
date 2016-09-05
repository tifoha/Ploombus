package ua.tifoha.crawler;

import java.io.IOException;

/**
 * Created by Vitaly on 05.09.2016.
 */
public interface PageFetcher<T extends PageResponse> {
    T fetch(Link link) throws InterruptedException, IOException;
}
