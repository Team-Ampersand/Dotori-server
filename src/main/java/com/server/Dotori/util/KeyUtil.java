package com.server.Dotori.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KeyUtil {
    public String keyIssuance(){
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }
}
