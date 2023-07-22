package Portfolio.Missing_Animal.restAPI.APIService;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.SolvedIncidentDto;

public interface RegisterRestApiService {

    // Register 등록 API
    public Long registerApi(Register register);

    // 총 등록 Register 수와 그 중 해결된 Register 수 반환!
    public SolvedIncidentDto countAllRegisters();


}
