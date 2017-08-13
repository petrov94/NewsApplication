package news.controllers;

import news.services.RestNewsServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import news.models.Article;

import java.util.*;

import news.services.RssNewsService;

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
        temp.add("standart");
        temp.add("dnes");
        temp.add("kaldata");
        media = Collections.unmodifiableList(temp);
    }

    protected static List<Article> bild = new ArrayList<>();
    protected static List<Article> bloomberg = new ArrayList<>();
    protected static List<Article> standart = new ArrayList<>();
    protected static List<Article> dnes = new ArrayList<>();
    protected static List<Article> kaldata = new ArrayList<>();
    @RequestMapping("/bild")
    public String bild(Model model) {
        List<Article> allArticle = null;
        try {
            allArticle = RestNewsServices.getAllArticles("bild");
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
            allArticle = RestNewsServices.getAllArticles("bloomberg");
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
        standart = RssNewsService.getAllArticles("standart");
        model.addAttribute("allarticles", standart);
        return "index";
    }

    @RequestMapping("/dnes")
    public String dnes(Model model) {
        dnes = RssNewsService.getAllArticles("dnes");
        model.addAttribute("allarticles", dnes);
        return "index";
    }

    @RequestMapping("/kaldata")
    public String kaldata(Model model) {
        kaldata = RssNewsService.getAllArticles("kaldata");
        model.addAttribute("allarticles", kaldata);
        return "index";
    }
    private void translateArticle(List<Article> articles,String key){
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
}
