package com.server.Dotori.domain.board;

import com.server.Dotori.global.entity.BaseTimeEntity;
import com.server.Dotori.domain.member.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name = "Board")
@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_content", length = 800, nullable = false)
    private String content;

    @Column(name = "board_image_url")
    private String url;

    public void updateBoard(String title, String content) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
    }
}