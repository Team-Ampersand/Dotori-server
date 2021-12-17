package com.server.Dotori.model.member;

import com.server.Dotori.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "Email_Certificate")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCertificate extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "certificate_email", nullable = false)
    private String email;

    @Column(name = "certificate_key", nullable = false)
    private String key;

    @Column(name = "certificate_expiredTime", nullable = false)
    private LocalDateTime expiredTime;
}
