package com.server.Dotori.domain.massage.repository;

import com.server.Dotori.domain.massage.dto.MassageStudentsDto;

import java.util.List;

public interface MassageRepositoryCustom {

    List<MassageStudentsDto> findByMassageList();
}
