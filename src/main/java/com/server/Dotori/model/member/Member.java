package com.server.Dotori.model.member;

import com.server.Dotori.model.member.enumType.Auth;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name="Member")
@Builder @Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_username", nullable = false)
    private String userName;

    @Column(name = "member_stdnum", nullable = false, unique = true)
    private String stdNum;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_auth", nullable = false)
    @Enumerated(value = STRING)
    private Auth auth;

    @Column(name = "member_selfstudy", nullable = false)
    @Enumerated(value = STRING)
    private SelfStudy selfStudy;

    @Column(name = "member_music", nullable = false)
    @Enumerated(value = STRING)
    private Music music;


}
