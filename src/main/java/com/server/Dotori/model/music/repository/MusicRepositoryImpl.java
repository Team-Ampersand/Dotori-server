package com.server.Dotori.model.music.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.QMember;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.music.QMusic;
import com.server.Dotori.model.music.dto.MusicResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.server.Dotori.model.member.QMember.member;
import static com.server.Dotori.model.music.QMusic.*;

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
     * @return List-MusicResDto (id, musicUrl, member.username)
     * @author 배태현
     */
    @Override
    public List<MusicResDto> findAllMusic() {
        return queryFactory
                .select(Projections.fields(MusicResDto.class,
                        music.id,
                        music.url,
                        music.member.username,
                        music.createdDate
                        ))
                .from(music)
                .orderBy(music.createdDate.asc())
                .fetch();
    }

    @Override
    public List<MusicResDto> findCurrentDateMusic(LocalDate localDate) {
        return queryFactory
                .select(Projections.fields(MusicResDto.class,
                        music.id,
                        music.url,
                        music.member.username,
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
}
