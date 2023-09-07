package Portfolio.Missing_Animal.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiPage")
@RequiredArgsConstructor
public class ApiPageController {

    /**
     * 회원(member) 조회 api(GET)
     */

    @GetMapping("/memberQuery")
    String membersQuery(){

        return "api/memberApi/members";

    }

    @GetMapping("/memberQueryWithId")
    String memberQueryWithId(){

        return "api/memberApi/memberWithId";

    }

    /**
     * 실종 등록(Register) 조회 API(GET)
     *
     */
    @GetMapping("/registerQuery")
    public String registerQuery(){


        return "api/registerApi/registers";

    }

    @GetMapping("/registerQueryWithId")
    public String registerQueryWithId(){


        return "api/registerApi/registerWithId";

    }

    @GetMapping("/registerImageQuery")
    public String registerImageQuery(){


        return "api/registerApi/registerImageQuery";

    }


    /**
     * 발견 신고(Report) 조회 API(GET)
     *
     */
    @GetMapping("/reportQuery")
    public String reportQuery(){


        return "api/reportApi/reports";

    }

    @GetMapping("/reportQueryWithId")
    public String reportQueryWithId(){


        return "api/reportApi/reportWithId";

    }

    @GetMapping("/reportImageQuery")
    public String reportImageQuery(){


        return "api/reportApi/reportImageQuery";

    }

    /**
     *  조회 이회의 API(수정,생성 등)
     *
     */

    @GetMapping("/login")
    String login(){


        return "api/memberApi/loginApi";

    }


    @GetMapping("/memberJoin")
    String memberJoin(){


        return "api/memberApi/memberJoinApi";

    }

    @GetMapping("/memberUpdate")
    String memberUpdate(){


        return "api/memberApi/memberUpdateApi";

    }

    @GetMapping("/register")
    String register(){


        return "api/registerApi/registerApi";

    }

    @GetMapping("/registerUpdate")
    String registerUpdate(){


        return "api/registerApi/registerUpdateApi";

    }

    @GetMapping("/report")
    String report(){


        return "api/reportApi/reportApi";

    }

    @GetMapping("/reportUpdate")
    String reportUpdate(){

        return "api/reportApi/reportUpdateApi";

    }






}
