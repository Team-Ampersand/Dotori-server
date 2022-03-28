package com.server.Dotori.domain.point.service;

import com.server.Dotori.domain.point.dto.GetAboutPointDto;
import com.server.Dotori.domain.point.dto.PointDto;

import java.util.List;

public interface PointService {

    void point(PointDto pointDto);

    List<GetAboutPointDto> getAllStudentPoint(Long id);
}
