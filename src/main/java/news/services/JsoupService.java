package news.services;

import news.models.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.validation.constraints.Null;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        htmlTags.put("bild", "txt");
        htmlTags.put("bloomberg", "body-copy");
        htmlTags.put("standart_img", "articleSlider");
        htmlTags.put("standart", "articleBodyHolder");
        htmlTags.put("standart_img", "articleSlider");
        htmlTags.put("dnes_img", "art_img");
        htmlTags.put("dnes", "art_start");
        htmlTags.put("kaldata_img", "entry-thumb td-modal-image");
        htmlTags.put("gol", "article-text");
        html = Collections.unmodifiableMap(htmlTags);
    }

    public static String htmlParseDivId(String url, String divID) throws NullPointerException{
        Document doc;
        Elements info = null;
        Element info_ByID = null;
        try {
            doc = Jsoup.connect(url).followRedirects(true).get();
            info = doc.getElementsByClass(divID);
            if (info.isEmpty()) {
                info_ByID = doc.getElementById(divID);
            }
        } catch (IOException e) {
            throw new NullPointerException();
        }
        if (!info.isEmpty()) {
            return info.text();
        } else {
            if(info_ByID.text()!= null && info_ByID.text().isEmpty()){ throw new NullPointerException();}
            return info_ByID.text();
        }
    }

    public static String getImage(String url, String divID, String title) {
        Document doc;
        Elements info = null;
        try {
            doc = Jsoup.connect(url).get();
            info = doc.getElementsByTag("img");
            for (Element el : info) {
                if (el.hasClass(divID) || el.attr("alt").equals(title)) {
                    return el.attr("src");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getKaldata(String url, boolean flag) {
        try {
            URL obj = new URL(url);
            URLConnection conn = obj.openConnection();
            StringBuffer text = new StringBuffer();
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);

            String line;
            do {
                line = buff.readLine();
                text.append(line + "\n");
            } while (line != null);

            Document doc = Jsoup.parse(text.toString());
            if (flag) {
                Elements info = doc.getElementsByTag("img");
                for (Element el : info) {
                    if (el.hasClass("entry-thumb td-modal-image")) {
                        return el.attr("src");
                    }
                }
            }else{
                StringBuffer textArticle = new StringBuffer();
                Elements elements = doc.select("div[class=td-post-content] p");
                for(int i=0;i<elements.size()-1;i++){
                    textArticle.append(elements.get(i).text());
                }
                return textArticle.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTheMediaHtmlTag(String media) {
        return (String) html.get(media);
    }
}