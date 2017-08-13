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
        allarticles.addAll(NewsControllers.bild);
        allarticles.addAll(NewsControllers.bloomberg);
        allarticles.addAll(NewsControllers.standart);
        allarticles.addAll(NewsControllers.dnes);
        allarticles.addAll(NewsControllers.kaldata);
        for(Article art : allarticles){
            if(art.getId().equals(url)){
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
                translatedArt.setUrlToImage(art.getUrlToImage());
                model.addAttribute("article",translatedArt);
                break;
            }
        }
        return "article";
    }

    public String getTheMediaName(String url){
        for(String med : NewsControllers.media){
            if(url.contains(med)){
                return med;
            }
        }
        return null;
    }

}
