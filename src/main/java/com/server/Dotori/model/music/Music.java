package com.server.Dotori.model.music;

import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

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
