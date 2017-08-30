package news.controllers;

import news.models.Article;
import news.models.User;
import news.services.RestNewsServices;
import news.services.RssNewsService;
import news.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


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
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam Map<String, String> requestParams) throws SQLException, ClassNotFoundException {
        ModelAndView model = new ModelAndView("login");
        String userName = requestParams.get("username");
        String password = requestParams.get("password");

        UserService service = new UserService();
        boolean loginSuccess = service.getUserByNameAndPassword(userName, password);
        if (!loginSuccess) {
            model.addObject("error", loginSuccess);
            return model;
        }
        return new ModelAndView("redirect:/standart");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationUser(@RequestParam Map<String, String> requestParams, Model model) throws ClassNotFoundException {
        String userName = requestParams.get("username");
        String password = requestParams.get("password");
        if (!(stringNotNullAndEmpty(userName) && stringNotNullAndEmpty(password))) {
            model.addAttribute("successMessage", "Give a valid username and password");
            return "registration";
        }
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        User newUser = new User();
        newUser.setUsername(userName);
        newUser.setPassword(password);
        newUser.setSubscription(date);
        UserService service = new UserService();
        service.insertUser(newUser);
        model.addAttribute("successMessage", "The user was successfully created");
        return "registration";
    }


    private static boolean stringNotNullAndEmpty(String string) {
        return string != null && (!string.isEmpty()) ? true : false;
    }
}
