package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

import static jakarta.mail.internet.MimeMessage.RecipientType.TO;

@RequiredArgsConstructor
@Service
public class VerificationService {

    private static final String EMAIL_SUBJECT = "Prostargram";
    private static final String EMAIL_CONTENT_CHAR_SET = "utf-8";
    private static final String EMAIL_CONTENT_SUBTYPE = "html";
    private static final String CONTEXT_NAME = "verificationCode";
    private static final String TEMPLATE_NAME = "email";
    private static final long MINUTES_FOR_DURATION = 60 * 5L;

    // ^[A-Za-z0-9._%+-]+ : 사용자 명(ex. pask220), 영문 대/소문자, 숫자, ! 포함 가능
    // @[A-Za-z0-9.-] : @ 뒤의 도메인 이름, 영문 대/소문자, 숫자, 온점(.), 하이픈(-) 포함 가능
    // \\.[A-Za-z]{2,4}$ : 도메인의 마지막 부분, 온점 뒤 2 ~ 4개의 영문 대/소문자 포함 가능
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9.!]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$\n";

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;

    // Todo 추후, 이메일 말고 다른 전송 수단도 추가할 예정이라면 writeEmail() 메서드를 클래스로 분리 예정
    public void sendVerificationCode(String email) {
        validateEmail(email);

        if (redisUtil.hasKey(email)) {
            redisUtil.delete(email);
        }

        String verificationCode = createVerificationCode();
        MimeMessage message = composeEmail(email, EMAIL_SUBJECT, setContext(verificationCode));

        redisUtil.setWithDuration(email, verificationCode, MINUTES_FOR_DURATION);

        emailSender.send(message);
    }

    public Boolean checkVerificationCode(String email, String verificationCode) {
        String validCode = redisUtil.get(email);

        if (validCode == null) {
            return false;
        }

        return validCode.equals(verificationCode);
    }

    private void validateEmail(String email) {
        if (StringUtils.isBlank(email) || !email.matches(EMAIL_PATTERN)) {
            throw new InvalidUserInputException("Invalid Email.");
        }
    }

    private String createVerificationCode() {
        String verificationCode = UUID.randomUUID().toString().substring(0, 7);

        return verificationCode;
    }

    private MimeMessage composeEmail(String email, String subject, String content) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            message.setRecipient(TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(content, EMAIL_CONTENT_CHAR_SET, EMAIL_CONTENT_SUBTYPE);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    private String setContext(String verificationCode) {
        Context context = new Context();

        context.setVariable(CONTEXT_NAME, verificationCode);

        return templateEngine.process(TEMPLATE_NAME, context);
    }
}