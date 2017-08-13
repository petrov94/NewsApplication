package news.services;

import news.models.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static news.services.JsoupService.getTheMediaHtmlTag;

/**
 * Created by Petar on 8/9/2017.
 */
public class RssNewsService {
    private static Map<String, String> map = new HashMap<String, String>();
    static {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("standart",
                "http://www.standartnews.com/rss.php?p=1");
        temp.put("dnes",
                "http://www.dnes.bg/rss.php?today");
        temp.put("kaldata",
                "https://www.kaldata.com/feed");
        map = Collections.unmodifiableMap(temp);
    }
    public static List<Article> getAllArticles(String source){
        assert source != null;
        if(map.containsKey(source)){
            String url = map.get(source);
            return getAllArticlesByRss(url,source);
        }
        return null;
    }

    public static List<Article> getAllArticlesByRss(String urlMedia,String media){
        List<Article> allRssArticles = new ArrayList<>();
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL(urlMedia);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            Document doc = db.parse(con.getInputStream());
            doc.getDocumentElement().normalize();
            // loop through each item
            NodeList items = doc.getElementsByTagName("item");
            int counter = 1;
            for (int i = 0; i < items.getLength(); i++) {
                Node n = items.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element e = (Element) n;
                String imageUrl = "";
                if(!media.equals("kaldata")) {
                    imageUrl = JsoupService.getImage(e.getElementsByTagName("link").item(0).getTextContent(), getTheMediaHtmlTag(media + "_img"), e.getElementsByTagName("title").item(0).getTextContent());
                }else {
                    imageUrl = JsoupService.getKaldata(e.getElementsByTagName("link").item(0).getTextContent(), true);
                }
                Article art = new Article();
                art.setTitle(e.getElementsByTagName("title").item(0).getTextContent());
                if(imageUrl!=null) {
                    art.setUrlToImage(imageUrl);
                    //throw NEW EXCEPTION!!!!!!
                }
                art.setUrl(e.getElementsByTagName("link").item(0).getTextContent());
                if(media.equals("standart")||media.equals("kaldata")) {
                    art.setPublishedAt(e.getElementsByTagName("pubDate").item(0).getTextContent());
                }else{
                    art.setPublishedAt(e.getElementsByTagName("dc:date").item(0).getTextContent());
                    System.out.println(e.getElementsByTagName("dc:date").item(0).getTextContent());
                }
                if(media.equals("kaldata")) {
                    String p[] = e.getElementsByTagName("description").item(0).getTextContent().split("<p>");
                    String p1[] = p[1].split("</p>");
                    art.setDescription(p1[0]);
                }else{
                    art.setDescription(e.getElementsByTagName("description").item(0).getTextContent());
                }
                art.setId(Integer.toString(counter)+media);
                counter++;
                allRssArticles.add(art);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return allRssArticles;
    }
}
