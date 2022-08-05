package com.server.Dotori.domain.board;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name = "BoardImage")
@Getter @Builder @Setter
@AllArgsConstructor @NoArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id",nullable = false)
    private Board board;

    @Column(name = "board_image_url")
    private String url;
}
