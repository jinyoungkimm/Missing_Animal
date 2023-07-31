package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

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

        return "registers/register";

    }

    @PostMapping("")
    public String registerMissingPost(Register register,
                                      @RequestParam("file") MultipartFile file){

        if(!file.isEmpty()) {

            storageService.store(file);
            String originalFilename = file.getOriginalFilename();
            register.setFileName(originalFilename);

        }

        // register 엔티티 저장(em.persist)시에, 연관 관계이 있는 Member,MissingAddress 등의 id 값을 모르면 등록(registerMissing)이 되지 않는다.
        // 고로, cascade(연쇄 반응).Persist를 설정하여, [register 엔티티를 영속화할 때, 연관된 엔티티도 자동으로 영속화 시켜줘야 한다.]
        registerService.registerMissing(register);

        return "redirect:/registers/register";

    }

    @GetMapping("/{registerId}/edit")
    String updateRegisterGet(@PathVariable("registerId") Long registerId, Model model){

        Register findRegister = registerService.findOne(registerId);

        model.addAttribute("register",findRegister);

        return "registers/registerUpdate";
    }

    @PostMapping("/{registerId}/edit")
    public String updateRegister(Register register,
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

        return "redirect:/registers/reigster";
    }

    @GetMapping("/missingAddress")
    public String showRegistersWithMissingAddress(MissingAddress missingAddress){

        List<Register> registers = registerService.ListingMissingAnimalByMissingAddress(missingAddress);

        return "아직 구현 안함";

    }

    @GetMapping("{registerId}/getOneRegister")
    public String findOneRegisterById(@PathVariable("registerId") Long registerId, Model model
    )
    {

        Register findRegister = registerService.findOne(registerId);

        if(!isEmpty(findRegister.getFileName()))
        {
            Path path = storageService.load(findRegister.getFileName());
            String serveFile = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile", path.getFileName().toString()).build().toUri().toString();
            model.addAttribute("file",serveFile);

        }

        model.addAttribute("register",findRegister);

        return "registers/showOneRegister";

    }


}
