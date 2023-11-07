package flab.project.service;

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

    public void sendVerificationCode(String email) {
        String verificationCode = createVerificationCode();
        MimeMessage message = writeEmail(email, emailSubject, setContext(verificationCode));

        emailSender.send(message);
    }

    private String createVerificationCode() {

        String verificationCode = UUID.randomUUID().toString().substring(0, 7);

        return verificationCode;
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

        return message;
    }

    // Todo 테스트를 위해 임시적으로 적용, 추후 삭제 예정
    private String setContext(String verificationCode) {
        Context context = new Context();

        context.setVariable("verificationCode", verificationCode);

        return templateEngine.process("email", context);
    }
}