package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.annotation.LogTrace;
import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;

import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import Portfolio.Missing_Animal.repository.repositoryinterface.MemberRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForRegister;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@LogTrace
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final MemberRepository memberRepository;
    private final RegisterRepository registerRepository; // 순수 JPA Repository

   //private final MissingAddressRepository missingAddressRepository; // 순수 JPA Repository
    private final RegisterRepositorySDJ registerRepositorySDJ;
    private final MissingAddressRepositorySDJ missingAddressRepositorySDJ;

    private final StorageServiceForRegister storageService;

    //실종 등록
    @Override
    @Transactional
    public Long registerMissing(Member member,Register register) {

        register.setRegisterStatus(RegisterStatus.NOT_SOLVED);
        register.setReportedStatus(ReportedStatus.NO);

        String address = register.getMissingAddress().getStreetName();

        String prefecture = findPrefecture(address);

        String cityName = findCity(address);

        String Gu = findGu(address);

        String Dong = findDong(address);

        String streetName = findStreetName(address);

        String streetNumber = findStreetNumber(address);

        MissingAddress missingAddress = new MissingAddress();
        missingAddress.setPrefecture(prefecture);
        missingAddress.setCityName(cityName);
        missingAddress.setGu(Gu);
        missingAddress.setDong(Dong);
        missingAddress.setStreetName(streetName);
        missingAddress.setStreetNumber(streetNumber);
        missingAddress.setZipcode(register.getMissingAddress().getZipcode());

        register.setMissingAddress(missingAddress);

        Member findMember = memberRepository.findByUserId(member.getUserId());

        register.setMember(findMember);

        Long saveId = registerRepositorySDJ.save(register).getId();

        return saveId;

    }

    //실종 목록
    @Override
    @Transactional(readOnly = true)
    public List<Register> listingRegister() {

        List<Register> all = registerRepositorySDJ.findAll();

        return all;
    }

    @Override
    public Page<Register> listingRegisterV2(Pageable pageable) {

        Page<Register> page = registerRepositorySDJ.findAll(pageable);

        return page;

    }

    @Override
    @Transactional(readOnly = true)
    public Register findOne(Long id) {

        Register register = registerRepositorySDJ.findById(id).get();

        return register;
    }

    @Override
    @Transactional // dirty checking 이용(Spring Data JPA는 Dirty Checking이 기능하지 x)
    public Long updateForm(Long registerId, Register register) {


        Register findRegister = registerRepository.findById(registerId);

        if(hasText(register.getFileName()))
            findRegister.setFileName(register.getFileName());

        if(hasText(register.getAnimalName()))
            findRegister.setAnimalName(register.getAnimalName());

        if(hasText(register.getAnimalSex()))
            findRegister.setAnimalSex(register.getAnimalSex());

        if(hasText(register.getAnimalVariety()))
            findRegister.setAnimalVariety(register.getAnimalVariety());

        if(hasText(register.getAnimalWeight()))
            findRegister.setAnimalWeight(register.getAnimalWeight());

        if(hasText(register.getEtc()))
            findRegister.setEtc(register.getEtc());

        if(register.getRegisterStatus() != null)
            findRegister.setRegisterStatus(register.getRegisterStatus());

        if(register.getReportedStatus() != null)
            findRegister.setReportedStatus(register.getReportedStatus());

        if(register.getMissingAddress() != null) {

            MissingAddress missingAddress = findRegister.getMissingAddress();

            String address = register.getMissingAddress().getStreetName();
            String prefecture = findPrefecture(address);
            String cityName = findCity(address);
            String Gu = findGu(address);
            String Dong = findDong(address);
            String streetName = findStreetName(address);
            String streetNumber = findStreetNumber(address);

            if (hasText(prefecture))
                missingAddress.setPrefecture(prefecture);
            if (hasText(cityName))
                missingAddress.setCityName(cityName);
            if (hasText(Gu))
                missingAddress.setGu(Gu);
            if (hasText(Dong))
                missingAddress.setDong(Dong);
            if (hasText(streetName))
                missingAddress.setStreetName(streetName);
            if (hasText(streetNumber))
                missingAddress.setStreetNumber(streetNumber);

            if (hasText(register.getMissingAddress().getZipcode()))
                missingAddress.setZipcode(register.getMissingAddress().getZipcode());
        }

        return registerId;
    }

    @Override
    @Transactional
    public List<Register> ListingMissingAnimalByMissingAddress(MissingAddress missingAddress){

        // [도시명]만 입력된 경우!
        // [도시명] + [구/군]이 입력된 경우
        // [도시명] + [구/군] + [동/읍/리]가 입력된 경우
        // [도시명] + [구/군] + [동/읍/리] + [도로명]이 입력된 경우!
        // -> 추후에 Querydsl로 MissingAddressRepository에서 동적 쿼리로 구현을 하자!!!

        // 지금은 [도시명]만으로 신고 기능을 만들자!
        if(missingAddress.getCityName() != null){

            List<MissingAddress> byCityName = missingAddressRepositorySDJ.findByCityName(missingAddress.getCityName());

            if(byCityName.isEmpty() == true)
                throw new IllegalStateException("해당 도시에는 실종 등록된 동물이 없습니다.");

            List<Register> register = new ArrayList<>();

            byCityName.forEach(mr->{

                List<Register> registers = mr.getRegisters();

                for (Register register1 : registers) {

                    register.add(register1);
                }
            });

            return register;
        }


        return null;
    }


    @Override
    public Page<Register> searchByRegisterCond(RegisterSearchCond registerSearchCond, Pageable pageable) {



        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingComplexV2(registerSearchCond,pageable);


        return page;

    }

    @Override
    public Page<Register> searchByRegisterCond2(RegisterSearchCond registerSearchCond, Pageable pageable) {

        String address = registerSearchCond.getStreetName();

        String prefecture = findPrefecture(address);
        registerSearchCond.setPrefecture(prefecture);


        String cityName = findCity(address);
        registerSearchCond.setCityName(cityName);


        String Gu = findGu(address);
        registerSearchCond.setGu(Gu);


        String Dong = findDong(address);
        registerSearchCond.setDong(Dong);


        String streetName = findStreetName(address);
        registerSearchCond.setStreetName(streetName);


        String streetNumber = findStreetNumber(address);
        registerSearchCond.setStreetNumber(streetNumber);


        Page<Register> page = registerRepositorySDJ.searchRegistersWithPagingComplexV2(registerSearchCond,pageable);


        return page;

    }

    @Override
    public Page<Register> findRegiserInfo(String userId, Pageable pageable) {

        Page<Register> registers = registerRepository.findByUserId(userId, pageable);

        return registers;

    }


    public static String findPrefecture(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            //북/남/도
            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '북' || s.charAt(s.length()-1) == '남' || s.charAt(s.length()-1) == '도' )
                return s;

        }

        return null;

    }

    public static String findCity(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){
            // 시/군/면

            String s = st.nextToken();
            if(s.charAt(s.length()-1) != '북' && s.charAt(s.length()-1) != '남' && s.charAt(s.length()-1) != '도'
                    && s.charAt(s.length()-1) != '군'  && s.charAt(s.length()-1) != '구'
                    && s.charAt(s.length()-1) != '읍' && s.charAt(s.length()-1) != '면' && s.charAt(s.length()-1) != '동'
                    && s.charAt(s.length()-1) != '0' && s.charAt(s.length()-1) != '1' && s.charAt(s.length()-1) != '2'
                    && s.charAt(s.length()-1) != '3' && s.charAt(s.length()-1) != '4' && s.charAt(s.length()-1) != '5'
                    && s.charAt(s.length()-1) != '6' && s.charAt(s.length()-1) != '7' && s.charAt(s.length()-1) != '8'
                    && s.charAt(s.length()-1) != '9')
                return s;

        }

        return null;

    }


    public static String findGu(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            //구

            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '구')
                return s;

        }

        return null;

    }

    public static String findDong(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // 읍/면/동
            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '읍' || s.charAt(s.length()-1) == '면' || s.charAt(s.length()-1) == '동')
                return s;

        }

        return null;

    }

    public static String findStreetName(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // ~로, ~길
            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '로' || s.charAt(s.length()-1) == '길')
                return s;

        }

        return null;

    }


    public static String findStreetNumber(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // 0~9
            String s = st.nextToken();
            if(  s.charAt(s.length()-1) == '0' || s.charAt(s.length()-1) == '1' || s.charAt(s.length()-1) == '2'
                    || s.charAt(s.length()-1) == '3' || s.charAt(s.length()-1) == '4' || s.charAt(s.length()-1) == '5'
                    || s.charAt(s.length()-1) == '6' || s.charAt(s.length()-1) == '7' || s.charAt(s.length()-1) == '8'
                    || s.charAt(s.length()-1) == '9')
                return s;

        }

        return null;

    }


}
