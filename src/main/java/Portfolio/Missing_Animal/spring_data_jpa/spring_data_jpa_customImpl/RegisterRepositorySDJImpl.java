package Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_customImpl;


import Portfolio.Missing_Animal.domainEntity.QRegister;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.RegisterSearchCond;
import Portfolio.Missing_Animal.spring_data_jpa.spring_data_jpa_custom.RegisterRepositorySDJCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public Page<Tuple> searchRegistersWithPagingSimple(RegisterSearchCond registerSearchCond, Pageable pageable) {

        //PageRequest(Pageable의 구현체)의 pageNumber와 size만으로, offset과 limit이 자동 계산이 된다.
        long offset = pageable.getOffset();
        int limit = pageable.getPageSize();

        QueryResults<Tuple> results = query.select(register, missingAddress)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber()))

                .offset(offset)

                .limit(limit)

                .fetchResults();// 내용 조회와 count 쿼리를 한번에 조회!!

        List<Tuple> content = results.getResults();
        long totalElementCount = results.getTotal();

        return new PageImpl<>(content,pageable,totalElementCount);

    }

    @Override
    public Page<Tuple> searchRegistersWithPagingComplexV1(RegisterSearchCond registerSearchCond,Pageable pageable) {

        /**
         * 내용 쿼리와 count 쿼리를 분리하면 코드 가독성이 좋다.
         */
        long offset = pageable.getOffset();
        int limit = pageable.getPageSize();

        List<Tuple> content = query
                .select(register, missingAddress)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber()))

                .offset(offset)

                .limit(limit)

                .fetch();// 내용만 조회!

        long totalElementsCount = query.select(register)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber()))

                .fetchCount();// count 쿼리 분리!

        return new PageImpl<>(content,pageable,totalElementsCount);

    }

    @Override
    public Page<Tuple> searchRegistersWithPagingComplexV2(RegisterSearchCond registerSearchCond,Pageable pageable){

        /**
         * countQuery를 PageableExcutions.getpage()를 이용하여 분리!
         */

        long offset = pageable.getOffset();
        int limit = pageable.getPageSize();

        List<Tuple> content = query
                .select(register, missingAddress)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber()))

                .offset(offset)

                .limit(limit)

                .fetch();// 내용만 조회!


        JPAQuery<Tuple> countQuery = query
                .select(register, missingAddress)

                .from(register)

                .leftJoin(register.missingAddress, missingAddress)

                .where(animalNameEq(registerSearchCond.getAnimalName()),
                        animalSexEq(registerSearchCond.getAnimalSex()),
                        animalAgeEq(registerSearchCond.getAnimalAge()),
                        animalVarietyEq(registerSearchCond.getAnimalVariety()),
                        prefectureEq(registerSearchCond.getPrefecture()),
                        cityNameEq(registerSearchCond.getCityName()),
                        guEq(registerSearchCond.getGu()),
                        DongEq(registerSearchCond.getDong()),
                        streetNameEq(registerSearchCond.getStreetName()),
                        streetNumberEq(registerSearchCond.getStreetNumber()));

        return PageableExecutionUtils.getPage(content,pageable,
                countQuery::fetchCount); // 여기에서 fetchCount() 실행!

    }


    // 아래 함수들은 [재사용] 가능!
    private BooleanExpression animalNameEq(String animalname){

        return isEmpty(animalname) ? null : register.animalName.contains(animalname); // == (%animalname%)
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
