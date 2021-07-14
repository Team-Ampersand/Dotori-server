package com.server.Dotori.model.board;

import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Entity @Table(name = "Board")
@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_content", length = 500, nullable = false)
    private String content;

}
