package com.server.Dotori.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity @Table(name="SelfStudy")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelfStudy {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "selfStudy_id")
    private Long id;

    @Column(name = "selfStudy_count")
    private int count;

    public void updateCount(int count) {
        this.count = count;
    }
}
