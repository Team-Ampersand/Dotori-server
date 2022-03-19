package com.server.Dotori.domain.music.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.member.enumType.Music;
import com.server.Dotori.domain.music.dto.MusicResDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.server.Dotori.domain.member.QMember.member;
import static com.server.Dotori.domain.music.QMusic.music;


/**
 * @since 1.0.0
 * @author 배태현
 */
@RequiredArgsConstructor
public class MusicRepositoryImpl implements MusicRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 음악 상태가 음악 "신청됨" 상태인 회원을 음악 "신청가능" 으로 update 하는 query
     * @author 배태현
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

    /**
     * 신청된 음악을 조회하는 query
     * @return List-MusicResDto (id, musicUrl, member.username, member.email)
     * @author 배태현
     */
    @Override
    public List<MusicResDto> findAllMusic() {
        return queryFactory
                .select(Projections.fields(MusicResDto.class,
                        music.id,
                        music.url,
                        music.member.memberName,
                        music.member.email,
                        music.createdDate
                        ))
                .from(music)
                .innerJoin(music.member, member)
                .orderBy(music.createdDate.asc())
                .fetch();
    }

    /**
     * 오늘 신청된 음악을 조회하는 query
     * @param localDate localDate
     * @return List-MusicResDto (id, musicUrl, member.username, member.email)
     * @author 배태현
     */
    @Override
    public List<MusicResDto> findCurrentDateMusic(LocalDate localDate) {
        return queryFactory
                .select(Projections.fields(MusicResDto.class,
                        music.id,
                        music.url,
                        music.member.memberName,
                        music.member.email,
                        music.createdDate
                        ))
                .from(music)
                .where(music.createdDate.between(
                        localDate.atStartOfDay(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX).withNano(0)
                ))
                .orderBy(music.createdDate.asc())
                .fetch();
    }

    /**
     * 해당하는 날짜에 신청된 음악을 조회하는 query
     * @param date date
     * @return List-MusicResDto (id, musicUrl, member.username, member.email)
     * @author 배태현
     */
    @Override
    public List<MusicResDto> findDateMusic(LocalDate date) {
        return queryFactory
                .select(Projections.fields(MusicResDto.class,
                        music.id,
                        music.url,
                        music.member.memberName,
                        music.member.email,
                        music.createdDate
                ))
                .from(music)
                .innerJoin(music.member, member)
                .where(music.createdDate.stringValue().like(date+"%"))
                .orderBy(music.createdDate.asc())
                .fetch();
    }
}
