package com.server.Dotori.exception.user;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class UserExceptionController {

    @GetMapping("/user-not-found")
    public CommonResult userNotFoundException(){
        throw new UserNotFoundException();
    }
}
