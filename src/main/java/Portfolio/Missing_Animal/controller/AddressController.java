package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.EmailForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AddressController {

    @GetMapping("/addressApi")
    public String callAddressApi(Model model){

        model.addAttribute("address",new AddressForm());

        return "members/addressForm";

    }

    @PostMapping("/addressApi")
    public String resultOfAddressApi(AddressForm address){

        AddressForm result = new AddressForm(address.getZipcode(), address.getStreetAdr(), address.getDetailAdr());

        System.out.println(result);

        return "redirect:/";
    }

    @GetMapping("/email")
    public String getEmail(Model model){

        model.addAttribute("emailForm",new EmailForm());

        return "members/email";
    }

    @PostMapping("/email")
    public String getEmail(EmailForm emailForm){

        System.out.println(emailForm);

        return "redirect:/email";
    }


}
