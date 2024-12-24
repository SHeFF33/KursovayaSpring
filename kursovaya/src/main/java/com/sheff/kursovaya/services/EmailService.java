package com.sheff.kursovaya.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    public void sendActivationEmail(String recipientEmail, String subject, String activationLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(
                    "<p>Здравствуйте!</p>" +
                            "<p>Для активации вашего аккаунта, перейдите по <a href='" + activationLink + "'>этой ссылке</a>.</p>" +
                            "<p>С уважением, магазин продаж б/у смартфонов Mobile Shop!.</p>", true
            );
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Ошибка отправки email", e);
        }
    }
}
