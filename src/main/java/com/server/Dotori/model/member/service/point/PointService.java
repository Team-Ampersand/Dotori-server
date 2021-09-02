package com.server.Dotori.model.member.service.point;

import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.dto.PointDto;

import java.util.List;

public interface PointService {

    void point(PointDto pointDto);

    List<GetAboutPointDto> getAllStudentPoint(Long id);
}
