package com.server.Dotori.model.health;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HealthCheckController {

    @GetMapping("/health-check")
    @ResponseStatus( HttpStatus.OK )
    public String healthCheck() {
        return "Health OK";
    }
}
