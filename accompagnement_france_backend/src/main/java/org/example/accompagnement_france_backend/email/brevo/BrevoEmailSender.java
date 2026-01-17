package org.example.accompagnement_france_backend.email.brevo;

import brevoApi.TransactionalEmailsApi;
import lombok.extern.slf4j.Slf4j;
import org.example.accompagnement_france_backend.email.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import brevo.*;

import brevo.auth.*;
import brevoModel.*;
import brevoApi.AccountApi;

import java.io.File;
import java.util.*;

@Slf4j
@Service
public class BrevoEmailSender implements EmailSender {
    private final TransactionalEmailsApi emailApi;

    private final String senderEmail;
    private final String senderName;

    public BrevoEmailSender(
            @Value("${brevo.sender.name}") String senderName,
            @Value("${brevo.sender.email}") String senderEmail) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.emailApi = new TransactionalEmailsApi();
    }

    @Override
    public void send(String to, String subject, String htmlContent) {
        SendSmtpEmail email = new SendSmtpEmail()
                .sender(new SendSmtpEmailSender().name(senderName).email(senderEmail))
                .to(List.of(new SendSmtpEmailTo().email(to)))
                .subject(subject)
                .htmlContent(htmlContent);

        try {
            emailApi.sendTransacEmail(email);
            log.info("Sending email to {}", to);
        } catch (ApiException e) {
            throw new RuntimeException("Failed to send email via Brevo: " + e.getResponseBody(), e);
        }
    }

}
