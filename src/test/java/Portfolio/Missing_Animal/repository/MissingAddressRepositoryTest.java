package Portfolio.Missing_Animal.repository;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MissingAddressRepositoryTest {


    @Autowired
    MissingAddressRepositorySDJ missingAddressRepository;



    @Test
    void delete(){


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


        //Spring Data JPA
        long count = missingAddressRepository.count();

        assertThat(count).isEqualTo(4L);

    }

    @Test
    void findByPrefecture(){


        //Spring Data JPA
        //givien


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
    void findByPrefectureWithPaging(){


        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByPrefecture("충청",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(2L);
        assertThat(isNextPage).isFalse();

    }

    @Test
    void findByZipcode(){


        //Spring Data JPA
        //when
        List<MissingAddress> byZipcode1 = missingAddressRepository.findByZipcode("1-1-1");


        //then
        assertThat(byZipcode1.size()).isEqualTo(1);
        assertThat(byZipcode1.get(0).getCityName()).isEqualTo("천안시1");


    }

    @Test
    void ffindByZipcodeWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByZipcode("1-",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(2L);

        assertThat(totalElements).isEqualTo(4L);

        assertThat(isNextPage).isTrue();


    }

    @Test
    void findByCityName() { // ~[시/군]


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
    void findByCityNameWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByCityName("천안",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(1L);

        assertThat(totalElements).isEqualTo(2L);

        assertThat(isNextPage).isFalse();

    }


    @Test
    void findByGu() {


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
    void findByGuWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByGu("전주",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(1L);

        assertThat(totalElements).isEqualTo(2L);

        assertThat(isNextPage).isFalse();

    }

    @Test
    void findByDong(){

        List<MissingAddress> 천안 = missingAddressRepository.findByDong("천안");


        for (MissingAddress missingAddress : 천안) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(천안.size()).isEqualTo(2L);

    }

    @Test
    void findByDongWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByDong("전주",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(1L);

        assertThat(totalElements).isEqualTo(2L);

        assertThat(isNextPage).isFalse();

    }


    @Test
    void findByStreetName() {


        //Spring Data JPA
        //when
        List<MissingAddress> 천안대로 = missingAddressRepository.findByStreetName("천안대로1");


        //then
        assertThat(천안대로.size()).isEqualTo(1);
        assertThat(천안대로.get(0).getStreetName()).isEqualTo("천안대로1_1");

    }

    @Test
    void findByStreetNameWithPaging(){

        //givien
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<MissingAddress> page = missingAddressRepository.findByStreetName("전주",pageRequest);

        List<MissingAddress> content = page.getContent();

        int totalPages = page.getTotalPages();

        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();
        //then
        for (MissingAddress missingAddress : content) {
            System.out.println("missingAddress = " + missingAddress);
        }

        assertThat(totalPages).isEqualTo(1L);

        assertThat(totalElements).isEqualTo(2L);

        assertThat(isNextPage).isFalse();



    }

}