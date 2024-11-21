package com.ssafy.miro.email.application;

import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.email.exception.EmailTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.ssafy.miro.common.code.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisTokenService redisTokenService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;
    private String emailPrefix = "emailValidation: ";

    @Async
    public void sendVerificationEmail(String email) {
        String token=generateVerificationCode();
        redisTokenService.saveToken(emailPrefix+token,email,authCodeExpirationMillis);
        String verificationLink="http://localhost:8080/api/v1/user/verify-email?token="+token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("MiRo 인증 확인 메일입니다.");
        message.setText("MiRo 인증을 하려면 다음 링크를 클릭하세요: " + verificationLink);

        mailSender.send(message);
    }

    public String generateVerificationCode() {
        return UUID.randomUUID().toString();
    }

    public void verifyEmail(String verificationCode) {
        String emailByToken = redisTokenService.getToken(emailPrefix+verificationCode);
        if(emailByToken==null) {
            throw new EmailTokenNotFoundException(NOT_FOUND_EMAIL_TOKEN_ID);
        }
        redisTokenService.deleteToken(emailPrefix+verificationCode);

    }

}
