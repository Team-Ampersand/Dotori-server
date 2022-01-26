package com.server.Dotori.model.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.Dotori.model.BaseTimeEntity;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name="Member")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_stuNum", nullable = false, unique = true)
    private String stuNum;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_point", columnDefinition = "Long default 0")
    private Long point;

    @Column(name = "member_refreshToken")
    private String refreshToken;

    @Column(name = "self_study_expired_date")
    private LocalDateTime selfStudyExpiredDate;

    @Column(name = "massage_expired_date")
    private LocalDateTime massageExpiredDate;

    @Enumerated(STRING) @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = @JoinColumn(name = "member_id"))
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // List<Role> 형태를 Stream을 사용하여 roles 원소의 값을 String으로 바꿔주는 Enum.name()을 이용하여 List<String>형태로 변환(GrantedAuthority의 생성자는 String 타입을 받기 때문)
        List<String> rolesConvertString = this.roles.stream().map(Enum::name).collect(Collectors.toList());
        return rolesConvertString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "member_selfstudy", nullable = false)
    private SelfStudy selfStudy;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_music", nullable = false)
    private Music music;

    @Enumerated(STRING)
    @Column(name = "member_massage")
    private Massage massage;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateMusic(Music music) {
        this.music = music != null ? music : this.music;
    }

    public void updatePassword(String password) {
        this.password = password != null ? password : this.password;
    }

    public void updatePoint(Long point) {
        this.point = point != null ? point : this.point;
    }
    public void updateSelfStudy(SelfStudy selfStudy) {
        this.selfStudy = selfStudy != null ? selfStudy : this.selfStudy;
    }
    public void updateMassage(Massage massage) {
        this.massage = massage != null ? massage : this.massage;
    }
    public void updateMassageExpiredDate(LocalDateTime massageExpiredDate) {
        this.massageExpiredDate = massageExpiredDate;
    }
    public void updateRole(List<Role> roles) {
        this.roles = roles != null ? roles : this.roles;
    }

    public void updateStuNum(String stuNum) {
        this.stuNum = stuNum != null ? stuNum : this.stuNum;
    }

    public void updateMemberName(String memberName) {
        this.memberName = memberName != null ? memberName : this.memberName;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateSelfStudyExpiredDate(LocalDateTime selfStudyExpiredDate) {
        this.selfStudyExpiredDate = selfStudyExpiredDate;
    }
}
