package com.server.Dotori.model.member.repository;

import com.server.Dotori.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {
    Member findByUsername(String username);
    Member findByEmail(String email);
    Member findByStdNum(String stdNum);
    boolean existsByUsername(String username);
    boolean existsByStdNum(String stuNum);
}
