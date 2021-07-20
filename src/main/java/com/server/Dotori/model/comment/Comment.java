package com.server.Dotori.model.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name = "Comment")
@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "comment_contents", nullable = false)
    private String contents;

    @Column(name = "comment_writer", nullable = false)
    private String writer;


    private void updateContent(String contents) {
        this.contents = contents != null ? contents : this.contents;
    }
}
