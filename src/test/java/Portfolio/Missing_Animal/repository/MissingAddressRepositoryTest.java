package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.repository.repositoryinterface.MissingAddressRepository;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MissingAddressRepositoryTest {



   // @Autowired
   // private MissingAddressRepository missingAddressRepository; // 순수 JPA Repository

    @Autowired
    MissingAddressRepositorySDJ missingAddressRepository;



    @Test
    void delete(){
        // 순수 JPA Repository
        /*//givien
        MissingAddress finded1 = missingAddressRepository.findById(1L);

        //when
        missingAddressRepository.delete(finded1);
        MissingAddress finded2 = missingAddressRepository.findById(1L);

        //then

        fail("예외가 터졌어야 했다.");*/

        //Spring Data JPA
        //givien
        MissingAddress finded1 = missingAddressRepository.findById(1L).get();

        //when
        missingAddressRepository.delete(finded1);
        MissingAddress finded2 = missingAddressRepository.findById(1L).get();

        //then

        fail("예외가 터졌어야 했다.");
    }

    @Test
    void count() {

        // 순수 JPA Repository
        /*long count = missingAddressRepository.count();

        assertThat(count).isEqualTo(4L);*/

        //Spring Data JPA
        long count = missingAddressRepository.count();

        assertThat(count).isEqualTo(4L);

    }

    @Test
    //@Rollback(false)
    void findByPrefecture(){

        // 순수 JPA Repository
       /* //givien
        //충청 남도

        //when
        List<MissingAddress> 충청 = missingAddressRepository.findByPrefecture("충청");
        List<MissingAddress> 충청북도 = missingAddressRepository.findByPrefecture("충청북도");
        List<MissingAddress> 전라남도 = missingAddressRepository.findByPrefecture("전라남도");
        List<MissingAddress> 전라북도 = missingAddressRepository.findByPrefecture("전라북도");

        //then
        assertThat(충청.size()).isEqualTo(2);
        assertThat(충청북도.size()).isEqualTo(1);
        assertThat(전라남도.size()).isEqualTo(1);
        assertThat(전라북도.size()).isEqualTo(1);*/

        //Spring Data JPA
        //givien
        //충청 남도

        //when
        List<MissingAddress> 충청 = missingAddressRepository.findByPrefecture("충청");
        List<MissingAddress> 충청북도 = missingAddressRepository.findByPrefecture("충청북도");
        List<MissingAddress> 전라남도 = missingAddressRepository.findByPrefecture("전라남도");
        List<MissingAddress> 전라북도 = missingAddressRepository.findByPrefecture("전라북도");

        //then
        assertThat(충청.size()).isEqualTo(2);
        assertThat(충청북도.size()).isEqualTo(1);
        assertThat(전라남도.size()).isEqualTo(1);
        assertThat(전라북도.size()).isEqualTo(1);
    }

    @Test
    //@Rollback(false)
    void findByZipcode(){


        // 순수 JPA Repository
     /*   //when
        List<MissingAddress> byZipcode1 = missingAddressRepository.findByZipcode("1-1-1");
        List<MissingAddress> byZipcode2 = missingAddressRepository.findByZipcode("1-4-2");

        //then
        assertThat(byZipcode1.size()).isEqualTo(1);
        assertThat(byZipcode1.get(0).getCityName()).isEqualTo("천안시1");


        assertThat(byZipcode2.size()).isEqualTo(1);
        assertThat(byZipcode2.get(0).getCityName()).isEqualTo("전주군2");*/


        //Spring Data JPA
        //when
        List<MissingAddress> byZipcode1 = missingAddressRepository.findByZipcode("1-1-1");


        //then
        assertThat(byZipcode1.size()).isEqualTo(1);
        assertThat(byZipcode1.get(0).getCityName()).isEqualTo("천안시1");


    }


    @Test
    //@Rollback(false)
    void findByCityName() { // ~[시/군]

        // 순수 JPA Repository
      /*  //when

        List<MissingAddress> 천안 = missingAddressRepository.findByCityName("천안");
        List<MissingAddress> 전주 = missingAddressRepository.findByCityName("전주");


        //then
        assertThat(천안.size()).isEqualTo(2);
        for(MissingAddress a:천안){

            System.out.println(a.getCityName());

        }

        assertThat(전주.size()).isEqualTo(2);
        for(MissingAddress a:전주){

            System.out.println(a.getCityName());

        }*/


        //Spring Data JPA
        //when

        List<MissingAddress> 천안 = missingAddressRepository.findByCityName("천안");
        List<MissingAddress> 전주 = missingAddressRepository.findByCityName("전주");


        //then
        assertThat(천안.size()).isEqualTo(2);
        for (MissingAddress a : 천안) {

            System.out.println(a.getCityName());

        }

        assertThat(전주.size()).isEqualTo(2);
        for (MissingAddress a : 전주) {

            System.out.println(a.getCityName());
        }
    }
    @Test
    //@Rollback(false)
    void findByGu() {

        // 순수 JPA Repository
      /*  //when
        List<MissingAddress> 천안 = missingAddressRepository.findByGu("천안");
        List<MissingAddress> 전주 = missingAddressRepository.findByGu("전주");


        //then
        assertThat(천안.size()).isEqualTo(4);
        for(MissingAddress a:천안){

            System.out.println(a.getGu());

        }

        assertThat(전주.size()).isEqualTo(4);
        for(MissingAddress a:전주){

            System.out.println(a.getGu());

        }*/


        //Spring Data JPA
        //when
        List<MissingAddress> 천안 = missingAddressRepository.findByGu("천안");
        List<MissingAddress> 전주 = missingAddressRepository.findByGu("전주");


        //then
        assertThat(천안.size()).isEqualTo(2);
        for(MissingAddress a:천안){

            System.out.println(a.getGu());

        }

        assertThat(전주.size()).isEqualTo(2);
        for(MissingAddress a:전주){

            System.out.println(a.getGu());

        }
    }

    @Test
   // @Rollback(false)
    void findByStreetName() {

        // 순수 JPA Repository
       /* //when
        List<MissingAddress> 천안대로 = missingAddressRepository.findByStreetName("천안대로1_1");


        //then
        assertThat(천안대로.size()).isEqualTo(1);
        assertThat(천안대로.get(0).getStreetName()).isEqualTo("천안대로1_1");*/


        //Spring Data JPA
        //when
        List<MissingAddress> 천안대로 = missingAddressRepository.findByStreetName("천안대로1");


        //then
        assertThat(천안대로.size()).isEqualTo(1);
        assertThat(천안대로.get(0).getStreetName()).isEqualTo("천안대로1_1");

    }

}