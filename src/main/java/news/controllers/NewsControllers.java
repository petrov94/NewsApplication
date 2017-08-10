package news.controllers;

import news.services.NewsServices;
import news.services.TranslateApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import news.models.Article;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.*;

import static news.services.RssService.getAllArticles;

/**
 * Created by Petar on 8/6/2017.
 */
@Controller
public class NewsControllers {
    protected static List<String> media = new ArrayList<String>();

    static {
        List<String> temp = new ArrayList<String>();
        temp.add("bild");
        temp.add("bloomberg");
        media = Collections.unmodifiableList(temp);
    }

    protected static List<Article> bild = new ArrayList<>();
    protected static List<Article> bloomberg = new ArrayList<>();
    protected static List<Article> standart = new ArrayList<>();

    @RequestMapping("/bild")
    public String bild(Model model) {
        List<Article> allArticle = null;

        try {
            allArticle = NewsServices.getAllArticles("bild");
            translateArticle(allArticle,"bild");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot translate the article");
        }

        model.addAttribute("allarticles", bild);
        return "index";
    }

    @RequestMapping("/bloomberg")
    public String bloomberg(Model model) {
        List<Article> allArticle = null;
        try {
            allArticle = NewsServices.getAllArticles("bloomberg");
            translateArticle(allArticle,"bloomberg");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot translate the article");
        }
        model.addAttribute("allarticles", bloomberg);
        return "index";
    }

    @RequestMapping("/standart")
    public String standart(Model model) {
        standart = getAllArticles();
        model.addAttribute("allarticles", standart);
        return "index";
    }
    private void translateArticle(List<Article> articles,String key){
        int counter = 1;
        for (Article art : articles) {
            Article article = new Article();
//                article.setDescription(NewsServices.translate(art.getDescription()));
            article.setDescription(art.getDescription());
//                article.setTitle(NewsServices.translate(art.getTitle()));
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
}
