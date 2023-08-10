package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForRegister;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    private final StorageServiceForRegister storageService;




    @GetMapping("")
    public String registerMissingGet(Model model){

        Register register = new Register();
        model.addAttribute("register",register);



        return "registers/register";

    }

    @PostMapping("")
    public String registerMissingPost(@ModelAttribute Register register, BindingResult bindingResult,
                                      @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){


        if(!StringUtils.hasText(register.getAnimalName())){
            bindingResult.rejectValue("animalName","required");
        }
        if(!StringUtils.hasText(register.getAnimalSex()))
            bindingResult.rejectValue("animalSex","required");
        if(!StringUtils.hasText(register.getAnimalVariety()))
            bindingResult.rejectValue("animalVariety","required");
        if(!StringUtils.hasText(register.getMissingAddress().getZipcode()))
            bindingResult.rejectValue("missingAddress.zipcode","required");
        if(!StringUtils.hasText(register.getMissingAddress().getStreetName()))
            bindingResult.rejectValue("missingAddress.streetName","required");


        if(bindingResult.hasErrors()) {

            //model.addAttribute("register",register);  생략 가능
            return "registers/register";

        }


        if(!file.isEmpty()) {

            storageService.store(file);
            String originalFilename = file.getOriginalFilename();
            register.setFileName(originalFilename);

        }

        // register 엔티티 저장(em.persist)시에, 연관 관계이 있는 Member,MissingAddress 등의 id 값을 모르면 등록(registerMissing)이 되지 않는다.
        // 고로, cascade(연쇄 반응).Persist를 설정하여, [register 엔티티를 영속화할 때, 연관된 엔티티도 자동으로 영속화 시켜줘야 한다.]
        registerService.registerMissing(register);


        redirectAttributes.addAttribute("status",true);

        return "redirect:/register";

    }

   // @GetMapping("/list")
    public String registerListV1(Model model){

        List<Register> registers = registerService.listingRegister();

        model.addAttribute("registers",registers);

        return "registers/registerList";

    }

    @GetMapping("/list")
    public String registerListV2(Model model, @PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Register> page = registerService.listingRegisterV2(pageable);


        int nowPage = page.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,page.getTotalPages());

        model.addAttribute("page",page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        RegisterSearchCond searCond = new RegisterSearchCond();
        model.addAttribute("searchCond",searCond);

        return "registers/registerList";

    }

    @PostMapping("/search/list") // 실종 리스트
    public String registerListBySearchCondtion(Model model,
                                               @PageableDefault(page = 0, size = 2, sort = "id",direction = Sort.Direction.ASC) Pageable pageable,
                                               RegisterSearchCond registerSearchCond)
    {
        System.out.println("registerSearchCond = " + registerSearchCond);;

        Page<Register> page = registerService.searchByRegisterCond(registerSearchCond,pageable);

        System.out.println("size = " + page.getTotalElements());

        int nowPage = page.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,page.getTotalPages());

        model.addAttribute("page",page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        RegisterSearchCond searCond = new RegisterSearchCond();
        model.addAttribute("searchCond",searCond);

        return "registers/registerList";

    }

    @PostMapping("/search/list/myVilage") // 우리 동네 실종 동물 찾기
    public String registerListBySearchCondtion2(@ModelAttribute RegisterSearchCond registerSearchCond ,BindingResult bindingResult, Model model,
                                               @PageableDefault(page = 0, size = 2, sort = "id",direction = Sort.Direction.ASC) Pageable pageable
                                              )
    {


        if(!StringUtils.hasText(registerSearchCond.getZipcode()))
            bindingResult.rejectValue("zipcode","required");
        if(bindingResult.hasErrors())
            return "registers/registersByMissingAddress";




        Page<Register> page = registerService.searchByRegisterCond2(registerSearchCond,pageable);



        int nowPage = page.getPageable().getPageNumber() + 1; // or pageable.getPageNumber();
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,page.getTotalPages());

        model.addAttribute("page",page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        RegisterSearchCond searCond = new RegisterSearchCond();
        model.addAttribute("searchCond",searCond);

        return "registers/registerListMyVilage";

    }

    @GetMapping("/{registerId}/edit")
    String updateRegisterGet(@PathVariable("registerId") Long registerId, Model model){

        Register findRegister = registerService.findOne(registerId);

        model.addAttribute("register",findRegister);

        return "registers/registerUpdate";
    }

    @PostMapping("/{registerId}/edit")
    public String updateRegister(Register register,
                                 @PathVariable("registerId") Long registerId,
                                 @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        Register findRegister = registerService.findOne(registerId);

        if(!file.isEmpty() )
        {
            String fileName = findRegister.getFileName();

            storageService.deleteOne(fileName); // 이전에 저장한 파일 삭제!

            storageService.store(file); // 새로 수정한 사진 저장!

        }

        String originalFilename = file.getOriginalFilename();
        register.setFileName(originalFilename);

        registerService.updateForm(registerId, register);

        redirectAttributes.addAttribute("status",true);

        return "redirect:/register/{registerId}/edit";
    }

    @GetMapping("/missingAddress")
    public String showRegistersWithMissingAddress(Model model){

        //List<Register> registers = registerService.ListingMissingAnimalByMissingAddress(missingAddress);

        RegisterSearchCond registerSearchCond = new RegisterSearchCond();


        model.addAttribute("registerSearchCond",registerSearchCond);


        return "registers/registersByMissingAddress";

    }




    @GetMapping("{registerId}/getOneRegister")
    public String findOneRegisterById(@PathVariable("registerId") Long registerId, Model model
    )
    {

        Register findRegister = registerService.findOne(registerId);

        if(!isEmpty(findRegister.getFileName()))
        {
            Path path = storageService.load(findRegister.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForRegister.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        model.addAttribute("register",findRegister);

        return "registers/showOneRegister";

    }

}
