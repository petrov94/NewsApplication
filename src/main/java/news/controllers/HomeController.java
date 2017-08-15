package news.controllers;

import news.models.Article;
import news.services.RestNewsServices;
import news.services.RssNewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    private static List<Article> bild = new ArrayList<>();
    private static List<Article> bloomberg = new ArrayList<>();
    private static List<Article> standart = new ArrayList<>();
    private static List<Article> dnes = new ArrayList<>();
    private static List<Article> kaldata = new ArrayList<>();
    private static List<Article> gol = new ArrayList<>();
    static {
            List<Article> allArticleBild = null;
            try {
                allArticleBild = RestNewsServices.getAllArticles("bild");
                translateArticle(allArticleBild,"bild");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot translate the article");
            }
            List<Article> allArticleBloomberg = null;
            try {
                allArticleBloomberg = RestNewsServices.getAllArticles("bloomberg");
                translateArticle(allArticleBloomberg,"bloomberg");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot translate the article");
            }
        standart = RssNewsService.getAllArticles("standart");
        dnes = RssNewsService.getAllArticles("dnes");
        kaldata = RssNewsService.getAllArticles("kaldata");
        gol = RssNewsService.getAllArticles("gol");
    }

    private static void translateArticle(List<Article> articles, String key){
        int counter = 1;
        for (Article art : articles) {
            Article article = new Article();
//                article.setDescription(RestNewsServices.translate(art.getDescription()));
            article.setDescription(art.getDescription());
//                article.setTitle(RestNewsServices.translate(art.getTitle()));
            article.setTitle(art.getTitle());
            article.setUrl(art.getUrl());
            article.setUrlToImage(art.getUrlToImage());
            article.setPublishedAt(art.getPublishedAt());
            article.setId(Integer.toString(counter)+key);
            counter++;
            if(key.equals("bloomberg")) {
                bloomberg.add(article);
            }else{
                bild.add(article);
            }
        }
    }
    public static List<Article> getBild() {
        return bild;
    }

    public static List<Article> getBloomberg() {
        return bloomberg;
    }

    public static List<Article> getStandart() {
        return standart;
    }

    public static List<Article> getDnes() {
        return dnes;
    }

    public static List<Article> getKaldata() {
        return kaldata;
    }

    public static List<Article> getGol() {
        return gol;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
