package news.controllers;

import news.models.Article;
import news.services.JsoupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static news.services.JsoupService.getTheMediaHtmlTag;

/**
 * Created by Petar on 8/7/2017.
 */
@Controller
public class ArticleController {
    @RequestMapping(value="/article",method = RequestMethod.GET,params = {"url"})
    public String article(@RequestParam(value="url", required = true) String url, Model model) throws Exception {
        List<Article> allarticles = new ArrayList<Article>();
        allarticles.addAll(HomeController.getBild());
        allarticles.addAll(HomeController.getBloomberg());
        allarticles.addAll(HomeController.getStandart());
        allarticles.addAll(HomeController.getDnes());
        allarticles.addAll(HomeController.getKaldata());
        allarticles.addAll(HomeController.getGol());
        for(Article art : allarticles){
            if(art.getId().equals(url)){
                model.addAttribute("article",getArticleText(art));
                break;
            }
        }
        return "article";
    }
    public static Article getArticleText(Article art){
        String medianame = getTheMediaName(art.getUrl());
        Article translatedArt = new Article();
//                translatedArt.setTitle(RestNewsServices.translate(art.getTitle()));
        translatedArt.setTitle(art.getTitle());
//                translatedArt.setDescription(RestNewsServices.translate(art.getDescription()));
        translatedArt.setDescription(art.getDescription());
        translatedArt.setPublishedAt(art.getPublishedAt());
        String articleText = null;
        if(!medianame.equals("kaldata")) {
            articleText = JsoupService.htmlParseDivId(art.getUrl(), getTheMediaHtmlTag(medianame));
        }else{
            articleText = JsoupService.getKaldata(art.getUrl(),false);
        }
//                translatedArt.setText(RestNewsServices.translate(articleText));
        translatedArt.setText(articleText);
        if(art.getUrlToImage()!=null) {
            translatedArt.setUrlToImage(art.getUrlToImage());
        }
        return translatedArt;
    }

    public static String getTheMediaName(String url){
        for(String med : NewsControllers.media){
            if(url.contains(med)){
                return med;
            }
        }
        return null;
    }

}
