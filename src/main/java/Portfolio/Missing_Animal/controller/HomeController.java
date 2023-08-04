package Portfolio.Missing_Animal.controller;


import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
public class HomeController {

    public static HashMap<String, List<String>> chatPerson = new HashMap<>();


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
    String chatInit( //@RequestParam("sender") String sender,
                Model model){

        String sender = "wlsdud6525";
        List<ChatList> chatList = new ArrayList<>();


        if(chatPerson.containsKey(sender))
        {
            List<String> receivers = chatPerson.get(sender);

            List<ChatList> collect = receivers.stream().map(reciever -> {

                String url = "http://localhost:8080/chatPage?receiver=" + reciever;

                String roomNum = createRoomNum(sender, reciever);

                return new ChatList(url,reciever);})

                    .collect(toList());

            chatList = collect;

        }


        model.addAttribute("chatList",chatList);

        return "chatInit";

    }

    @GetMapping("/chatPage")
    String chatPage(//@RequestParam("sender") String sender,
            @RequestParam("receiver") String receiver,
            Model model){


        String sender = "wlsdud6525";

        String roomNum = createRoomNum(sender, receiver); // 채팅방 id 생성 메서드

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setRoomNum(roomNum);

        System.out.println("chat = " + chat);

        model.addAttribute("chat",chat);

        if(!chatPerson.containsKey(sender)) {

            List<String> _new = new ArrayList<>();
            _new.add(receiver);
            chatPerson.put(sender,_new);

        }
        else
        {
            List<String> values = chatPerson.get(sender);

            if(!values.contains(receiver)) { // 이미 receiver가 저장돼 있는 경우에는 삽입하지 x.

                List<String> receivers = chatPerson.get(sender);
                receivers.add(receiver);

            }

        }

        return "chat";
    }

    // 채팅방 id 생성 메서드
    public static String createRoomNum(String sender,String receiver){

        String[] s = {sender,receiver};

        Arrays.sort(s); // 오름차순 정렬

        String roomNum = s[0] + s[1];

        return roomNum;

    }

    @Data
    static class Chat{

        private String sender;
        private String receiver;
        private String roomNum;


    }

    @Data
    static class ChatList{

        private String url;
        private String id;

        public ChatList(String url,String id){
            this.url = url;
            this.id = id;
        }

    }
}
