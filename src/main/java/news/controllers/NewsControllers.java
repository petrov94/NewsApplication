package news.controllers;

import news.services.RestNewsServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import news.models.Article;

import java.util.*;

import news.services.RssNewsService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static news.controllers.HomeController.*;

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
        temp.add("gol");
        media = Collections.unmodifiableList(temp);
    }


    @RequestMapping("/bild")
    public String bild(Model model) {
        model.addAttribute("allarticles", getBild());
        return "index";
    }

    @RequestMapping("/bloomberg")
    public String bloomberg(Model model) {
        model.addAttribute("allarticles", getBloomberg());
        return "index";
    }

    @RequestMapping("/standart")
    public String standart(Model model) {
        model.addAttribute("allarticles", getStandart());
        return "index";
    }

    @RequestMapping("/dnes")
    public String dnes(Model model) {
        model.addAttribute("allarticles", getDnes());
        return "index";
    }

    @RequestMapping("/kaldata")
    public String kaldata(Model model) {
        model.addAttribute("allarticles", getKaldata());
        return "index";
    }

    @RequestMapping("/gol")
    public String gol(Model model) {
        model.addAttribute("allarticles", getGol());
        return "index";
    }

    @RequestMapping("/email")
    public String subscription(Model model) {
        return "subscription";
    }
}
