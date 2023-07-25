package Portfolio.Missing_Animal.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.dto.RegisterDto;
import Portfolio.Missing_Animal.dto.ReportDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RegisterQueryRepositoryTest {

    @Autowired
    RegisterQueryRepository repository;


    @Test
    void findAllRegisters() {

        //when
        List<Register> allRegisters = repository.findAllRegisters();

        //then
         for(int x = 1; x <= allRegisters.size();x++){

          assertThat(allRegisters.get(x-1).getAnimalName()).isEqualTo("사랑이"+x);

      }
    }

    @Test
    void findAllRegisters2() {

        //when
        List<RegisterDto> allRegisters = repository.findAllRegisters2();

        assertThat(allRegisters.size()).isEqualTo(8);

        //then
        for(int x = 0; x < allRegisters.size();x++){

            assertThat(allRegisters.get(x).getAnimalName()).isEqualTo("사랑이"+(x+1));
            System.out.println(allRegisters.get(x).getReports().get(0).getRegisterId());
            System.out.println(allRegisters.get(x).getReports().get(1).getRegisterId());

        }

    }


    @Test
    void findRegisterWithId() {

        //givien
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        int offset = 0;
        int limit = Integer.MAX_VALUE;
        //when
        Register registerWithId1 = repository.findRegisterWithOneId(id1);
        Register registerWithId2 = repository.findRegisterWithOneId(id2);
        Register registerWithId3 = repository.findRegisterWithOneId(id3);

        //then
        assertThat(registerWithId1.getAnimalName()).isEqualTo("사랑이1");
        assertThat(registerWithId2.getAnimalName()).isEqualTo("사랑이2");
        assertThat(registerWithId3.getAnimalName()).isEqualTo("사랑이3");


    }

    @Test
    void findedAddress(){


        List<RegisterDto> allRegisters2 = repository.findAllRegisters2();
        for (RegisterDto registerDto : allRegisters2) {
            List<ReportDto> reports = registerDto.getReports();
            for (ReportDto report : reports) {
                System.out.println(report.getFindedAddress());
            }
        }

        RegisterDto registerWithOneId2 = repository.findRegisterWithOneId2(1L);
        List<ReportDto> reports = registerWithOneId2.getReports();
        for (ReportDto report : reports) {
            System.out.println(report.getFindedAddress());
        }



    }
}