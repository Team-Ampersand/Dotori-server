package com.server.Dotori.domain.self_study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="SelfStudyCount")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelfStudyCount {

    @Id
    @Column(name = "selfStudyCount_id")
    private Long id;

    @Column(name = "selfStudyCount_count")
    private Long count;

    public void addCount() {
        this.count++;
    }

    public void removeCount() {
        this.count--;
    }

    public void updateCount(Long count) {
        this.count = count != null ? count : this.count;
    }
}
