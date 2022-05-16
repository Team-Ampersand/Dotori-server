package com.server.Dotori.domain.self_study.dev;

import com.server.Dotori.domain.self_study.SelfStudyCount;
import com.server.Dotori.domain.self_study.repository.SelfStudyCountRepository;
import com.server.Dotori.domain.self_study.repository.SelfStudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class SelfStudyCountConfig {

    private final SelfStudyRepository selfStudyRepository;
    private final SelfStudyCountRepository selfStudyCountRepository;

    @PostConstruct
    private void selfStudyCountEntitySetting() {
        selfStudyCountRepository.deleteAll();
        selfStudyCountRepository.save(
                SelfStudyCount.builder()
                        .id(1L)
                        .count(selfStudyRepository.count())
                        .build()
        );

        log.info("========== SelfStudyCount Setting Success ==========");
    }
}
