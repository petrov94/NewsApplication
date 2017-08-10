package news.controllers;

import news.models.Article;
import news.services.JsoupService;
import news.services.NewsServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

import static news.services.JsoupService.getTheMediaHtmlTag;
import static news.services.RssService.getAllArticles;

/**
 * Created by Petar on 8/7/2017.
 */
@Controller
public class ArticleController {
    @RequestMapping(value="/article",method = RequestMethod.GET,params = {"url"})
    public String article(@RequestParam(value="url", required = true) String url, Model model) throws Exception {
        List<Article> allarticles = new ArrayList<Article>();
        allarticles.addAll(NewsControllers.bild);
        allarticles.addAll(NewsControllers.bloomberg);
        for(Article art : allarticles){
            if(art.getId().equals(url)){
                getAllArticles();
//                String medianame = getTheMediaName(art.getUrl());
//                Article translatedArt = new Article();
////                translatedArt.setTitle(NewsServices.translate(art.getTitle()));
//                translatedArt.setTitle(art.getTitle());
////                translatedArt.setDescription(NewsServices.translate(art.getDescription()));
//                translatedArt.setDescription(art.getDescription());
//                translatedArt.setPublishedAt(art.getPublishedAt());
//                String articleText = JsoupService.htmlParse(art.getUrl(),getTheMediaHtmlTag(medianame));
////                translatedArt.setText(NewsServices.translate(articleText));
//                translatedArt.setText(articleText);
//                translatedArt.setUrlToImage(art.getUrlToImage());
//                model.addAttribute("article",translatedArt);
//                break;
            }
        }
        return "article";
    }

    private String getTheMediaName(String url){
        for(String med : NewsControllers.media){
            if(url.contains(med)){
                return med;
            }
        }
        return null;
    }

}
