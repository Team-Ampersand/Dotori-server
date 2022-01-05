package com.server.Dotori.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity @Table(name = "Image")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "image_url", nullable = false)
    private String url;
}
