package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.annotation.Login;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.service.serviceinterface.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@LogTrace
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    public static HashMap<String, List<String>> chatPerson = new HashMap<>();

    private static final String SESSION_ID="session Id";


    private final MemberRepository memberRepository;




    @RequestMapping("/")
    public String homeV3(@Login Member member,Model model){


        // 세션이 있는 경우!
        if(member == null)
            return "home";

        // 세션이 유지되는 경우!
        model.addAttribute("member",member);

        return "homeLoginSuccess";

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
    String chatInit(
                @Login Member member,
                Model model){


        String sender = member.getUserId();
        log.info("sender = {}",member.getUserId());

        List<ChatList> chatList = new ArrayList<>();


        if(chatPerson.containsKey(sender))
        {
            List<String> receivers = chatPerson.get(sender);

            List<ChatList> collect = receivers.stream().map(reciever -> {

                String url = "http://43.202.152.228:8080/chatPage?receiver=" + reciever;

                //String roomNum = createRoomNum(sender, reciever);

                return new ChatList(url,reciever);})

                    .collect(toList());

            chatList = collect;

        }


        model.addAttribute("chatList",chatList);

        return "chatInit";

    }

    @GetMapping("/chatPage")
    String chatPage(
            @Login Member member,
            @RequestParam("receiver") String receiver,
            Model model){

        //receiver, 즉 이 id가 회원 가입이된 id인지 아닌지를 판단!
        Member _receiver = memberRepository.findByUserId(receiver);
        if(_receiver ==  null){
            throw new IllegalStateException("해당 id는 존재하지 않는 id입니다.");
        }

        String sender = member.getUserId();
        log.info("sender = {}",sender);

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
