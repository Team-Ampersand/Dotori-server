package com.server.Dotori.model.music;

import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "Music")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Music {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @Column(name = "music_url", nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "music_time", nullable = false)
    private String time;
}
