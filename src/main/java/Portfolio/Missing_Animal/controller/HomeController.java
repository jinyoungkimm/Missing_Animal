package Portfolio.Missing_Animal.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){

        return "home";

    }

    @RequestMapping("/QueryApiList")
    public String queryApiList(){

        return "api/queryApiList";



    }


    @RequestMapping("/otherApiList")
    public String otherApiList(){

        return "api/otherApiList";



    }



}
