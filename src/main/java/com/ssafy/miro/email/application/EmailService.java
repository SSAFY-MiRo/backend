package com.ssafy.miro.email.application;

import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.email.exception.EmailTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    public void sendVerificationEmail(String email) {
        String token=generateVerificationCode();
        redisTokenService.saveToken(emailPrefix+token,email,authCodeExpirationMillis);
        String verificationLink="http://localhost:8080/verify-email?token="+token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Email");
        message.setText("Your verification code has been sent to " + verificationLink);

        mailSender.send(message);
    }

    public String generateVerificationCode() {
        return UUID.randomUUID().toString();
    }

    public void verifyEmail(String verificationCode) {
        String emailByToken = redisTokenService.getEmailByToken(emailPrefix+verificationCode);
        if(emailByToken==null) {
            throw new EmailTokenNotFoundException(NOT_FOUND_EMAIL_TOKEN_ID);
        }
        redisTokenService.deleteToken(emailPrefix+verificationCode);

    }

}
