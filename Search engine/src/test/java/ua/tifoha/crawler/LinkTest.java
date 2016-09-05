package ua.tifoha.crawler;

import junit.framework.TestCase;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class LinkTest extends TestCase {
    public void testOf() throws Exception {
        Link link = new Link("https://translate.google.com.ua/extension?hl=ru&tab=wT&authuser=0#en/ru/greater", 5);
//        assertEquals("https", link.getProtocol());
//        assertEquals("translate", link.getSubDomain());
//        assertEquals("google.com.ua", link.getDomain());
//        assertEquals("/extension", link.getPath());
        assertNull(link.getParent());
    }

    public void testSubLinkRelative() throws Exception {
        Link parentLink = new Link("https://translate.google.com.ua/extention?hl=ru&tab=wT&authuser=0#en/ru/greater", 5);
        Link childLink = parentLink.subLink("/node?q=sdfsadf");
//        assertEquals("https", childLink.getProtocol());
//        assertEquals("translate", childLink.getSubDomain());
//        assertEquals("google.com.ua", childLink.getDomain());
//        assertEquals("/node", childLink.getPath());
        assertEquals(parentLink, childLink.getParent());
        assertEquals(parentLink.getDepth() - 1, childLink.getDepth());
    }

    public void testSubLinkAbsoluteWithDomain() throws Exception {
        Link parentLink = new Link("https://translate.google.com.ua/extention?hl=ru&tab=wT&authuser=0#en/ru/greater", 5);
        Link childLink = parentLink.subLink("http://ex.ua/node?q=sdfsadf");
        Link newLink = new Link("http://ex.ua/node?q=sdfsadf", 4);
        assertEquals(parentLink, childLink.getParent());
        assertEquals(newLink, childLink);

    }
}