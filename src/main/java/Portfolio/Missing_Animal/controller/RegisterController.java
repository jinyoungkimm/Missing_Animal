package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    private final StorageService storageService;

    @GetMapping("")
    public String registerMissingGet(Model model){

        Register register = new Register();
        model.addAttribute("register",register);

        return "register";

    }

    @PostMapping("")
    public String registerMissingPost(Register register,
                                      @RequestParam("file") MultipartFile file){

        storageService.store(file);

        // register 엔티티 저장(em.persist)시에, 연관 관계이 있는 Member,MissingAddress 등의 id 값을 모르면 등록(registerMissing)이 되지 않는다.
        // 고로, cascade(연쇄 반응).Persist를 설정하여, [register 엔티티를 영속화할 때, 연관된 엔티티도 자동으로 영속화 시켜줘야 한다.]

        String originalFilename = file.getOriginalFilename();
        register.setFileName(originalFilename);

        registerService.registerMissing(register);

        return "redirect:/register";

    }

    @GetMapping("/{registerId}/edit")
    String updateRegisterGet(@PathVariable("registerId") Long registerId, Model model){

        Register findRegister = registerService.findOne(registerId);

        model.addAttribute("register",findRegister);

        return "registerUpdate";
    }

    @PostMapping("/{registerId}/edit")
    public String updateRegister(//@PathVariable("registerId") Long registerId,
                                 Register register,
                                 @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println(register.getId());

        Register findRegister = registerService.findOne(register.getId());

        if(file != null)
        {
            String fileName = findRegister.getFileName();

            storageService.deleteOne(fileName); // 이전에 저장한 파일 삭제!

            storageService.store(file); // 새로 수정한 사진 저장!

        }

        String originalFilename = file.getOriginalFilename();
        register.setFileName(originalFilename);

        registerService.updateForm(register.getId(), register);

        return "redirect:/reigster";
    }

    @GetMapping("/missingAddress")
    public List<Register> showRegistersWithMissingAddress(MissingAddress missingAddress){


        List<Register> registers = registerService.ListingMissingAnimalByMissingAddress(missingAddress);

        return registers;

    }

}
