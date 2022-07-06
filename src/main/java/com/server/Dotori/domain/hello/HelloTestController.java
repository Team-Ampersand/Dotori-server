package com.server.Dotori.domain.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {

    @GetMapping("/v1/hello")
    public String hello() {
        return "hello";
    }
}
