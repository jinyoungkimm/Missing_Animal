package Portfolio.Missing_Animal.controller;


import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class HomeController {

    public static HashMap<String,String> chatRoomNum = new HashMap<>();



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

    @RequestMapping("/chatInit")
    String chat(){

        return "chatInit";

    }

    @GetMapping("/chatPage")
    String chatPage(//@RequestParam("sender") String sender,
            @RequestParam("receiver") String receiver,
            Model model){

        String sender = "wlsdud6523";
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setRoomNum(sender+receiver);

        model.addAttribute("chat",chat);


        return "chat";
    }


    @Data
    static class Chat{

        private String sender;
        private String receiver;
        private String roomNum;


    }


}
