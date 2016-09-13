/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.tifoha.search.indexer.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.ParseData;
import org.apache.http.Header;
import ua.tifoha.search.indexer.crawler.Link;
import ua.tifoha.search.indexer.crawler.WebPage;

public class ImmutableWebPageAdapter implements WebPage {

  private final Page page;
  private final Status status;
  private final Link link;


  public ImmutableWebPageAdapter(Page page) {
    this.page = page;
    this.status = Status.fromStatusCode(page.getStatusCode());
    this.link = new ImmutableLinkAdapter(page.getWebURL());
  }

  @Override
  public Link getLink() {
    return new ImmutableLinkAdapter(page.getWebURL());
  }


  @Override
  public boolean isRedirect() {
    return page.isRedirect();
  }


  @Override
  public String getRedirectedToUrl() {
    return page.getRedirectedToUrl();
  }


  @Override
  public Status getStatus() {
    return status;
  }


  /**
   * Returns headers which were present in the response of the fetch request
   *
   * @return Header Array, the response headers
   */
  public Header[] getFetchResponseHeaders() {
    return page.getFetchResponseHeaders();
  }


  /**
   * @return parsed data generated for this page by parsers
   */
  public ParseData getParseData() {
    return page.getParseData();
  }


  /**
   * @return content of this page in binary format.
   */
  @Override
  public byte[] getContentData() {
    return page.getContentData();
  }


  /**
   * @return ContentType of this page.
   * For example: "text/html; charset=UTF-8"
   */
  @Override
  public String getContentType() {
    return page.getContentType();
  }


  /**
   * @return encoding of the content.
   * For example: "gzip"
   */
  @Override
  public String getContentEncoding() {
    return page.getContentEncoding();
  }


  /**
   * @return charset of the content.
   * For example: "UTF-8"
   */
  @Override
  public String getContentCharset() {
    return page.getContentCharset();
  }


  /**
   * @return Language
   */
  @Override
  public String getLanguage() {
    return page.getLanguage();
  }

}