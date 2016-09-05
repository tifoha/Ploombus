package ua.tifoha.util;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class UrlUtilsTest {

    @Test
    public void testGetCanonicalURL1() {

        assertEquals("http://www.example.com/display?category=foo%2Fbar%2Bbaz",
                UrlUtils.getCanonicalURL("http://www.example.com/display?category=foo/bar+baz"));

        assertEquals("http://www.example.com/?q=a%2Bb", UrlUtils.getCanonicalURL("http://www.example.com/?q=a+b"));

        assertEquals("http://www.example.com/display?category=foo%2Fbar%2Bbaz",
                UrlUtils.getCanonicalURL("http://www.example.com/display?category=foo%2Fbar%2Bbaz"));

        assertEquals("http://somedomain.com/uploads/1/0/2/5/10259653/6199347.jpg?1325154037", UrlUtils
                .getCanonicalURL("http://somedomain.com/uploads/1/0/2/5/10259653/6199347.jpg?1325154037"));

        assertEquals("http://hostname.com/", UrlUtils.getCanonicalURL("http://hostname.com"));

        assertEquals("http://hostname.com/", UrlUtils.getCanonicalURL("http://HOSTNAME.com"));

        assertEquals("http://www.example.com/index.html",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?&"));

        assertEquals("http://www.example.com/index.html",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?"));

        assertEquals("http://www.example.com/", UrlUtils.getCanonicalURL("http://www.example.com"));

        assertEquals("http://www.example.com/bar.html",
                UrlUtils.getCanonicalURL("http://www.example.com:80/bar.html"));

        assertEquals("http://www.example.com/index.html?name=test&rame=base",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?name=test&rame=base#123"));

        assertEquals("http://www.example.com/~username/",
                UrlUtils.getCanonicalURL("http://www.example.com/%7Eusername/"));

        assertEquals("http://www.example.com/A/B/index.html",
                UrlUtils.getCanonicalURL("http://www.example.com//A//B/index.html"));

        assertEquals("http://www.example.com/index.html?x=y",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?&x=y"));

        assertEquals("http://www.example.com/a.html",
                UrlUtils.getCanonicalURL("http://www.example.com/../../a.html"));

        assertEquals("http://www.example.com/a/c/d.html",
                UrlUtils.getCanonicalURL("http://www.example.com/../a/b/../c/./d.html"));

        assertEquals("http://foo.bar.com/?baz=1", UrlUtils.getCanonicalURL("http://foo.bar.com?baz=1"));

        assertEquals("http://www.example.com/index.html?c=d&e=f&a=b",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?&c=d&e=f&a=b"));

        assertEquals("http://www.example.com/index.html?q=a%20b",
                UrlUtils.getCanonicalURL("http://www.example.com/index.html?q=a b"));

        assertEquals("http://www.example.com/search?width=100%&height=100%",
                UrlUtils.getCanonicalURL("http://www.example.com/search?width=100%&height=100%"));
    }

    @Test
    public void testGetCanonicalURL2() {
        assertEquals("http://foo.bar/mydir/myfile?page=2",
                UrlUtils.getCanonicalURL("?page=2", "http://foo.bar/mydir/myfile"));

    }

    @Test
    public void testIsEquals() {
        assertTrue(UrlUtils.isEquals("http://www.example.com/a/c/d.html", "http://www.example.com/../a/b/../c/./d.html"));
        assertTrue(UrlUtils.isEquals("http://www.example.com/", "http://www.example.com"));
    }
}