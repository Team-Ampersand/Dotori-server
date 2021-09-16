package com.server.Dotori.exception.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.SelfStudyAlready;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantChange;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyNotFound;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyOverPersonal;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class SelfStudyExceptionController {

    @GetMapping("/SelfStudy-Already")
    public CommonResult selfStudyAlready() {
        throw new SelfStudyAlready();
    }

    @GetMapping("/SelfStudy-Cant-Change")
    public CommonResult selfStudyCantChange() {
        throw new SelfStudyCantChange();
    }

    @GetMapping("/SelfStudy-Not-Found")
    public CommonResult selfStudyNotFound() {
        throw new SelfStudyNotFound();
    }

    @GetMapping("/SelfStudy-Over-Personal")
    public CommonResult selfStudyOverPersonal() {
        throw new SelfStudyOverPersonal();
    }
}
