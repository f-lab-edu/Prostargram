package flab.project.service;

import flab.project.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final String emailSubject = "Prostargram";

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;

    public void sendVerificationCode(String email) {
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }

        String verificationCode = createVerificationCode();
        MimeMessage message = writeEmail(email, emailSubject, setContext(verificationCode));

        emailSender.send(message);
    }

    public Boolean checkVerificationCode(String email, String verificationCode) {
        String validCode = redisUtil.getData(email);

        return validCode.equals(verificationCode);
    }

    private String createVerificationCode() {
        String verificationCode = UUID.randomUUID().toString().substring(0, 7);

        return verificationCode.toString();
    }

    private MimeMessage writeEmail(String email, String subject, String content) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(content, "utf-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        redisUtil.setDataExpire(email, content, 60 * 5L);

        return message;
    }

    private String setContext(String verificationCode) {
        Context context = new Context();

        context.setVariable("verificationCode", verificationCode);

        return templateEngine.process("email", context);
    }
}