package Portfolio.Missing_Animal.propertiesWithJava.QueryrestApi.queryrepository;

import Portfolio.Missing_Animal.domainEntity.MissingAddress;
import Portfolio.Missing_Animal.domainEntity.QMissingAddress;
import Portfolio.Missing_Animal.domainEntity.Register;
import Portfolio.Missing_Animal.dto.*;
import Portfolio.Missing_Animal.spring_data_jpa.MissingAddressRepositorySDJ;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import static  Portfolio.Missing_Animal.domainEntity.QMissingAddress.missingAddress;
import static  Portfolio.Missing_Animal.domainEntity.QRegister.register;
import static org.springframework.util.StringUtils.isEmpty;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  컬렉션 최적화
 *  -> [1:다] 관계에서의 [다] 조회, 즉 컬렉션을 new 연산자로 [직접] DTO로 반환받기!!(내 포폴에서는 굳이 [직접] DTO로 받아가면서까지 컬렉션을 최적화시키지 X.
 *  *  Default batch fetch size 설정으로 컬렉션 최적화하는 게 더 적절하므로, 여기에서는 구현하지 x.JPA 활용 2의 P30참조)
 * 컬렉션 최적화는 [1:다] 관계에서 1에 해당하는 엔티티를 조회하는 Repository가 있고, 거기서 [다]에 해당하는 컬렉션을 조회할 필요가 있을 때 하는 최적화이다.
 * 고로, [다]에 해당하는 Register를 조회하는 Register(Query)Repository에서는 컬렉션 최적화라는 것이 성립이 안 된다.
 * (root 쿼리 : 예를 들어,MemberRepository이면, Member를 조회하는 쿼리(ex.  SELECT m FROM Member m ) )
 */

@Repository
@RequiredArgsConstructor
public class MissingAddressQueryRepository {

    private final EntityManager em;

    private final MissingAddressRepositorySDJ missingAddressRepositorySDJ; // Spring Data JPA Repository

    public List<MissingAddress> findAllMissingAddress(){ // [페이징] 불가능!

        return  em.createQuery("SELECT mr FROM MissingAddress mr" +

                                " JOIN FETCH mr.registers r"// 컬렉션을 페치 조인하게 되면, row수가 MissingAddress가 아닌, Register(컬렉션)을 기준으로 늘어 나므로, [페이징]도 Register을 기준으로 연산된다.
                                                            // DB는 모든 컬렉션을 메모리로 들고와서 페이징을 실행을 하므로, 최악의 경우 장애로 연결이 된다.


                        ,MissingAddress.class)
                .getResultList();

    }

    public MissingAddress findByOneId(Long id) throws NonUniqueResultException, NoResultException { // [페이징] 불가능

        return em.createQuery("SELECT mr FROM MissingAddress mr" +

                        " JOIN FETCH mr.registers r"+ // 컬렉션을 fetch join하게 되면 페이징 불가능

                        " WHERE mr.id=:id",MissingAddress.class)
                .setParameter("id",id)
                .getSingleResult();
    }

    public MissingAddressDto findByOneId2(Long id) throws NonUniqueResultException, NoResultException{

        MissingAddressDto missingAddressDto = em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                                "MissingAddressDto(m.id,m.zipcode,m.prefecture,m.cityName,m.gu,m.Dong,m.streetName,m.streetNumber)" +

                                " FROM MissingAddress m" + //MissingAddress에 대해서 @xToOne 관계인 엔티티는 없다.
                                " WHERE m.id=:id"          //만약에 있다면, 여기서 fetch join을 사용하여서 조회를 하면 된다.

                        , MissingAddressDto.class)
                .setParameter("id",id)
                .getSingleResult();


        Long missingAddressId = missingAddressDto.getMissingAddressId();
        List<Long> missingAddressIds = new ArrayList<>();
        missingAddressIds.add(missingAddressId);

        Map<Long, List<RegisterDto>> registerMap = findRegisterMap(missingAddressIds);
        List<RegisterDto> registerDtos = registerMap.get(missingAddressId);

