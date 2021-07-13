package com.server.Dotori.model.selfstudy;

import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "SelfStudy")
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor
public class SelfStudy {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "study_username", nullable = false)
    private String username;
}
