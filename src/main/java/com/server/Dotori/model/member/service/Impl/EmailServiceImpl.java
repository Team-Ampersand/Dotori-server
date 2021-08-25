package com.server.Dotori.model.member.service.Impl;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.dto.MemberEmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.EmailSandService;
import com.server.Dotori.model.member.service.EmailService;
import com.server.Dotori.util.KeyUtil;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final KeyUtil keyUtil;
    private final EmailSandServiceImpl emailSandServiceService;
    private final RedisUtil redisUtil;

    @Override
    public String auth(MemberEmailDto memberEmailDto) {
        String key = keyUtil.keyIssuance();
        redisUtil.setDataExpire(key,memberEmailDto.getUserEmail(), 3 * 60 * 1000L);
        emailSandServiceService.sandEmail(memberEmailDto.getUserEmail(),key);
        return "인증 키 : " + key;
    }

    @Override
    public String authCheck(MemberEmailKeyDto memberEmailKeyDto) {
        if(redisUtil.getData(memberEmailKeyDto.getKey()) != null){
            return redisUtil.getData(memberEmailKeyDto.getKey());
        }else{
            throw new UserNotFoundException();
        }
    }
}
