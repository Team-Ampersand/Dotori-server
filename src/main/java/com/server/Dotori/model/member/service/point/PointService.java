package com.server.Dotori.model.member.service.point;

import com.server.Dotori.model.member.dto.point.GetPointDto;
import com.server.Dotori.model.member.dto.point.PointDto;

import java.util.List;

public interface PointService {

    void point(PointDto pointDto);

    List<GetPointDto> getAllStudentPoint(Long id);
}
