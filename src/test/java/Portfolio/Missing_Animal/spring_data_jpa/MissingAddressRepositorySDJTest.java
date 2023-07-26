package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MissingAddressRepositorySDJTest {


    @Autowired
    MissingAddressRepositorySDJ missingAddressRepository;


    @Test
    void basicCRUD(){

        //SAVE
        MissingAddress address1 = new MissingAddress();
        MissingAddress address2= new MissingAddress();

        MissingAddress savedRegister1 = missingAddressRepository.save(address1);
        MissingAddress savedRegister2 = missingAddressRepository.save(address2);


        //findById(단건 조회)
        MissingAddress byId1 = missingAddressRepository.findById(savedRegister1.getId()).get();
        MissingAddress byId2 = missingAddressRepository.findById(savedRegister2.getId()).get();
        assertThat(byId1).isEqualTo(address1);
        assertThat(byId2).isEqualTo(address2);


        //findAll
        List<MissingAddress> all = missingAddressRepository.findAll();
        assertThat(all.size()).isEqualTo(6L);


        //count
        long count = missingAddressRepository.count();
        assertThat(count).isEqualTo(6L);


        //delete
        missingAddressRepository.delete(address1);
        missingAddressRepository.delete(address2);

        long deletedCount = missingAddressRepository.count();
        assertThat(deletedCount).isEqualTo(4L);

    }

    @Test
    void countMissingAddressById(){

        long count = missingAddressRepository.countMissingAddressBy();

        assertThat(count).isEqualTo(4L);

    }

    @Test
    void findMissingAddressById(){

        //givien
        MissingAddress address = new MissingAddress();
        missingAddressRepository.save(address);

        //when
        MissingAddress missingAddressById = missingAddressRepository.findById(address.getId()).get();


        //then
        assertThat(address).isEqualTo(missingAddressById); // 동일성

    }

    @Test
    void findMissingAddressByZipcode(){

        //givien
        MissingAddress address = new MissingAddress();
        address.setZipcode("123-123");
        missingAddressRepository.save(address);

        //when
        List<MissingAddress> missingAddressByZipcode = missingAddressRepository.findByZipcode("123-123");


        //then
        assertThat(missingAddressByZipcode.size()).isEqualTo(1L);
        assertThat(missingAddressByZipcode.get(0).getZipcode()).isEqualTo(address.getZipcode());

    }

    @Test
    void findMissingAddressByGu(){

        //givien
        MissingAddress address = new MissingAddress();
        address.setGu("동래구");
        missingAddressRepository.save(address);

        //when
        List<MissingAddress> 동래구 = missingAddressRepository.findByGu("동래");

        //then
        assertThat(동래구.size()).isEqualTo(1L);
        assertThat(동래구.get(0)).isEqualTo(address); // 동일성


    }

    @Test
    void findMissingAddressByCityName(){

        //givien
        MissingAddress address = new MissingAddress();
        address.setCityName("부산");
        missingAddressRepository.save(address);

        //when
        List<MissingAddress> 부산 = missingAddressRepository.findByCityName("부산");

        //then
        assertThat(부산.size()).isEqualTo(1L);
        assertThat(부산.get(0).getCityName()).isEqualTo(address.getCityName());

    }

    @Test
    void findMissingAddressByPrefecture(){

        //givien
        MissingAddress address = new MissingAddress();
        address.setPrefecture("경상도");
        missingAddressRepository.save(address);

        //when
        List<MissingAddress> 경상도 = missingAddressRepository.findByPrefecture("경상도");


        //then
        assertThat(경상도.size()).isEqualTo(1L);
        assertThat(경상도.get(0).getPrefecture()).isEqualTo(address.getPrefecture());


    }

    @Test
    void findMissingAddressByStreetName(){

        //givien
        MissingAddress address = new MissingAddress();
        address.setStreetName("스프링대로");
        missingAddressRepository.save(address);

        //when
        List<MissingAddress> 스프링대로 = missingAddressRepository.findByStreetName("스프링대로");


        //then
        assertThat(스프링대로.size()).isEqualTo(1L);
        assertThat(스프링대로.get(0).getStreetName()).isEqualTo(address.getStreetName());

    }


    @Test
    void findAll(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findAll(pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(2L);
        assertThat(totalElements).isEqualTo(4L);
        assertThat(isNextPage).isTrue();

    }


    @Test
    void findMissingAddressByPrefectureWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findByPrefecture("충청남도",pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(1L);
        assertThat(isNextPage).isFalse();

    }


    @Test
    void findMissingAddressByZipcodeWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findByZipcode("1-1-1",pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(1L);
        assertThat(isNextPage).isFalse();

    }

    @Test
    void findMissingAddressByCityNameWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findByCityName("천안시1",pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(1L);
        assertThat(isNextPage).isFalse();

    }

    @Test
    void findMissingAddressByGuWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findByGu("천안구1",pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(1L);
        assertThat(isNextPage).isFalse();

    }

    @Test
    void findMissingAddressByStreetNameWithPaging(){


        //when
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<MissingAddress> page = missingAddressRepository.findByStreetName("천안대로1_1",pageRequest);


        List<MissingAddress> content = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean isNextPage = page.hasNext();

        //then
        for(MissingAddress m : content){

            System.out.println(m);

        }

        assertThat(totalPages).isEqualTo(1L);
        assertThat(totalElements).isEqualTo(1L);
        assertThat(isNextPage).isFalse();

    }


}