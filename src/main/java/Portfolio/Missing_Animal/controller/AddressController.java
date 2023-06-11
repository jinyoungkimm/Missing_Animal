package Portfolio.Missing_Animal.controller;


import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping("/")
    public String createForm(Model model){

        model.addAttribute("memberForm",new memberForm());
        return "members/createMemberForm";

    }

    @Getter
    static class memberForm{

        private String zipcode;
        private String streetAdr;
        private String detailAdr;

        protected memberForm(){

        }

        public memberForm(String zipcod, String streetAdr, String detaildr) {
            this.zipcode = zipcod;
            this.streetAdr = streetAdr;
            this.detailAdr = detaildr;
        }
    }

}
