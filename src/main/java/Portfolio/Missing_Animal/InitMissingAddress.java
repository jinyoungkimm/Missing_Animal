package Portfolio.Missing_Animal;


import Portfolio.Missing_Animal.domainEntity.Member;
import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.enumType.RegisterStatus;
import Portfolio.Missing_Animal.enumType.ReportedStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
        createRegister(missingAddress1, "김진영1","wlsdud6523","사랑이1", RegisterStatus.SOLVED,ReportedStatus.YES,
                "사랑이2" ,RegisterStatus.NOT_SOLVED,ReportedStatus.NO);

        // 충청 북도
        MissingAddress missingAddress3 = createMissingAddress("충청북도","1-2-1","천안군1","천안군1","천안읍1","천안대로2_1");
        createRegister(missingAddress3, "김진영2","wlsdud6524","사랑이3",RegisterStatus.SOLVED,ReportedStatus.YES,
                "사랑이4",RegisterStatus.NOT_SOLVED,ReportedStatus.NO);

        //전라 남도
        MissingAddress missingAddress5 = createMissingAddress("전라남도","1-3-1","전주시1","전주구1","전주동1","전주대로1_1");
        createRegister(missingAddress5, "김진영3","wlsdud6525","사랑이5",RegisterStatus.SOLVED,ReportedStatus.YES,
                "사랑이6",RegisterStatus.NOT_SOLVED,ReportedStatus.NO);


        //전라 북도
        MissingAddress missingAddress7 = createMissingAddress("전라북도","1-4-1","전주군1","전주군1","전주읍1","전주대로2_1");
        createRegister(missingAddress7, "김진영4","wlsdud6526","사랑이7",RegisterStatus.SOLVED,ReportedStatus.YES,
                "사랑이8",RegisterStatus.NOT_SOLVED,ReportedStatus.NO);
    }
    public  void createRegister(MissingAddress missingAddress, String userName,  String userId,String animalName1, RegisterStatus registerStatus1 , ReportedStatus reportedStatus1,
                                String animalName2, RegisterStatus registerStatus2 , ReportedStatus reportedStatus2
                                ) {


                // Member : Register : MissingAddress = 1 : 2 : 1 [ 1 : 다 :1]을 표현함
                em.persist(missingAddress);

                Member member = new Member();
                member.setUserId(userId);
                member.setUsername(userName);
                em.persist(member);

                Register register1 = new Register();
                register1.setAnimalName(animalName1);
                register1.setMissingAddress(missingAddress);
                register1.setMember(member);
                register1.setRegisterStatus(registerStatus1);
                register1.setReportedStatus(reportedStatus1);
                em.persist(register1);

                Register register2 = new Register();
                register2.setAnimalName(animalName2);
                register2.setMissingAddress(missingAddress);
                register2.setMember(member);
                register2.setRegisterStatus(registerStatus2);
                register2.setReportedStatus(reportedStatus2);
                em.persist(register2);


                //report : Register == 1 : 2
                AddressForm addressForm1 = new AddressForm("1","2","3");
                Report report1 = new Report();
                report1.setRegister(register1);
                report1.setMember(member);
                report1.setFindedAddress(addressForm1);
                em.persist(report1);

                AddressForm addressForm2 = new AddressForm("4","5","6");
                Report report2 = new Report();
                report2.setRegister(register1);
                report2.setMember(member);
                report2.setFindedAddress(addressForm2);
                em.persist(report2);

                AddressForm addressForm3 = new AddressForm("7","8","9");
                Report report3 = new Report();
                report3.setRegister(register2);
                report3.setMember(member);
                report3.setFindedAddress(addressForm3);
                em.persist(report3);

                AddressForm addressForm4 = new AddressForm("10","11","12");
                Report report4 = new Report();
                report4.setRegister(register2);
                report4.setMember(member);
                report4.setFindedAddress(addressForm4);
                em.persist(report4);


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
