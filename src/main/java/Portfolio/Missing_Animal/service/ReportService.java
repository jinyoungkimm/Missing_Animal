package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService { // 신고 관련 기능

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    // 실종동물을 [주소]로 검색하여서 조회하고 싶을 때!
    private final MissingAddressRepository missingAddressRepository;

    // 실종 리스트 페이지에 있는 실종 등록된 리스트를 클릭을 하면 id값을 전달받아,즉 Register 엔티티의 id을 전달받아 실종 등록 내역을 조회한다.
    @Transactional(readOnly = true)
    public Register showingRegisterContentById(Long id){

        Register byAnimalId = registerRepository.findByAnimalId(id);

        if(byAnimalId == null)
            throw new IllegalStateException("해당 id로 조회되는 실종 등록 내용은 업습니다.");

        return byAnimalId;
    }



    @Transactional(readOnly = true)
    public List<MissingAddress> ListingMissingAnimalByMissingAddress(MissingAddress missingAddress){

        // [도시명]만 입력된 경우!
        // [도시명] + [구/군]이 입력된 경우
        // [도시명] + [구/군] + [동/읍/리]가 입력된 경우
        // [도시명] + [구/군] + [동/읍/리] + [도로명]이 입력된 경우!
        // -> 추후에 Querydsl로 MissingAddressRepository에서 동적 쿼리로 구현을 하자!!!
        // 지금은 [도시명], [구/군], [우편번호] 중 1개만으로 조회를 하자!
        if(missingAddress.getCityName() != null){

            List<MissingAddress> byCityName = missingAddressRepository.findByCityName(missingAddress.getCityName());

            if(byCityName.isEmpty() == true)
                throw new IllegalStateException("해당 도시에는 실종 등록된 동물이 없습니다.");





            return byCityName;
        }

        else if(missingAddress.getStreetName() != null){

            List<MissingAddress> byStreetName = missingAddressRepository.findByStreetName(missingAddress.getStreetName());

            if(byStreetName.isEmpty() == true)
                throw new IllegalStateException("해당 도로명에는 실종 등록된 동물이 없습니다.");

            return byStreetName;
        }

        else if(missingAddress.getZipcode() != null)
        {
            List<MissingAddress> byZipcode = missingAddressRepository.findByZipcode(missingAddress.getZipcode());

            if(byZipcode.isEmpty() == true)
                throw new IllegalStateException("해당 우편번호에 실종 등록된 동물이 없습니다.");

            return byZipcode;

        }
        else
            throw new IllegalStateException("도시명 or 도로명 or 우편번호를 입력하세요");

    }
}
