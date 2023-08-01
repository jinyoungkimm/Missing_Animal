package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;

import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import Portfolio.Missing_Animal.service.serviceinterface.RegisterService;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import Portfolio.Missing_Animal.spring_data_jpa.RegisterRepositorySDJ;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    //private final RegisterRepository registerRepository; // 순수 JPA Repository

   //private final MissingAddressRepository missingAddressRepository; // 순수 JPA Repository

    private final RegisterRepositorySDJ registerRepository;

    private final MissingAddressRepositorySDJ missingAddressRepository;

    //실종 등록
    @Override
    @Transactional
    public Long registerMissing(Register register) {

        register.setRegisterStatus(RegisterStatus.NOT_SOLVED);
        register.setReportedStatus(ReportedStatus.NO);

        String address = register.getMissingAddress().getStreetName();
        String prefecture = findPrefecture(address);
        System.out.println("prefecture = " + prefecture);

        String cityName = findCity(address);
        System.out.println("cityName = " + cityName);

        String Gu = findGu(address);
        System.out.println("Gu = " + Gu);

        String Dong = findDong(address);
        System.out.println("Dong = " + Dong);

        String streetName = findStreetName(address);
        System.out.println("streetName = " + streetName);

        String streetNumber = findStreetNumber(address);
        System.out.println("streetNumber = " + streetNumber);

        MissingAddress missingAddress = new MissingAddress();
        missingAddress.setPrefecture(prefecture);
        missingAddress.setCityName(cityName);
        missingAddress.setGu(Gu);
        missingAddress.setDong(Dong);
        missingAddress.setStreetName(streetName);
        missingAddress.setStreetNumber(streetNumber);
        missingAddress.setZipcode(register.getMissingAddress().getZipcode());

        register.setMissingAddress(missingAddress);

        Long saveId = registerRepository.save(register).getId();

        return saveId;

    }

    //실종 목록
    @Override
    @Transactional(readOnly = true)
    public List<Register> listingRegister() {

        List<Register> all = registerRepository.findAll();

        return all;
    }

    @Override
    public Page<Register> listingRegisterV2(Pageable pageable) {

        Page<Register> page = registerRepository.findAll(pageable);

        return page;

    }

    @Override
    @Transactional(readOnly = true)
    public Register findOne(Long id) {

        Register register = registerRepository.findById(id).get();

        return register;
    }

    @Override
    @Transactional // dirty checking 이용
    public Long updateForm(Long registerId, Register register) {

        Register findRegister = registerRepository.findById(registerId).get();

        if(register.getFileName() != null)
            findRegister.setFileName(register.getFileName());

        if(register.getAnimalName() != null)
            findRegister.setAnimalName(register.getAnimalName());

        if(register.getAnimalSex() != null)
            findRegister.setAnimalSex(register.getAnimalSex());

        if(register.getAnimalVariety() != null)
            findRegister.setAnimalVariety(register.getAnimalVariety());

        if(register.getAnimalWeight()!= null)
            findRegister.setAnimalWeight(register.getAnimalWeight());

        if(register.getEtc() != null)
            findRegister.setEtc(register.getEtc());

        if(register.getRegisterStatus() != null)
            findRegister.setRegisterStatus(register.getRegisterStatus());

        if(register.getRegisterStatus() != null)
            findRegister.setReportedStatus(register.getReportedStatus());

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

            List<MissingAddress> byCityName = missingAddressRepository.findByCityName(missingAddress.getCityName());

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



        Page<Register> page = registerRepository.searchRegistersWithPagingComplexV2(registerSearchCond,pageable);


        return page;

    }

    @Override
    public Page<Register> searchByRegisterCond2(RegisterSearchCond registerSearchCond, Pageable pageable) {

        String address = registerSearchCond.getStreetName();
        String prefecture = findPrefecture(address);
        registerSearchCond.setPrefecture(prefecture);
        System.out.println("prefecture = " + prefecture);

        String cityName = findCity(address);
        registerSearchCond.setCityName(cityName);
        System.out.println("cityName = " + cityName);

        String Gu = findGu(address);
        registerSearchCond.setGu(Gu);
        System.out.println("Gu = " + Gu);

        String Dong = findDong(address);
        registerSearchCond.setDong(Dong);
        System.out.println("Dong = " + Dong);

        String streetName = findStreetName(address);
        registerSearchCond.setStreetName(streetName);
        System.out.println("streetName = " + streetName);

        String streetNumber = findStreetNumber(address);
        registerSearchCond.setStreetNumber(streetNumber);
        System.out.println("streetNumber = " + streetNumber);


        Page<Register> page = registerRepository.searchRegistersWithPagingComplexV2(registerSearchCond,pageable);


        return page;

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
