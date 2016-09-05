package ua.tifoha;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.xml.sax.SAXException;

public class LinksExtractor {

    public static void main(String[] args) {


        try {
            String address = JOptionPane.showInputDialog("Web page address:");
            if (address != null)
                extractLinks(address, System.getProperty("user.dir") + "/links.txt");

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public static void extractLinks(String src, String desc) throws IOException, SAXException, TikaException {
//        BufferedWriter bw = null;

//        try {
            //create stream read to read data from the url
            InputStream f = TikaInputStream.get(new URL(src).openStream());
            //create buffered writer to write extracted links to the file
//            bw = new BufferedWriter(new FileWriter(desc));
            //create metadata to store the metadata of the web page
            Metadata meta = new Metadata();
            //create linkcontent hander to store the extracted links
            LinkContentHandler linkhandler = new LinkContentHandler();
            //create a parser to parse the web page file
            AutoDetectParser parser = new AutoDetectParser();
            //now parse the web page
            parser.parse(f, linkhandler, meta);
            //get links from the handler and write them to the file
            List<Link> links = linkhandler.getLinks();
            Iterator<Link> i = links.iterator();
            while (i.hasNext()) {
                String str = i.next().toString();
                System.out.println(i.next().getUri());
//                bw.write(str);
//                bw.newLine();
            }
//        }
        //close the buffered writer
//        finally {
//            bw.flush();
//            bw.close();
//        }


    }


}