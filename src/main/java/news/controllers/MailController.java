package news.controllers;

import com.itextpdf.text.DocumentException;
import news.models.Article;
import news.services.MailService;
import news.services.PdfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static news.controllers.HomeController.getBild;

/**
 * Created by Petar on 8/14/2017.
 */
@Controller
public class MailController {
    @RequestMapping(value="/subscription", method = RequestMethod.POST)
    public String sendEmail(@RequestParam Map<String, String> requestParams, Model model) throws IOException, DocumentException {
        String url = requestParams.get("country");
        String email = requestParams.get("email");
        String[] mediaNames = url.split("_");
        ArrayList<Article> allArticles = getAllSubscribedArticles(mediaNames);
        PdfService.createPdf(allArticles);
        MailService.sendMail(email);
        return "subscription";
    }

    private ArrayList<Article> getAllSubscribedArticles(String[] mediaName){
        ArrayList<Article> subscribedArticles = new ArrayList<Article>();
        for(String media: mediaName){
            switch (media) {
                case "bloomberg":
                    subscribedArticles.addAll(HomeController.getBloomberg());
                    break;
                case "bild":
                    subscribedArticles.addAll(HomeController.getBild());
                    break;
                case "standart":
                    subscribedArticles.addAll(HomeController.getStandart());
                    break;
                case "Dnes.bg":
                    subscribedArticles.addAll(HomeController.getDnes());
                    break;
                case "kaldata":
                    subscribedArticles.addAll(HomeController.getKaldata());
                    break;
                case "gol":
                    subscribedArticles.addAll(HomeController.getGol());
                    break;
            }
        }
        return  subscribedArticles;
    }
}
