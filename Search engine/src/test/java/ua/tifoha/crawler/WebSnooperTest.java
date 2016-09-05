package ua.tifoha.crawler;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vitaly on 05.09.2016.
 */
public class WebSnooperTest extends TestCase {
    private List<String> urls;
    private SnooperConfig config;

    public void setUp() throws Exception {
        super.setUp();
        config = new SnooperConfig();

        urls = new ArrayList<>();
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/cern-baldi-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Pedro-Domingos-web-thumb.jpg");
        urls.add("http://facebook.com/UCIBrenICS");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/facebook.png");
        urls.add("http://calendar.ics.uci.edu/calendar.php");
        urls.add("http://www.ics.uci.edu/bin/pdf/grad/F15-16%20Graduate%20Handbook.pdf");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/NCWIT-NEXT-AWARDS-thumbnail.jpg");
        urls.add("http://www.informatics.uci.edu/");
        urls.add("http://www.uci.edu/cgi-bin/phonebook");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/NickNguyen-CubeForme-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/RebeccaBlack-Web-thumb.jpg");
        urls.add("http://instagram.com/ucibrenics/");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Stembility-feature-April-2016-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/instagram.png");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/avanderh.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/sternh.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/gmark.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/ACMfellows-Dec2015-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/kobsa.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Jessica-Utts-International-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/zyBooks-feature-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/hziv.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/golson.jpg");
        urls.add("http://youtube.com/UCIBrenICS");
        urls.add("http://twitter.com/UCIbrenICS");
        urls.add("http://www.ics.uci.edu/bin/img/photos/noteworthy/GeneTsudik-noteworthy.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Lopes-November2014-4screens-squarethumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/walt-s-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Sharad-Mehrotra-NSF-photo-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/noteworthy/noteworthy-Darpa-Larsen.jpg");
        urls.add("http://www.cs.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/styles/stylesheet_secondary.css");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/GillianHayes-JacobsFoundation-thumb.jpg");
        urls.add("http://intranet.ics.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/youtube.png");
        urls.add("http://www.stat.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/hellman-Levorato-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Ian-Harris-robotics-2016-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/HomerStrong-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/babaks.jpg");
        urls.add("https://www.linkedin.com/company/uc-irvine-information-and-computer-sciences");
        urls.add("http://www.ics.uci.edu/bin/img/photos/noteworthy/noteworthy-AnkitaRaturi-2015.jpg");
        urls.add("http://www.youtube.com/UCIBrenICS");
        urls.add("http://www.uci.edu/copyright.php");
        urls.add("https://ucirvine-csm.symplicity.com/index.php/pid700104");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Dimitrios-Kotziaa-Yelp-Red-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/twitter.png");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Nick-Jonas-Raincheck-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/ZotFinder-Feature-Anne-Widney-thumb.jpg");
        urls.add("http://www.uadv.uci.edu/DonaldBrenSchoolOfICSAnnualGiving");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/linkedin.png");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Marios-Papaefthymiou-homepage-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/photos/faculty/franz.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/ICS-group-hall-of-fame-thumb.jpg");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/code-org-thumb.jpg");
        urls.add("http://www.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/img/features/thumbnail/Sajjad-Mustehsan-Profile-thumb.jpg");
        urls.add("https://scout.eee.uci.edu/s/ExcessUnits");
        urls.add("https://eee.uci.edu/survey/requestCSspecialization");
        urls.add("http://facebook.com/UCIBrenICS");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/facebook.png");
        urls.add("http://www.cs.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/styles/stylesheet_secondary.css");
        urls.add("http://calendar.ics.uci.edu/calendar.php");
        urls.add("http://www.ics.uci.edu/bin/pdf/grad/F15-16%20Graduate%20Handbook.pdf");
        urls.add("http://intranet.ics.uci.edu/");
        urls.add("http://www.informatics.uci.edu/");
        urls.add("http://www.uci.edu/cgi-bin/phonebook");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/youtube.png");
        urls.add("http://instagram.com/ucibrenics/");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/instagram.png");
        urls.add("http://www.stat.uci.edu/");
        urls.add("https://www.linkedin.com/company/uc-irvine-information-and-computer-sciences");
        urls.add("http://www.uci.edu/copyright.php");
        urls.add("https://ucirvine-csm.symplicity.com/index.php/pid700104");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/twitter.png");
        urls.add("http://www.uadv.uci.edu/DonaldBrenSchoolOfICSAnnualGiving");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/linkedin.png");
        urls.add("https://ugradforms.ics.uci.edu/");
        urls.add("http://www.uci.edu/");
        urls.add("http://youtube.com/UCIBrenICS");
        urls.add("http://twitter.com/UCIbrenICS");
        urls.add("http://www.informatics.uci.edu/grad/ms-informatics/");
        urls.add("http://www.informatics.uci.edu/grad/phd-software-engineering/");
        urls.add("http://catalogue.uci.edu/donaldbrenschoolofinformationandcomputersciences/");
        urls.add("http://catalogue.uci.edu/donaldbrenschoolofinformationandcomputersciences/departmentofinformatics/");
        urls.add("http://facebook.com/UCIBrenICS");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/facebook.png");
        urls.add("http://www.cs.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/styles/stylesheet_secondary.css");
        urls.add("http://calendar.ics.uci.edu/calendar.php");
        urls.add("http://www.ics.uci.edu/bin/pdf/grad/F15-16%20Graduate%20Handbook.pdf");
        urls.add("http://intranet.ics.uci.edu/");
        urls.add("http://www.informatics.uci.edu/");
        urls.add("http://www.networkedsystems.uci.edu/requirements09.html");
        urls.add("http://www.uci.edu/cgi-bin/phonebook");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/youtube.png");
        urls.add("http://instagram.com/ucibrenics/");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/instagram.png");
        urls.add("http://www.stat.uci.edu/");
        urls.add("http://www.ics.uci.edu/bin/img/photos/degrees/bmi_header.jpg");
        urls.add("https://www.linkedin.com/company/uc-irvine-information-and-computer-sciences");
        urls.add("http://catalogue.uci.edu/donaldbrenschoolofinformationandcomputersciences/departmentofcomputerscience/");
        urls.add("http://www.uci.edu/copyright.php");
        urls.add("https://ucirvine-csm.symplicity.com/index.php/pid700104");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/twitter.png");
        urls.add("http://www.informatics.uci.edu/grad/ms-software-engineering/");
        urls.add("http://www.uadv.uci.edu/DonaldBrenSchoolOfICSAnnualGiving");
        urls.add("http://www.ics.uci.edu/bin/img/logos/socialmedia/linkedin.png");
        urls.add("http://www.informatics.uci.edu/phd-informatics/");
        urls.add("http://www.uci.edu/");
        urls.add("http://youtube.com/UCIBrenICS");
        urls.add("http://twitter.com/UCIbrenICS");

    }

    public void testAddUrl() throws Exception {
        WebSnooper snooper = new WebSnooper(config);
        snooper.addUrl(urls.get(0));
        snooper.shutdown();
    }

    public void testAddUrls() throws Exception {
        WebSnooper snooper = new WebSnooper(config);
        snooper.addUrls(urls);
//        TimeUnit.SECONDS.sleep(2);
        snooper.shutdown();
    }

    public void testShutdown() throws Exception {

    }

    public void testIsShutdown() throws Exception {

    }

    public void testIsTerminated() throws Exception {

    }

    public void testAwaitTermination() throws Exception {

    }

    public void testShutdownNow() throws Exception {

    }

}