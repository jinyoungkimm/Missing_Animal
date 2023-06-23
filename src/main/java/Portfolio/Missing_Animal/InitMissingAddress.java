package Portfolio.Missing_Animal;


import Portfolio.Missing_Animal.domain.Member;
import Portfolio.Missing_Animal.domain.MissingAddress;
import Portfolio.Missing_Animal.domain.Register;
import Portfolio.Missing_Animal.domain.Report;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMissingAddress {

   private final EntityManager em;


    @Transactional
    public void init(){

        //충청 남도(사랑이1,2를 똑같은 주소에서 실종했다고 가정)
        MissingAddress missingAddress1 = createMissingAddress("충청남도","1-1-1","천안시1","천안구1","천안동1","천안대로1_1");
        createRegister(missingAddress1, "김진영1","사랑이1");
       // MissingAddress missingAddress2 = createMissingAddress("충청남도","1-1-2","천안시2","천안구2","천안동2","천안대로1_2");
        createRegister(missingAddress1, "김진영2","사랑이2");




        // 충청 북도
        MissingAddress missingAddress3 = createMissingAddress("충청북도","1-2-1","천안군1","천안군1","천안읍1","천안대로2_1");
        createRegister(missingAddress3, "김진영3","사랑이3");
        //MissingAddress missingAddress4 = createMissingAddress("충청북도","1-2-2","천안군2","천안군2","천안읍2","천안대로2_2");
        createRegister(missingAddress3, "김진영4","사랑이4");


        //전라 남도
        MissingAddress missingAddress5 = createMissingAddress("전라남도","1-3-1","전주시1","전주구1","천주동1","전주대로1_1");
        createRegister(missingAddress5, "김진영5","사랑이5");
       // MissingAddress missingAddress6 = createMissingAddress("전라남도","1-3-2","전주시2","전주구2","천주동2","전주대로1_2");
        createRegister(missingAddress5, "김진영6","사랑이6");

        //전라 북도
        MissingAddress missingAddress7 = createMissingAddress("전라북도","1-4-1","전주군1","전주군1","천주읍1","전주대로2_1");
        createRegister(missingAddress7, "김진영7","사랑이7");
        //MissingAddress missingAddress8 = createMissingAddress("전라북도","1-4-2","전주군2","전주군2","천주읍2","전주대로2_2");
        createRegister(missingAddress7, "김진영8","사랑이8");

    }
    public  Register createRegister(MissingAddress missingAddress,String userName,String animalName) {

                em.persist(missingAddress);

                Member member = new Member();
                member.setUsername(userName);
                em.persist(member);


                Register register = new Register();
                register.setAnimalName(animalName);
                register.setMissingAddress(missingAddress);
                register.setMember(member);
                em.persist(register);

                return register;

            }

            public  MissingAddress createMissingAddress(String prefecture,String zipcode,String cityName,String Gu,String Dong,String streetName){

                MissingAddress missingAddress = new MissingAddress();

                missingAddress.setPrefecture(prefecture);
                missingAddress.setZipcode(zipcode);
                missingAddress.setCityName(cityName);
                missingAddress.setGu(Gu);
                missingAddress.setDong(Dong);
                missingAddress.setStreetName(streetName);

                return missingAddress;


            }

        }
