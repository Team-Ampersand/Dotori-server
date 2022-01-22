package com.server.Dotori.model.massage.controller;

import com.server.Dotori.model.massage.service.MassageService;
import com.server.Dotori.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberMassageController {

    private final MassageService massageService;
    private final ResponseService responseService;


}
