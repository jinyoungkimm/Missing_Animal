package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.annotation.Login;
import Portfolio.Missing_Animal.controller.validation.RegisterValidator;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.springframework.util.StringUtils.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
@LogTrace
public class RegisterController {
    private final RegisterService registerService;

    private final RegisterRepository registerRepository;

    private final StorageServiceForRegister storageService;

    private final RegisterValidator registerValidator;


    @GetMapping("")
    public String registerMissing_Get(Model model){

        Register register = new Register();


        model.addAttribute("register",register);


        return "registers/register";

    }

    @PostMapping("")
    public String registerMissing_Post( @ModelAttribute Register register, BindingResult bindingResult,
                                       @Login Member member,
                                       @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        registerValidator.validate(register,bindingResult); // 직접 호출!

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
        registerService.registerMissing(member,register);

        redirectAttributes.addAttribute("status",true);

        return "redirect:/register";

    }



    @GetMapping("/list")
    public String registerList(Model model, @PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

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

        Page<Register> page = registerService.searchByRegisterCond(registerSearchCond,pageable);


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

    @GetMapping("/missingAddress") // 우리 동네 실종 동물 찾기
    public String showRegistersWithMissingAddress(Model model){

        RegisterSearchCond registerSearchCond = new RegisterSearchCond();

        model.addAttribute("registerSearchCond",registerSearchCond);

        return "registers/registersByMissingAddress";

    }

    @PostMapping("/search/list/myVilage") // [우리 동네 실종 찾기]의 결과
    public String registerListBySearchCondtion(
            @ModelAttribute RegisterSearchCond registerSearchCond ,BindingResult bindingResult, Model model,
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

        MissingAddress missingAddress = findRegister.getMissingAddress();

        String streetName = missingAddress.getStreetName();

        if(hasText(missingAddress.getDong()))
            streetName = missingAddress.getDong() + " " + streetName;
        if(hasText(missingAddress.getGu()))
            streetName = missingAddress.getGu() + " " + streetName;
        if(hasText(missingAddress.getCityName()))
            streetName = missingAddress.getCityName() + " " + streetName;
        if(hasText(missingAddress.getPrefecture()))
            streetName = missingAddress.getPrefecture() + " " +streetName;
        if(hasText(missingAddress.getStreetNumber()))
            streetName = streetName + " " + missingAddress.getStreetNumber();

        missingAddress.setStreetName(streetName);

        model.addAttribute("register",findRegister);

        return "registers/registerUpdate";
    }



    @PostMapping("/{registerId}/edit")
    public String updateRegister(@Validated @ModelAttribute Register register, BindingResult bindingResult,
                                 @PathVariable("registerId") Long registerId,
                                 @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {


        if(bindingResult.hasErrors())
            return "registers/registerUpdate";

        //Register findRegister = registerService.findOne(registerId);
        Register findRegister = registerRepository.findById(registerId);

        if(!file.isEmpty() )
        {
            String fileName = null;

            if(findRegister.getFileName() != null){

                fileName = findRegister.getFileName();

                storageService.deleteOne(fileName); // 이전에 저장한 파일 삭제!

                storageService.store(file); // 새로 수정한 사진 저장!

            }

             else{
                 storageService.store(file);
            }

        }

        String originalFilename = file.getOriginalFilename();

        register.setFileName(originalFilename);

        registerService.updateForm(registerId, register);

        redirectAttributes.addAttribute("status",true);

        return "redirect:/register/{registerId}/edit";
    }




    @GetMapping("{registerId}/getOneRegister")
    public String findOneRegister_NoUpdate(@PathVariable("registerId") Long registerId, Model model)

    {

        Register findRegister = registerService.findOne(registerId);
        Member member = findRegister.getMember();

        if(member.getEmail() != null) {
            String email = member.getEmail().getFirst() + "@" + member.getEmail().getLast();
            member.getEmail().setFirst(email);
        }
        else{

            String email = "이메일에 대한 정보가 없습니다.";
            member.getEmail().setFirst(email);

        }

        if(!isEmpty(findRegister.getFileName()))
        {
            Path path = storageService.load(findRegister.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForRegister.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }


        MissingAddress missingAddress = findRegister.getMissingAddress();
        String streetName = missingAddress.getStreetName();

        if(hasText(missingAddress.getDong()))
            streetName = missingAddress.getDong() + " " + streetName;
        if(hasText(missingAddress.getGu()))
            streetName = missingAddress.getGu() + " " + streetName;
        if(hasText(missingAddress.getCityName()))
            streetName = missingAddress.getCityName() + " " + streetName;
        if(hasText(missingAddress.getPrefecture()))
            streetName = missingAddress.getPrefecture() + " " +streetName;
        if(hasText(missingAddress.getStreetNumber()))
            streetName = streetName + " " + missingAddress.getStreetNumber();

        missingAddress.setStreetName(streetName);

        model.addAttribute("register",findRegister);

        return "registers/showOneRegister";

    }

    @GetMapping("{registerId}/getOneRegisterWithoutUpdate")
    public String findOneRegister_YesUpdate(@PathVariable("registerId") Long registerId, Model model)
    {

        Register findRegister = registerService.findOne(registerId);


        Member member = findRegister.getMember();

        if(member.getEmail() != null) {
            String email = member.getEmail().getFirst() + "@" + member.getEmail().getLast();
            member.getEmail().setFirst(email);
        }
        else{

            String email = "이메일에 대한 정보가 없습니다.";
            member.getEmail().setFirst(email);

        }


        if(!isEmpty(findRegister.getFileName()))
        {
            Path path = storageService.load(findRegister.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadControllerForRegister.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        MissingAddress missingAddress = findRegister.getMissingAddress();
        String streetName = missingAddress.getStreetName();

        if(hasText(missingAddress.getDong()))
            streetName = missingAddress.getDong() + " " + streetName;
        if(hasText(missingAddress.getGu()))
            streetName = missingAddress.getGu() + " " + streetName;
        if(hasText(missingAddress.getCityName()))
            streetName = missingAddress.getCityName() + " " + streetName;
        if(hasText(missingAddress.getPrefecture()))
            streetName = missingAddress.getPrefecture() + " " +streetName;
        if(hasText(missingAddress.getStreetNumber()))
            streetName = streetName + " " + missingAddress.getStreetNumber();

        missingAddress.setStreetName(streetName);

        model.addAttribute("register",findRegister);
        model.addAttribute("member",member);

        return "registers/showOneRegisterWithoutUpdate";

    }

}
