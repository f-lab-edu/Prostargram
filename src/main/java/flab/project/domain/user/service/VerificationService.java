package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.exception.ExistedAccountException;
import flab.project.domain.user.exception.InvalidVerificationCodeException;
import flab.project.domain.user.mapper.UserMapper;
import flab.project.domain.user.model.EmailTokenReturnDto;
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
    private static final long FIVE_MINUTE = 60 * 5L;
    private static final long FIFTEEN_MINUTE = 60 * 15L;

    // ^[A-Za-z0-9._%+-]+ : 사용자 명(ex. pask220), 영문 대/소문자, 숫자, ! 포함 가능
    // @[A-Za-z0-9.-] : @ 뒤의 도메인 이름, 영문 대/소문자, 숫자, 온점(.), 하이픈(-) 포함 가능
    // \\.[A-Za-z]{2,4}$ : 도메인의 마지막 부분, 온점 뒤 2 ~ 4개의 영문 대/소문자 포함 가능
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\" +
            ".[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int VERIFICATION_CODE_LENGTH = 7;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private final UserMapper userMapper;

    // Todo 추후, 이메일 말고 다른 전송 수단도 추가할 예정이라면 writeEmail() 메서드를 클래스로 분리 예정
    public void sendVerificationCode(String email) {
        validateEmail(email);

        if (redisUtil.hasKey(email)) {
            redisUtil.delete(email);
        }

        String verificationCode = createVerificationCode();
        MimeMessage message = composeEmail(email, EMAIL_SUBJECT, setContext(verificationCode));

        redisUtil.setWithDuration(email, verificationCode, FIVE_MINUTE);

        emailSender.send(message);
    }

    public EmailTokenReturnDto checkVerificationCode(String email, String verificationCode) {
        validateVerificationCodeFormat(verificationCode);

        String validCode = redisUtil.get(email);
        if (validCode == null || !validCode.equals(verificationCode)) {
            throw new InvalidVerificationCodeException();
        }

        String emailToken = createVerificationCode();
        redisUtil.setWithDuration(email, emailToken, FIFTEEN_MINUTE);

        return new EmailTokenReturnDto(emailToken);
    }

    private void validateVerificationCodeFormat(String verificationCode) {
        if (verificationCode.length() != VERIFICATION_CODE_LENGTH) {
            throw new InvalidVerificationCodeException();
        }
    }

    private void validateEmail(String email) {
        validateEmailFormat(email);
        validateRegisteredEmail(email);
    }

    private void validateEmailFormat(String email) {
        if (StringUtils.isBlank(email) || !email.matches(EMAIL_PATTERN)) {

            throw new InvalidUserInputException("Invalid Email.");
        }
    }

    private void validateRegisteredEmail(String email) {
        boolean existsEmail = userMapper.existsByEmail(email);

        if (existsEmail) {
            throw new ExistedAccountException();
        }
    }

    private String createVerificationCode() {
        return UUID.randomUUID().toString().substring(0, VERIFICATION_CODE_LENGTH);
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