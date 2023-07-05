package Portfolio.Missing_Animal.SpecifiedController;



import Portfolio.Missing_Animal.dto.SolvedIncidentDto;
import Portfolio.Missing_Animal.service.RegisterApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SolvedIncidentController {

    private final RegisterApiService registerApiService;

    @GetMapping("/solvedIncident")
    String querySolvedIncident(Model model){


        SolvedIncidentDto solvedIncidentDto = registerApiService.countAllRegisters();

        model.addAttribute("solvedIncidentDto",solvedIncidentDto);

        return "api/apiForm.html";

    }

}
