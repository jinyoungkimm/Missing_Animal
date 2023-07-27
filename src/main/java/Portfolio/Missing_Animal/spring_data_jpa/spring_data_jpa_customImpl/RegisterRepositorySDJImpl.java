package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_customImpl;


import Portfolio.Missing_Animal.domainEntity.QRegister;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_custom.RegisterRepositorySDJCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import static Portfolio.Missing_Animal.domainEntity.QRegister.register;
import static Portfolio.Missing_Animal.domainEntity.QMissingAddress.missingAddress;
import static org.springframework.util.StringUtils.isEmpty;


import java.util.List;

public class RegisterRepositorySDJImpl implements RegisterRepositorySDJCustom {

    private final JPAQueryFactory query; // 생성자 주입!

    public RegisterRepositorySDJImpl(EntityManager em){

        query = new JPAQueryFactory(em);

    }

    /***
     * // 프로퍼티 접근 법
     *List<MemberDto> result = queryFactory
     *  .select(Projections.bean(MemberDto.class,
     *  member.username,
     *  member.age))
     *  .from(member)
     *  .fetch()
     * // 필드 직접 접근 법
     *List<MemberDto> result = queryFactory
     *  .select(Projections.fields(MemberDto.class,
     *  member.username,
     *  member.age))
     *  .from(member)
     *  .fetch();
     * // 생성자 접근 법
     *List<MemberDto> result = queryFactory
     *  .select(Projections.constructor(MemberDto.class,
     *  member.username,
     *  member.age))
     *  .from(member)
     *  .fetch();
     *
     *
     *
     */

    @Override
    public List<Tuple> searchRegisters(RegisterSearchCond registerSearchCond) {


        List<Tuple> fetch = query

                .select(register, missingAddress)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                //WHERE 다중 파라미터로 동적 쿼리 작성(null은 WHERE 조건문에서 빠진다)
                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber())
                )
                .fetch();

        return fetch;

    }

    @Override
    public Page<Tuple> searchRegistersWithPaging(RegisterSearchCond registerSearchCond) {




    }



    // 아래 함수들은 [재사용] 가능!
    private BooleanExpression animalNameEq(String animalname){

        return isEmpty(animalname) ? null : register.animalName.contains(animalname);
    }

    private BooleanExpression animalSexEq(String animalSex){

        return isEmpty(animalSex) ? null : register.animalSex.eq(animalSex);

    }

    private BooleanExpression animalAgeEq(String animalAge){

        return isEmpty(animalAge) ? null : register.animalAge.eq(animalAge);

    }

    private BooleanExpression animalVarietyEq(String animalVariety){

        return isEmpty(animalVariety) ? null : register.animalVariety.contains(animalVariety);

    }


    private BooleanExpression prefectureEq(String prefecture){

        return isEmpty(prefecture) ? null : missingAddress.prefecture.contains(prefecture);

    }

    private BooleanExpression cityNameEq(String cityName){

        return isEmpty(cityName) ? null : missingAddress.cityName.contains(cityName);

    }

    private BooleanExpression guEq(String gu){

        return isEmpty(gu) ? null : missingAddress.gu.contains(gu);

    }

    private BooleanExpression DongEq(String Dong){

    return isEmpty(Dong) ? null : missingAddress.Dong.contains(Dong);

    }

    private BooleanExpression streetNameEq(String streetName){

        return isEmpty(streetName) ? null : missingAddress.streetName.contains(streetName);

    }

    private BooleanExpression streetNumberEq(String streetNumber){

        return isEmpty(streetNumber) ? null : missingAddress.streetNumber.contains(streetNumber);

    }

    private BooleanExpression zipcodeEq(String zipcode){

        return isEmpty(zipcode) ? null : missingAddress.zipcode.contains(zipcode);

    }

}
