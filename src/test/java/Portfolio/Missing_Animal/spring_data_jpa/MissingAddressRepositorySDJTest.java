package Portfolio.Missing_Animal.spring_data_jpa;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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






}