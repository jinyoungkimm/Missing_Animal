package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.dto.AddressFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {

    @GetMapping("/addressApi")
    public String callAddressApi(Model model){

        model.addAttribute("addressFormDto",new AddressFormDto());
        return "members/addressForm";

    }

    @PostMapping("/addressApi")
    public String resultOfAddressApi(AddressFormDto addressFormDto){

        AddressFormDto result = new AddressFormDto(addressFormDto.getZipcode(), addressFormDto.getStreetAdr(), addressFormDto.getDetailAdr());

        System.out.println(result);

        return "redirect:/";
    }




}
