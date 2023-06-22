package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domain.MissingAddress;
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

    @Autowired
    EntityManager em;

    @Autowired
    private MissingAddressRepository missingAddressRepository;

    static MissingAddress createMissingAddress(String prefecture,String zipcode,String cityName,String Gu,String Dong,String streetName){

        MissingAddress missingAddress = new MissingAddress();

        missingAddress.setPrefecture(prefecture);
        missingAddress.setZipcode(zipcode);
        missingAddress.setCityName(cityName);
        missingAddress.setGu(Gu);
        missingAddress.setDong(Dong);
        missingAddress.setStreetName(streetName);

        return missingAddress;


    }

    @BeforeEach
    void init(){

        //충청 남도
        MissingAddress missingAddress1 = createMissingAddress("충청남도","1-1-1","천안시1","천안구1","천안동1","천안대로1_1");
        em.persist(missingAddress1);
        MissingAddress missingAddress2 = createMissingAddress("충청남도","1-1-2","천안시2","천안구2","천안동2","천안대로1_2");
        em.persist(missingAddress2);

        // 충청 북도
        MissingAddress missingAddress3 = createMissingAddress("충청북도","1-2-1","천안군1","천안군1","천안읍1","천안대로2_1");
        em.persist(missingAddress3);
        MissingAddress missingAddress4 = createMissingAddress("충청북도","1-2-2","천안군2","천안군2","천안읍2","천안대로2_2");
        em.persist(missingAddress4);


        //전라 남도
        MissingAddress missingAddress5 = createMissingAddress("전라남도","1-3-1","전주시1","전주구1","천주동1","전주대로1_1");
        em.persist(missingAddress5);
        MissingAddress missingAddress6 = createMissingAddress("전라남도","1-3-2","전주시2","전주구2","천주동2","전주대로1_2");
        em.persist(missingAddress6);

        //전라 북도
        MissingAddress missingAddress7 = createMissingAddress("전라북도","1-4-1","전주군1","전주군1","천주읍1","전주대로2_1");
        em.persist(missingAddress7);

        MissingAddress missingAddress8 = createMissingAddress("전라북도","1-4-2","전주군2","전주군2","천주읍2","전주대로2_2");
        em.persist(missingAddress8);

        em.flush();
        em.clear();

    }

    @Test
    //@Rollback(false)
    void findByPrefecture(){

        //givien
        //충청 남도

        //when
        List<MissingAddress> 충청남도 = missingAddressRepository.findByPrefecture("충청남도");
        List<MissingAddress> 충청북도 = missingAddressRepository.findByPrefecture("충청북도");
        List<MissingAddress> 전라남도 = missingAddressRepository.findByPrefecture("전라남도");
        List<MissingAddress> 전라북도 = missingAddressRepository.findByPrefecture("전라북도");

        //then
        assertThat(충청남도.size()).isEqualTo(2);
        assertThat(충청북도.size()).isEqualTo(2);
        assertThat(전라남도.size()).isEqualTo(2);
        assertThat(전라북도.size()).isEqualTo(2);


    }

    @Test
    //@Rollback(false)
    void findByZipcode(){




        //when
        List<MissingAddress> byZipcode1 = missingAddressRepository.findByZipcode("1-1-1");
        List<MissingAddress> byZipcode2 = missingAddressRepository.findByZipcode("1-4-2");

        //then
        assertThat(byZipcode1.size()).isEqualTo(1);
        assertThat(byZipcode1.get(0).getCityName()).isEqualTo("천안시1");


        assertThat(byZipcode2.size()).isEqualTo(1);
        assertThat(byZipcode2.get(0).getCityName()).isEqualTo("전주군2");

    }


    @Test
    //@Rollback(false)
    void findByCityName() { // ~[시/군]


        //when

        List<MissingAddress> 천안 = missingAddressRepository.findByCityName("천안");
        List<MissingAddress> 전주 = missingAddressRepository.findByCityName("전주");

        //then
        assertThat(천안.size()).isEqualTo(4);
        for(MissingAddress a:천안){

            System.out.println(a.getCityName());

        }

        assertThat(전주.size()).isEqualTo(4);
        for(MissingAddress a:전주){

            System.out.println(a.getCityName());

        }



    }

    @Test
    //@Rollback(false)
    void findByGu() {


        //when
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

        }

    }

    @Test
   // @Rollback(false)
    void findByStreetName() {

        //when
        List<MissingAddress> 천안대로 = missingAddressRepository.findByStreetName("천안대로1_1");


        //then
        assertThat(천안대로.size()).isEqualTo(1);
        assertThat(천안대로.get(0).getStreetName()).isEqualTo("천안대로1_1");
    }


}