package Portfolio.Missing_Animal.service;

import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    ReportService reportService;


    @Test
    void showingRegisterContentById() {
        // Register 엔티티의 id : 1 ~ 8

    for(int x = 1; x <= 8; x++) {

        Register register = reportService.showingRegisterContentById((long)x);

        assertThat(register.getAnimalName()).isEqualTo("사랑이"+x);

    }

    }

    @Test
    void listingMissingAnimalByMissingAddress() {

        MissingAddress missingAddress1 = new MissingAddress();
        missingAddress1.setCityName("천안");

        MissingAddress missingAddress2 = new MissingAddress();
        missingAddress2.setCityName("전주");

        List<MissingAddress> missingAddresses1 = reportService.ListingMissingAnimalByMissingAddress(missingAddress1);

            assertThat(missingAddresses1.size()).isEqualTo(2);
            assertThat(missingAddresses1.get(0).getCityName()).isEqualTo("천안시1");
            assertThat(missingAddresses1.get(1).getCityName()).isEqualTo("천안군1");

        List<MissingAddress> missingAddresses2 = reportService.ListingMissingAnimalByMissingAddress(missingAddress2);

        assertThat(missingAddresses2.size()).isEqualTo(2);
        assertThat(missingAddresses2.get(0).getCityName()).isEqualTo("전주시1");
        assertThat(missingAddresses2.get(1).getCityName()).isEqualTo("전주군1");

    }
}