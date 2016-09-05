package ua.tifoha.crawler;

import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.crawler.PageResponse.Status.Family;
import ua.tifoha.util.UrlUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.tifoha.crawler.PageResponse.*;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class HttpPageFetcher extends Configurable<PageFetcherConfig> implements PageFetcher {
    private static final Logger logger = LoggerFactory.getLogger(PageFetcher.class);

    private PoolingHttpClientConnectionManager connectionManager;
    private CloseableHttpClient httpClient;
    private long lastFetchTime = 0;

    public HttpPageFetcher(PageFetcherConfig config) {
        super(config);

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setExpectContinueEnabled(false)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setRedirectsEnabled(false)
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectionTimeout());

        RequestConfig requestConfig = requestConfigBuilder.build();

        RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
        connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

        if (config.isIncludeHttpsPages()) {
            try {
                TrustStrategy trustStrategy = (chain, authType) -> true;
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                connRegistryBuilder.register("https", sslsf);
            } catch (Exception e) {
                logger.warn("Exception thrown while trying to register https");
                logger.debug("Stacktrace", e);
            }
        }

        Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
        connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
        connectionManager.setMaxTotal(config.getMaxTotalConnections());
        connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setConnectionManager(connectionManager);
        httpClient = clientBuilder.build();
    }

    @Override
    public PageResponse fetch(Link link) throws InterruptedException, IOException{
        // Getting URL, setting headers & content
        String linkUrl = link.getUrl();
        HttpUriRequest request = null;
        request = new HttpGet(linkUrl);

        // Applying request delay(anti ban)
        applyRequestDelay();

        try(CloseableHttpResponse response = httpClient.execute(request)) {
            // Setting HttpStatus
            Status status = Status.fromStatusCode(response.getStatusLine().getStatusCode());
            Map<String, String> headers = Stream.of(response.getAllHeaders())
                    .collect(Collectors.toMap(h -> h.getName().toUpperCase(), Header::getValue, (v1, v2) -> v2));
            InputStream content = null;
            HttpEntity entity = response.getEntity();

            if (status.getFamily() == Family.SUCCESSFUL) { // it looks everything OK
                String requestUrl = request.getURI().toString();
                if (!UrlUtils.isEquals(linkUrl, requestUrl)) {
                    logger.warn("Link url: {} & Response url {} not equals");
                    // TODO: 05.09.2016 handle situation
                }

                if (entity != null) {
                    // Checking maximum size
                    long size = entity.getContentLength();
                    if (size == -1) {
                        String length = headers.get("CONTENT-LENGTH");
                        if (length != null) {
                            size = Integer.parseInt(length);
                        }
                    }
                    if (size > config.getMaxContentSize()) {
                        response.close();
                        throw new DataFetcherException("Page bigger than max size " + size);
                    }

                    content = entity.getContent();
                }
            } else {
                // TODO: 05.09.2016 handle other statuses
            }

            return new PageResponse(status, headers, content);
        }
    }

    private void applyRequestDelay() throws InterruptedException {
        int requestDelay = config.getRequestDelay();
        if (requestDelay > 0) {
            synchronized (this) {
                long now = System.currentTimeMillis();
                if ((now - lastFetchTime) < requestDelay) {
                    logger.info("Applying request delay " + requestDelay);
                    Thread.sleep(requestDelay - (now - lastFetchTime));
                }
                lastFetchTime = System.currentTimeMillis();
            }
        }
    }

}
