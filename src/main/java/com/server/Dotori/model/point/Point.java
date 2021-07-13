package com.server.Dotori.model.point;

import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.point.enumType.Kind;
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
public class Point extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
    private Member giverId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
    private Member receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_kind", nullable = false)
    private Kind kind;

    @Column(name = "point_amount", nullable = false)
    private Long amount;

}
