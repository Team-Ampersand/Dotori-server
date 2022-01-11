package com.server.Dotori.model.member.repository.member;

import com.server.Dotori.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {
    Member findByUsername(String username);
    Optional<Member> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByStdNum(String stuNum);
    boolean existsByEmail(String email);
    boolean existsByEmailAndStdNum(String email, String stuNum);
}
