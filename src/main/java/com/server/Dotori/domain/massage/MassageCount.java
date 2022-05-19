package com.server.Dotori.domain.massage;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "massage_count")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MassageCount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "massage_count")
    private Long count;

    public void addCount() {
        this.count++;
    }

    public void deductionCount() {
        this.count--;
    }

    public void clearCount() {
        this.count = 0L;
    }
}
