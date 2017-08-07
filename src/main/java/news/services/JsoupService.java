package news.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Petar on 8/8/2017.
 */
public class JsoupService {
    public static String htmlParse (String url){

        Document doc;
        Elements info = null;
        try {
            doc = Jsoup.connect(url).get();
            info = doc.getElementsByClass("body-copy");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  info.text();
    }
}
