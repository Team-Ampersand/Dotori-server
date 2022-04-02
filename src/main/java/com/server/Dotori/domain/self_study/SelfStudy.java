package com.server.Dotori.domain.self_study;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name="SelfStudy")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelfStudy extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "selfStudy_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

}