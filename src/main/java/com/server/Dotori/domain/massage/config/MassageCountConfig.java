package com.server.Dotori.domain.massage.config;

import com.server.Dotori.domain.massage.MassageCount;
import com.server.Dotori.domain.massage.repository.MassageCountRepository;
import com.server.Dotori.domain.massage.repository.MassageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MassageCountConfig {
    private final MassageCountRepository massageCountRepository;
    private final MassageRepository massageRepository;

    @PostConstruct
    private void generateMassageCount() {
        massageCountRepository.deleteAll();
        massageCountRepository.save(
                MassageCount.builder()
                        .id(1L)
                        .count(massageRepository.count())
                        .build()
        );
    }
}
