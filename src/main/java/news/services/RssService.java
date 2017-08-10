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
import java.util.ArrayList;
import java.util.List;

import static news.services.JsoupService.getTheMediaHtmlTag;

/**
 * Created by Petar on 8/9/2017.
 */
public class RssService {
    public static List<Article> getAllArticles(){
        List<Article> allRssArticles = new ArrayList<>();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL("http://www.standartnews.com/rss.php?p=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            Document doc = db.parse(con.getInputStream());

            doc.getDocumentElement().normalize();
            System.out.println("Root element: " +
                    doc.getDocumentElement().getNodeName());

            // loop through each item
            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Node n = items.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element e = (Element) n;
                String imageUrl = JsoupService.getImage(e.getElementsByTagName("link").item(0).getTextContent(),getTheMediaHtmlTag("standart_img"));
                Article art = new Article();
                art.setTitle(e.getElementsByTagName("title").item(0).getTextContent());
                art.setUrlToImage(imageUrl);
                art.setUrl(e.getElementsByTagName("link").item(0).getTextContent());
                art.setPublishedAt(e.getElementsByTagName("pubDate").item(0).getTextContent());
                art.setDescription(e.getElementsByTagName("description").item(0).getTextContent());
                allRssArticles.add(art);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return allRssArticles;
    }
}
