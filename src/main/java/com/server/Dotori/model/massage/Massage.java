package com.server.Dotori.model.massage;

import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.member.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED) @AllArgsConstructor
@Getter @Builder
@Entity @Table(name = "Massage")
public class Massage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "massage_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
