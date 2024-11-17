package com.drn.projectmanagementsystem_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        String subject = "You're Invited to Join the Project Team";
        String text = String.format(
                "<p>Hello,</p>" +
                        "<p>You have been invited to join a project team. Please click the link below to join:</p>" +
                        "<p><a href='%s'>Join the Project Team</a></p>" +
                        "<p>If you did not expect this email, please ignore it.</p>",
                link
        );

        try {
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setTo(userEmail);
            javaMailSender.send(mimeMessage);
        } catch (MailSendException ex) {
            throw new MessagingException("Failed to send email to " + userEmail + ". Please check the email address and try again.", ex);
        }
    }

}
