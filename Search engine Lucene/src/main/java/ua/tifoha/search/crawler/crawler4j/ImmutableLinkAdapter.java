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

package ua.tifoha.search.crawler.crawler4j;

import edu.uci.ics.crawler4j.url.WebURL;
import ua.tifoha.search.crawler.Link;

public class ImmutableLinkAdapter implements Link {

  private final Link parent;
  private final WebURL webURL;

  public ImmutableLinkAdapter(WebURL webURL) {
    this.webURL = webURL;
    this.parent = webURL.getParentUrl() != null ? new ImmutableLinkAdapter(webURL.getParentUrl()):null;
  }


  /**
   * @return unique document url assigned to this Url.
   */
  @Override
  public Link getParent() {
    return parent;
  }

  /**
   * @return Url string
   */
  @Override
  public String getURL() {
    return webURL.getURL();
  }

  private ImmutableLinkAdapter(String url) {
    this.webURL = new WebURL();
    webURL.setURL(url);
    parent = null;
  }

  /**
   * @return
   *      crawl depth at which this Url is first observed. Seed Urls
   *      are at depth 0. Urls that are extracted from seed Urls are at depth 1, etc.
   */
  @Override
  public short getDepth() {
    return webURL.getDepth();
  }

  /**
   * @return
   *      domain of this Url. For 'http://www.example.com/sample.htm', domain will be 'example.com'
   */
  @Override
  public String getDomain() {
    return webURL.getDomain();
  }

  @Override
  public String getSubDomain() {
    return webURL.getSubDomain();
  }

  /**
   * @return
   *      path of this Url. For 'http://www.example.com/sample.htm', domain will be 'sample.htm'
   */
  @Override
  public String getPath() {
    return webURL.getPath();
  }


  /**
   * @return
   *      anchor string. For example, in <a href="example.com">A sample anchor</a>
   *      the anchor string is 'A sample anchor'
   */
  @Override
  public String getAnchor() {
    return webURL.getAnchor();
  }


  /**
   * @return priority for crawling this URL. A lower number results in higher priority.
   */
  @Override
  public byte getPriority() {
    return webURL.getPriority();
  }


  /**
   * @return tag in which this URL is found
   * */
  @Override
  public String getTag() {
    return webURL.getTag();
  }


  @Override
  public int hashCode() {
    return webURL.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }

    ImmutableLinkAdapter otherUrl = (ImmutableLinkAdapter) o;
    return (webURL != null) && webURL.equals(otherUrl.webURL);

  }

  @Override
  public String toString() {
    return webURL.getURL();
  }
}