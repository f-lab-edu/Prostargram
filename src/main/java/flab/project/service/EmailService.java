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

import static jakarta.mail.internet.MimeMessage.RecipientType.TO;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final String emailSubject = "Prostargram";
    private static final String emailContentCharSet = "utf-8";
    private static final String emailContentSubType = "html";
    private static final String contextName = "verificationCode";
    private static final String templateName = "email";

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
            message.setRecipient(TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(content, emailContentCharSet, emailContentSubType);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    private String setContext(String verificationCode) {
        Context context = new Context();

        context.setVariable(contextName, verificationCode);

        return templateEngine.process(templateName, context);
    }
}