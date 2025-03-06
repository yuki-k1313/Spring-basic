package com.korit.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.basic.entity.UserEntity;

import jakarta.transaction.Transactional;

// User 테이블에 접근할 리포지토리
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // SELECT * FROM user WHERE user_id = ?;
    UserEntity findByUserId(String userId);

    // SELECT * FROM user WHERE user_tel_number = ?;
    UserEntity findByUserTelNumber(String userTelNumber);

    List<UserEntity> findByOrderByUserIdAsc();

    boolean existsByUserId(String userId);
    boolean existsByUserTelNumber(String userTelNumber);

    // Query Method: 
    // - JpaRepository의 메서드 선언시에 지정된 규칙에 따라 메서드명을 작성하면 JPA가 SQL을 만들어주는 방법

    // - findBy [SELECT * WHERE]: 필드명을 기준으로 모든 컬럼을 조회할 때 사용함, findBy 바로 뒤에 필드명을 붙여서 작성, 필드명은 반드시 첫글자가 대문자로 작성되어야함

    // SELECT * FROM user WHERE user_name = ?;
    // List 타입으로 반환받는 경우 결과가 없으면 길이가 0인 리스트를 반환
    List<UserEntity> findByUserName(String userName);

    // existsBy: 조건에 해당하는 레코드가 존재하는지 여부 확인 시 사용
    // boolean 형태로 반환
    // EXISTS (SELECT * FROM user WHERE user_name = ?);
    boolean existsByUserName(String userName);

    // countBy: 조건에 해당하는 레코드의 개수 확인 시 사용
    // int 형태로 반환
    // SELECT count(*) FROM user WHERE user_name = ?;
    int countByUserName(String userName);

    // And / Or: and 연산 혹은 or 연산에 사용됨, 필드와 필드 사이 작성
    // And Or 연산우선순위 주의
    // SELECT * FROM user WHERE user_name = ? AND user_address = ?;
    List<UserEntity> findByUserNameAndUserAddress(String userName, String userAddress);

    // Like, NotLike, Containing, StartingWith, EndingWith:
    // Like 연산에 사용, 필드 뒤에 붙여서 사용
    // SELECT * FROM user WHERE user_address LIKE '%?%';
    List<UserEntity> findByUserAddressLike(String userAddress);
    // SELECT * FROM user WHERE user_address LIKE '?%';
    List<UserEntity> findByUserAddressStartingWith(String userAddress);

    // OrderBy: 정렬을 사용할 필드를 지정해서 Asc, Desc 시에 사용
    // SELECT * FROM user ORDER BY user_name DESC;
    List<UserEntity> findByOrderByUserNameDesc();

    // deleteBy: 조건에 따른 레코드를 삭제할 때 사용
    // @Transactinal을 적용해야 사용 가능
    @Transactional
    void deleteByUserName(String userName);

    // @Query:
    // - 쿼리 메서드 방식의 한계를 극복하기 위해 사용하는 방법
    // - 복잡한 쿼리(서브쿼리, 조인, 그룹화)를 직접 작성하는 방법

    // JPQL (Java Persistance Query Language)
    // - 표준 SQL과 매우 흡사하지만 Entity 및 Entity의 필드로 쿼리를 작성
    @Query(value="SELECT u FROM user u WHERE u.userName = ?1 AND u.userTelNumber = ?2")
    List<UserEntity> getUserList(String userName, String userTelNumber);

    @Query(value="SELECT u FROM user u WHERE u.userName = :name AND u.userTelNumber = :telNumber")
    List<UserEntity> getUserList2(
        @Param("name") String userName, 
        @Param("telNumber") String userTelNumber
    );

    // Native SQL:
    // - 현재 RDBMS의 SQL 문법을 그대로 사용하는 방법
    // - @Query의 nativeQuery 속성을 반드시 true로 지정
    @Query(
    value=
        "SELECT * " + 
        "FROM user " + 
        "WHERE user_name = :userName " + 
        "AND user_tel_number = :userTelNumber ", 
    nativeQuery=true)
    List<UserEntity> getUserList3(
        @Param("userName") String userName, 
        @Param("userTelNumber") String userTelNumber
    );

}