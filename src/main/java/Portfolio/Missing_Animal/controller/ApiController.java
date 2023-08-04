package Portfolio.Missing_Animal.controller;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/apiPage")
@RequiredArgsConstructor
public class ApiController {

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


        return "memberApi/loginApi";

    }


    @GetMapping("/memberJoin")
    String memberJoin(){


        return "memberApi/memberJoin";

    }

    @GetMapping("/memberUpdate")
    String memberUpdate(){


        return "memberApi/memberUpdateApi";

    }

    @GetMapping("/register")
    String register(){


        return "registerApi/registerApi";

    }

    @GetMapping("/registerUpdate")
    String registerUpdate(){


        return "registerApi/registerUpdateApi";

    }

    @GetMapping("/report")
    String report(){


        return "reportApi/reportApi";

    }

    @GetMapping("/reportUpdate")
    String reportUpdate(){

        return "reportApi/reportUpdateApi";

    }






}
