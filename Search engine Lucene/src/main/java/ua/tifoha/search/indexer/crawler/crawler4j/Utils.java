package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.apache.commons.beanutils.BeanUtils;
import ua.tifoha.search.indexer.crawler.CrawlerConfiguration;

public class Utils {
    public static CrawlConfig convert(CrawlerConfiguration crawlerConfiguration) {
        CrawlConfig config = new CrawlConfig();
        try {
            BeanUtils.copyProperties(config, crawlerConfiguration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        config.setProcessBinaryContentInCrawling(crawlerConfiguration.isIncludeBinaryContent());
        config.setIncludeBinaryContentInCrawling(crawlerConfiguration.isIncludeBinaryContent());

        return config;
    }
}
