package news.services;

import news.models.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petar on 8/8/2017.
 */
public class JsoupService {
    private static Map<String, String> html = new HashMap<String, String>();
    static {
        Map<String, String> htmlTags = new HashMap<String, String>();
        htmlTags.put("bild","txt");
        htmlTags.put("bloomberg","body-copy");
        htmlTags.put("standart_img","articleSlider");
        html = Collections.unmodifiableMap(htmlTags);
    }
    public static String htmlParseDivId (String url,String divID){

        Document doc;
        Elements info = null;
        try {
            doc = Jsoup.connect(url).get();
            info = doc.getElementsByClass(divID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  info.text();
    }
    public static String getImage (String url,String divID){

        Document doc;
        Elements info = null;
        try {
            doc = Jsoup.connect(url).get();
            info = doc.getElementsByTag("img");
            for (Element el : info) {
                if(el.hasClass(divID)){
                    return el.attr("src");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTheMediaHtmlTag(String media){
        return (String) html.get(media);
    }
}
