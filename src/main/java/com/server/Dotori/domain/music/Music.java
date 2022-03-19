package com.server.Dotori.domain.music;

import com.server.Dotori.global.entity.BaseTimeEntity;
import com.server.Dotori.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Music")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Music extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @Column(name = "music_url", nullable = false)
    private String url;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
