package com.server.Dotori.model.point;

import com.server.Dotori.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Entity @Getter @Builder
@Table(name = "Point")
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "giver_id", nullable = false)
    private Member giverId;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiverId;

    @Column(name = "point_amount", nullable = false)
    private Long amount;

    @Column(name = "point_gived", nullable = false)
    private LocalDateTime gived;

}
