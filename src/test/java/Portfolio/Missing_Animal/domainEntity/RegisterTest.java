package Portfolio.Missing_Animal.domainEntity;

import jakarta.persistence.EntityManager;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;


@SpringBootTest
@Transactional
class RegisterTest {

    @Autowired
    EntityManager em;

    @Test
    void 연관관계_메서드(){


      /*  Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setPassword("eoskan6523");
        em.persist(member1);

       *//* Member member2 = new Member();
        member2.setUsername("김진영2");
        member2.setPassword("eoskan6524");
        em.persist(member2);*//*


        Register register1 = new Register();
        register1.setMember(member1); // 연관 관계 메서드 사용(member1에도 register1가 [자동]으로 세팅돼 있다)
        register1.setAnimalName("사랑이1");
        em.persist(register1);

        Register register2 = new Register();
        register2.setMember(member1); // 연관 관계 메서드 사용(member1에도 register2가 [자동]으로 세팅돼 있다)
        register2.setAnimalName("사랑이2");
        em.persist(register2);


        Report report = new Report();

        register1.addReport(report);
        register2.addReport(report);

        em.persist(report);*/

        Register register1 = new Register();
        register1.setAnimalName("사랑이1");

        Register register2 = new Register();
        register2.setAnimalName("사랑이2");

        Member member1 = new Member();
        member1.setUsername("김진영1");
        member1.setPassword("eoskan6523");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김진영1");
        member2.setPassword("eoskan6523");
        em.persist(member2);


        register1.setMember(member1); // 연관 관계 메서드 사용(member1에도 양방향으로 register1을 가리키고 있음)
        register2.setMember(member2); // 연관 관계 메서드 사용(member2에도 양방향으로 register2을 가리키고 있음)

        // ==============================================================================================
        // 즉, 기존에는 연관 관계 주인에서만 다른 객체를 참조가 가능하였지만,
        // 관련된 모든 엔티티끼리 서로 양방향으로 연결이 되어 있으므로, 역참조가 가능하다.

        // register <-> member : 양방향으로 참조 가능!
        Member member = register1.getMember();
        assertThat(member.getUsername()).isEqualTo(member1.getUsername());

        List<Register> registers = member.getRegisters();
        for (Register register : registers) {

            assertThat(register.getMember().getUsername()).isEqualTo(member.getUsername());

        }

    }

    // [도/시]
    // [시/군/구]
    // [읍/면/동]
    // 도로명
    // 도로번호
    // 나머지 주소

    @Test
    @Rollback(value = false)
    void parsing(){

        String str  = "부산광역시 해운대구 해운대로 119";

        String prefecture = findPrefecture(str);
        System.out.println("prefecture = " + prefecture);

        String cityName = findCity(str);
        System.out.println("cityName = " + cityName);

        String Gu = findGu(str);
        System.out.println("Gu = " + Gu);

        String Dong = findDong(str);
        System.out.println("Dong = " + Dong);

        String streetName = findStreetName(str);
        System.out.println("streetName = " + streetName);

        String streetNumber = findStreetNumber(str);
        System.out.println("streetNumber = " + streetNumber);


    }


    public static String findPrefecture(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){
            //북/남/도

            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '북' || s.charAt(s.length()-1) == '남' || s.charAt(s.length()-1) == '도' )
                return s;

        }

        return null;

    }

    public static String findCity(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){
            // 시/군/면

            String s = st.nextToken();
            if(s.charAt(s.length()-1) != '북' && s.charAt(s.length()-1) != '남' && s.charAt(s.length()-1) != '도'
              && s.charAt(s.length()-1) != '군'  && s.charAt(s.length()-1) != '구'
                    && s.charAt(s.length()-1) != '읍' && s.charAt(s.length()-1) != '면' && s.charAt(s.length()-1) != '동'
                    && s.charAt(s.length()-1) != '0' && s.charAt(s.length()-1) != '1' && s.charAt(s.length()-1) != '2'
                    && s.charAt(s.length()-1) != '3' && s.charAt(s.length()-1) != '4' && s.charAt(s.length()-1) != '5'
                    && s.charAt(s.length()-1) != '6' && s.charAt(s.length()-1) != '7' && s.charAt(s.length()-1) != '8'
                    && s.charAt(s.length()-1) != '9')
                return s;

        }

        return null;

    }


    public static String findGu(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            //구

            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '구')
                return s;

        }

        return null;

    }

    public static String findDong(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // 읍/면/동
            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '읍' || s.charAt(s.length()-1) == '면' || s.charAt(s.length()-1) == '동')
                return s;

        }

        return null;

    }

    public static String findStreetName(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // ~로, ~길
            String s = st.nextToken();
            if(s.charAt(s.length()-1) == '로' || s.charAt(s.length()-1) == '길')
                return s;

        }

        return null;

    }


    public static String findStreetNumber(String str){


        StringTokenizer st = new StringTokenizer(str," ");

        while (st.hasMoreTokens()){

            // 0~9
            String s = st.nextToken();
            if(  s.charAt(s.length()-1) == '0' || s.charAt(s.length()-1) == '1' || s.charAt(s.length()-1) == '2'
                    || s.charAt(s.length()-1) == '3' || s.charAt(s.length()-1) == '4' || s.charAt(s.length()-1) == '5'
                    || s.charAt(s.length()-1) == '6' || s.charAt(s.length()-1) == '7' || s.charAt(s.length()-1) == '8'
                    || s.charAt(s.length()-1) == '9')
                return s;

        }

        return null;

    }

}




