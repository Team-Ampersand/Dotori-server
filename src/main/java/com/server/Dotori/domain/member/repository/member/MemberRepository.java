package com.server.Dotori.domain.member.repository.member;

import com.server.Dotori.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {


    Optional<Member> findByEmail(String email);
    Optional<Member> findByStuNum(String stuNum);
    boolean existsByStuNum(String stuNum);
    boolean existsByEmail(String email);
    boolean existsByEmailAndStuNum(String email, String stuNum);
}
