package ua.tifoha;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.langdetect.TextLangDetector;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Vitaly on 03.09.2016.
 */
public class App {
    public static void main(String[] args) throws IOException, TikaException, SAXException, URISyntaxException {
//        Tika tika = new Tika();
//        try (InputStream is = new FileInputStream("D:\\Java\\TT\\Ploombus\\Ploombus-web\\src\\main\\resources\\Тестовое Задание (Google Clone).docx")){
//            String s = tika.parseToString(is);
//            System.out.println(s);
//        }
        String fileName = "D:\\Java\\TT\\Ploombus\\Ploombus-web\\src\\main\\resources\\Тестовое Задание (Google Clone).docx";
        String fileName2 = "D:\\Java\\TT\\Ploombus\\Ploombus-web\\src\\main\\resources\\Apache Tika – Tika API Usage Examples.html";
        String fileName3 = "http://stackoverflow.com/questions/6656849/extract-the-text-from-urls-using-tika";
        String fileName4 = "D:\\GoogleDrive\\Vitalii Sereda.pdf";

//        System.out.println(parseExample(fileName2));
//        Files.lines(Paths.get(fileName2)).forEach(System.out::println);
//        LanguageDetector identifier = new TextLangDetector();
//        System.out.println(identifier.detect("an elephant"));
//        InputStream input = new FileInputStream(new File(fileName4));
        InputStream input = new URL(fileName3).openStream();
        ContentHandler textHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        AutoDetectParser parser = new AutoDetectParser();
        parser.parse(input, textHandler, metadata);
        input.close();
        System.out.println("Title: " + metadata.get("title"));
        System.out.println("Author: " + metadata.get("Author"));
        System.out.println("content: " + textHandler.toString());
        textHandler.toString();
    }
    public static String parseExample(String fileName) throws IOException, SAXException, TikaException {
        ContentHandler handler = new ToXMLContentHandler();
//        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = Files.newInputStream(Paths.get(fileName))) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }
}


