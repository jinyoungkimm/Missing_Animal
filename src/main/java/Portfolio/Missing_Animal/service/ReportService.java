package Portfolio.Missing_Animal.service;


import Portfolio.Missing_Animal.repository.MissingAddressRepository;
import Portfolio.Missing_Animal.repository.repositoryinterface.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    // 등록자 정보, 실종 등록 내역을 조회하기 위함!
    private final RegisterRepository registerRepository;

    // 실종동물을 [주소]로 검색하여서 조회하고 싶을 때!
    private final MissingAddressRepository missingAddressRepository;






}
