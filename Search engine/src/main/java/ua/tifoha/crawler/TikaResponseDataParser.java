package ua.tifoha.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vitaly on 06.09.2016.
 */
public class TikaResponseDataParser extends Configurable<ResponseDataParserConfig> implements ResponseDataParser {
    public TikaResponseDataParser(ResponseDataParserConfig config) {
        super(config);
    }

    protected static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private final HtmlParser htmlParser;
    private final ParseContext parseContext;

    public Parser(CrawlConfig config) {
        super(config);
        htmlParser = new HtmlParser();
        parseContext = new ParseContext();
    }

    public void parse(Page page, String contextURL) throws NotAllowedContentException, ParseException {
        if (Util.hasBinaryContent(page.getContentType())) { // BINARY
            BinaryParseData parseData = new BinaryParseData();
            if (config.isIncludeBinaryContentInCrawling()) {
                if (config.isProcessBinaryContentInCrawling()) {
                    parseData.setBinaryContent(page.getContentData());
                } else {
                    parseData.setHtml("<html></html>");
                }
                page.setParseData(parseData);
                if (parseData.getHtml() == null) {
                    throw new ParseException();
                }
                parseData.setOutgoingUrls(Net.extractUrls(parseData.getHtml()));
            } else {
                throw new NotAllowedContentException();
            }
        } else if (Util.hasPlainTextContent(page.getContentType())) { // plain Text
            try {
                TextParseData parseData = new TextParseData();
                if (page.getContentCharset() == null) {
                    parseData.setTextContent(new String(page.getContentData()));
                } else {
                    parseData.setTextContent(new String(page.getContentData(), page.getContentCharset()));
                }
                parseData.setOutgoingUrls(Net.extractUrls(parseData.getTextContent()));
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException();
            }
        } else { // isHTML
            Metadata metadata = new Metadata();
            HtmlContentHandler contentHandler = new HtmlContentHandler();
            try (InputStream inputStream = new ByteArrayInputStream(page.getContentData())) {
                htmlParser.parse(inputStream, contentHandler, metadata, parseContext);
            } catch (Exception e) {
                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException();
            }

            if (page.getContentCharset() == null) {
                page.setContentCharset(metadata.get("Content-Encoding"));
            }

            HtmlParseData parseData = new HtmlParseData();
            parseData.setText(contentHandler.getBodyText().trim());
            parseData.setTitle(metadata.get(DublinCore.TITLE));
            parseData.setMetaTags(contentHandler.getMetaTags());
            // Please note that identifying language takes less than 10 milliseconds
            LanguageIdentifier languageIdentifier = new LanguageIdentifier(parseData.getText());
            page.setLanguage(languageIdentifier.getLanguage());

            Set<WebURL> outgoingUrls = new HashSet<>();

            String baseURL = contentHandler.getBaseUrl();
            if (baseURL != null) {
                contextURL = baseURL;
            }

            int urlCount = 0;
            for (ExtractedUrlAnchorPair urlAnchorPair : contentHandler.getOutgoingUrls()) {

                String href = urlAnchorPair.getHref();
                if ((href == null) || href.trim().isEmpty()) {
                    continue;
                }

                String hrefLoweredCase = href.trim().toLowerCase();
                if (!hrefLoweredCase.contains("javascript:") && !hrefLoweredCase.contains("mailto:") &&
                        !hrefLoweredCase.contains("@")) {
                    String url = URLCanonicalizer.getCanonicalURL(href, contextURL);
                    if (url != null) {
                        WebURL webURL = new WebURL();
                        webURL.setURL(url);
                        webURL.setTag(urlAnchorPair.getTag());
                        webURL.setAnchor(urlAnchorPair.getAnchor());
                        outgoingUrls.add(webURL);
                        urlCount++;
                        if (urlCount > config.getMaxOutgoingLinksToFollow()) {
                            break;
                        }
                    }
                }
            }
            parseData.setOutgoingUrls(outgoingUrls);

            try {
                if (page.getContentCharset() == null) {
                    parseData.setHtml(new String(page.getContentData()));
                } else {
                    parseData.setHtml(new String(page.getContentData(), page.getContentCharset()));
                }

                page.setParseData(parseData);
            } catch (UnsupportedEncodingException e) {
                logger.error("error parsing the html: " + page.getWebURL().getURL(), e);
                throw new ParseException();
            }
        }
    }

    @Override
    public Page parse(PageResponse responseData) {
        return null;
    }
}
