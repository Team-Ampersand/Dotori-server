package com.server.Dotori.exception.music.controller;

import com.server.Dotori.exception.music.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class MusicExceptionController {

    @GetMapping("/music-already")
    public CommonResult musicAlreadyException() {
        throw new MusicAlreadyException();
    }

    @GetMapping("/music-not-applied")
    public CommonResult musicNotAppliedException() {
        throw new MusicNotAppliedException();
    }

    @GetMapping("/music-not-found")
    public CommonResult musicNotFoundException() {
        throw new MusicNotFoundException();
    }

    @GetMapping("/music-cant-request-date")
    public CommonResult musicCantRequestDate() {
        throw new MusicCantRequestDateException();
    }

    @GetMapping("/music-not-request-on-that-date")
    public CommonResult musicNotRequestOnThatDate() {
        throw new MusicNotRequestOnThatDateException();
    }

}
