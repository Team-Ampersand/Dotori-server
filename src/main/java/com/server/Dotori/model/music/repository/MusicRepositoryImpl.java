package com.server.Dotori.model.music.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.QMember;
import com.server.Dotori.model.member.enumType.Music;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.server.Dotori.model.member.QMember.member;

@RequiredArgsConstructor
public class MusicRepositoryImpl implements MusicRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 음악 상태가 음악 "신청됨" 상태인 회원을 음악 "신청가능" 으로 update 하는 query
     */
    @Override
    public void updateMusicStatusMemberByMember() {
        queryFactory
                .update(member)
                .where(
                        member.music.eq(Music.APPLIED)
                )
                .set(member.music, Music.CAN)
                .execute();
    }
}