        missingAddressDto.setRegisters(registerDtos);

        return missingAddressDto;
    }

    public List<MissingAddress> findAllMissingAddressWithPaging(int offset,int limit){ // [페이징 가능]

        return  em.createQuery("SELECT mr FROM MissingAddress mr"

                               // " JOIN FETCH mr.registers r" // 만약 @xToOne 관계인 엔티티가 있다면, 그건 fetch join을 해야 한다.(여기서는 없을 뿐)

                        ,MissingAddress.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    public List<MissingAddressDto> findAllMissingAddress2(){

        // root 조회(toOne 관계를 1번에 모두 조회)
        List<MissingAddressDto> missingAddress = findMissingAddress(); // toOne에 대한 조회

        List<Long> missingAddressIds = toMissingAddressDtoIds(missingAddress);

        Map<Long,List<RegisterDto>> registerMap = findRegisterMap(missingAddressIds); // toMany(컬렉션)에 대한 조회

        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        missingAddress.forEach(missingAddressdto  -> {

            Long id = missingAddressdto .getMissingAddressId();

            List<RegisterDto> registerDto = registerMap.get(id);

            missingAddressdto.setRegisters(registerDto);

        });

        return missingAddress;

    }

    public MissingAddressDtoWithPagination findAllMissingAddress2WithPaging(int pageNumber, int size){

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<MissingAddress> page = missingAddressRepositorySDJ.findAll(pageRequest); // 이 시점에서 이미 toOne에 대한 조회가 left fetch join에 의해 됨.

        Page<MissingAddressDto> pageDto = page.map(missingAddress -> new MissingAddressDto(missingAddress.getId(),missingAddress.getZipcode(),
                missingAddress.getPrefecture(), missingAddress.getCityName(), missingAddress.getGu(), missingAddress.getDong(), missingAddress.getStreetName(), missingAddress.getStreetNumber()));

        List<MissingAddressDto> content = pageDto.getContent(); // toOne : Member, MissingAddress에 대해 left fetch join 일어남!

        List<Long> missingAddressDtoIds = toMissingAddressDtoIds(content);

        Map<Long, List<RegisterDto>> registerMap = findRegisterMap(missingAddressDtoIds); // toMany에 대한 조회


        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        content.forEach(missingAddressDto -> {

            Long id = missingAddressDto.getMissingAddressId();

            List<RegisterDto> registerDtos = registerMap.get(id);

            missingAddressDto.setRegisters(registerDtos);

        });

        // pagination 정보!



        int countCurrent = content.size();

        int pageNumberCurrent = page.getNumber() + 1; // JPA의 PAGE 번호는 0부터 시작!

        long countTotal = page.getTotalElements();

        int pageTotal = page.getTotalPages();

        int itemsCountPerPage = size;

        Pagination pagination = new Pagination(countCurrent,pageNumberCurrent,countTotal,pageTotal,itemsCountPerPage);

        MissingAddressDtoWithPagination missingAddressDtoWithPagination = new MissingAddressDtoWithPagination(pagination,content);

        return missingAddressDtoWithPagination;

    }

    public List<MissingAddressDto> findMissingAddress(){

        return em.createQuery("SELECT new Portfolio.Missing_Animal.dto." +

                                "MissingAddressDto(m.id,m.zipcode,m.prefecture,m.cityName,m.gu,m.Dong,m.streetName,m.streetNumber)" +

                                " FROM MissingAddress m" // Member에 대해서 @xToOne 관계인 엔티티는 없다.

                        ,MissingAddressDto.class)
                .getResultList();

    }

    public List<Long> toMissingAddressDtoIds(List<MissingAddressDto> missingAddressDtos){

        return missingAddressDtos.stream()

                .map(missingAddressDto -> missingAddressDto.getMissingAddressId())

                .collect(Collectors.toList());


    }


    public  Map<Long,List<RegisterDto>> findRegisterMap(List<Long> missingAddressIds){

        List<RegisterDto> registerDtos = em.createQuery(

                        "SELECT new Portfolio.Missing_Animal.dto."+

                                "RegisterDto(r.id,r.member.id,r.missingAddress.id,r.animalName,r.animalSex,r.animalAge,r.registerDate,r.registerStatus,r.reportedStatus)"+

                                " FROM Register r" +

                                " WHERE r.missingAddress.id IN :missingAddressIds", RegisterDto.class)

                .setParameter("missingAddressIds", missingAddressIds)

                .getResultList();

        // RegisterDto의 id값을 기준으로, RigserDto를 Grouping~~!!!
        return registerDtos.stream()

                .collect(Collectors.groupingBy(RegisterDto::getMissingAddressId));

    }





    //여기서부터 아래는 Querydsl로 구현을 할 것이다.

    /**
     * 2. api/missingaddress/{prefecture }
     * 3. api/missingaddress/{prefecture }/{cityName}
     * 4. api/missingaddress{prefecture }/{cityName}/{gu}
     * 5. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}
     * 6. api/missingaddress{prefecture }/{cityName}/{gu}/{streetName}/{streetNumber}
     * @param
     * @return
     */


    //Querydsl 이용!
    public List<MissingAddressDto> findRegisterByMissingAddress(MissingAddressSearchCond missingAddressSearchCond){


        List<MissingAddressDto> missingAddressDtos = findMissingAddressWithQuerydsl(missingAddressSearchCond);

        List<Long> ids = toMissingAddressDtoIds(missingAddressDtos); // new MissingDto의 id값들!

        Map<Long, List<RegisterDto>> registerMap = findRegisterMap(ids);

        missingAddressDtos.forEach(missingAddressDto -> {

            Long id = missingAddressDto.getMissingAddressId();

            List<RegisterDto> registerdtos = registerMap.get(id);


            missingAddressDto.setRegisters(registerdtos);

        });

        return missingAddressDtos;
    }

   public List<MissingAddressDto> findMissingAddressWithQuerydsl(MissingAddressSearchCond missingAddressSearchCond){

       JPAQueryFactory query = new JPAQueryFactory(em); // Querydsl 사용!

       List<MissingAddressDto> missingAddressDtos  // toOne에 대한 조회!!!
               = query

               .select(
                       Projections.fields(MissingAddressDto.class,

                               missingAddress.id.as("missingAddressId"),
                               missingAddress.zipcode,
                               missingAddress.prefecture,
                               missingAddress.cityName,
                               missingAddress.gu,
                               missingAddress.Dong,
                               missingAddress.streetName,
                               missingAddress.streetNumber
                       ))

               .from(missingAddress) // MissingAddress는 toOne 관계가 없다.

               .where(
                       prefectureEq(missingAddressSearchCond.getPrefecture()),
                       cityNameEq(missingAddressSearchCond.getCityName()),
                       guEq(missingAddressSearchCond.getGu()),
                       DongEq(missingAddressSearchCond.getDong()),
                       streetNameEq(missingAddressSearchCond.getStreetName()),
                       streetNumberEq(missingAddressSearchCond.getStreetNumber()),
                       zipcodeEq(missingAddressSearchCond.getZipcode()))

               .fetch();

       return missingAddressDtos;

   }

   public Map<Long, List<RegisterDto>> findRegistersWithQuerydsl(List<Long> missindAdressDtoIds){

       JPAQueryFactory query = new JPAQueryFactory(em); // Querydsl 사용!

       List<RegisterDto> registerDtos = query // toMany에 대한 조회

               .select(Projections.fields(RegisterDto.class,

                       register.id.as("registerId"),
                       register.member.id.as("memberId"),
                       register.missingAddress.id.as("missingAddressId"),
                       register.animalName,
                       register.animalSex,
                       register.animalAge,
                       register.registerDate,
                       register.registerStatus,
                       register.reportedStatus

               ))

               .from(register)

               .where(register.missingAddress.id.in(missindAdressDtoIds))

               .fetch();

                return  registerDtos.stream()

               .collect(Collectors.groupingBy(RegisterDto::getMissingAddressId));

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
