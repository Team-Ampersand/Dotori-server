package com.server.Dotori.model.member;

import com.server.Dotori.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity @Table(name="SelfStudy")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelfStudy extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "selfStudy_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
