package org.example.accompagnement_france_backend.email;

public interface EmailSender {
    void send(
            String to,
            String subject,
            String content
    );
}
