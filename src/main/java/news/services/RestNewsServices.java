package news.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import news.models.Article;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by Petar on 8/6/2017.
 */
public class RestNewsServices {
    private static Map<String, String> map = new HashMap<String, String>();
    private static Map<String, String> langs = new HashMap<String, String>();
    static {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("bild",
                "https://newsapi.org/v1/articles?source=bild&sortBy=top&apiKey=030df3ce40f34b1a8a2f410dddac8528");
        temp.put("bloomberg",
                "https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=030df3ce40f34b1a8a2f410dddac8528");
        map = Collections.unmodifiableMap(temp);
        try {
           langs = TranslateApi.getLangs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Article> getAllArticles(String source){
        assert source != null;
        if(map.containsKey(source)){
            String url = map.get(source);
            return getAllArticlesByRest(url);
        }
        return null;
    }

    public static List<Article> getAllArticlesByRest(String url) {
        List<Article> allArticles = new ArrayList<Article>();
        Client client = Client.create();

        WebResource webResource = client.resource(url);

        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        InputStream is = response.getEntityInputStream();
        JsonReader rdr = Json.createReader(is);

        JsonObject obj = rdr.readObject();
        JsonArray results = obj.getJsonArray("articles");
        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            Article newArticle = new Article();
            newArticle.setTitle(result.getString("title"));
            newArticle.setDescription(result.getString("description"));
            newArticle.setUrl(result.getString("url"));
            newArticle.setUrlToImage(result.getString("urlToImage"));
            newArticle.setPublishedAt(result.getString("publishedAt"));
            allArticles.add(newArticle);
        }
        return allArticles;
    }

    public static String translate(String text) throws Exception {
        String source = TranslateApi.detectLanguage(text);
        String target = TranslateApi.getKey(langs, "bulgarian");
        String translatedText = TranslateApi.translate(text, source, target);
        return translatedText;
    }



}
