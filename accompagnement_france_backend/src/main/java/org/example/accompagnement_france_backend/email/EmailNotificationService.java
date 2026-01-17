package org.example.accompagnement_france_backend.email;

import org.example.accompagnement_france_backend.demand.dto.DemandDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    private final EmailSender emailSender;
    @Value("${brevo.email.admin.email}")
    private String adminEmail;

    public EmailNotificationService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    // Notify admin when a new contact request is submitted
    public void notifyAdminNewDemand(DemandDto demandDto) {
        String subject = "Nouvelle demande de contact reçue";
        String htmlContent = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
        <table role="presentation" style="width: 100%%; border-collapse: collapse;">
            <tr>
                <td style="padding: 40px 0; text-align: center; background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);">
                    <h1 style="margin: 0; color: #ffffff; font-size: 24px; font-weight: 600;">
                        Nouvelle Demande Reçue
                    </h1>
                </td>
            </tr>
            <tr>
                <td style="padding: 0;">
                    <table role="presentation" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                        <tr>
                            <td style="padding: 40px 30px;">
                                <p style="margin: 0 0 30px 0; color: #333333; font-size: 16px; line-height: 1.5;">
                                    Une nouvelle demande de contact a été soumise via le site web.
                                </p>
                                
                                <table role="presentation" style="width: 100%%; border-collapse: collapse;">
                                    <tr>
                                        <td style="padding: 15px; background-color: #f8f9fa; border-left: 4px solid #667eea;">
                                            <table role="presentation" style="width: 100%%; border-collapse: collapse;">
                                                <tr>
                                                    <td style="padding: 8px 0;">
                                                        <strong style="color: #667eea; font-size: 14px;">Prénom :</strong>
                                                        <span style="color: #333333; font-size: 14px; margin-left: 10px;">%s</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="padding: 8px 0;">
                                                        <strong style="color: #667eea; font-size: 14px;">Nom :</strong>
                                                        <span style="color: #333333; font-size: 14px; margin-left: 10px;">%s</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="padding: 8px 0;">
                                                        <strong style="color: #667eea; font-size: 14px;">Service demandé :</strong>
                                                        <span style="color: #333333; font-size: 14px; margin-left: 10px;">%s</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="padding: 8px 0;">
                                                        <strong style="color: #667eea; font-size: 14px;">Email :</strong>
                                                        <a href="mailto:%s" style="color: #667eea; text-decoration: none; margin-left: 10px;">%s</a>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                
                                <div style="margin-top: 30px; padding: 20px; background-color: #f8f9fa; border-radius: 6px;">
                                    <p style="margin: 0 0 10px 0; color: #667eea; font-weight: 600; font-size: 14px;">Message :</p>
                                    <p style="margin: 0; color: #555555; font-size: 14px; line-height: 1.6;">%s</p>
                                </div>
                                
                                <div style="margin-top: 30px; text-align: center;">
                                    <a href="http://localhost:4200/dashboard" style="display: inline-block; padding: 12px 30px; background-color: #667eea; color: #ffffff; text-decoration: none; border-radius: 6px; font-weight: 600; font-size: 14px;">
                                        Voir dans le tableau de bord
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td style="padding: 30px 0; text-align: center;">
                    <p style="margin: 0; color: #999999; font-size: 12px;">
                        © 2025 Cabinet de conseil france intégration. Tous droits réservés.
                    </p>
                </td>
            </tr>
        </table>
    </body>
    </html>
    """.formatted(
                demandDto.getFirstName(),
                demandDto.getLastName(),
                demandDto.getWantedServiceLabel(),
                demandDto.getEmail(),
                demandDto.getEmail(), // For mailto link
                demandDto.getMessage() != null ? demandDto.getMessage() : "Aucun message"
        );

        emailSender.send(adminEmail, subject, htmlContent);
    }
    public void sendResetPasswordEmail(String toEmail, String token) {
        // Reset link (frontend URL)
        String resetLink = "http://localhost:4200/auth/reset-password/" + token;
        String subject = "Réinitialisation de votre mot de passe";

        String htmlContent = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
        <table role="presentation" style="width: 100%%; border-collapse: collapse;">
            <tr>
                <td style="padding: 40px 0; text-align: center; background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);">
                    <h1 style="margin: 0; color: #ffffff; font-size: 24px; font-weight: 600;">
                        Réinitialisation de mot de passe
                    </h1>
                </td>
            </tr>
            <tr>
                <td style="padding: 0;">
                    <table role="presentation" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                        <tr>
                            <td style="padding: 40px 30px;">
                                <p style="margin: 0 0 20px 0; color: #333333; font-size: 16px; line-height: 1.6;">
                                    Bonjour,
                                </p>
                                
                                <p style="margin: 0 0 20px 0; color: #333333; font-size: 16px; line-height: 1.6;">
                                    Vous avez demandé la réinitialisation de votre mot de passe.
                                </p>
                                
                                <p style="margin: 0 0 30px 0; color: #333333; font-size: 16px; line-height: 1.6;">
                                    Cliquez sur le bouton ci-dessous pour définir un nouveau mot de passe :
                                </p>
                                
                                <div style="text-align: center; margin: 30px 0;">
                                    <a href="%s" style="display: inline-block; padding: 14px 32px; background-color: #667eea; color: #ffffff; text-decoration: none; border-radius: 6px; font-weight: 600; font-size: 16px; box-shadow: 0 4px 6px rgba(102, 126, 234, 0.3);">
                                        Réinitialiser mon mot de passe
                                    </a>
                                </div>
                                
                                <div style="margin: 30px 0; padding: 15px; background-color: #fff3cd; border-left: 4px solid #ffc107; border-radius: 4px;">
                                    <p style="margin: 0; color: #856404; font-size: 14px; line-height: 1.5;">
                                        ⚠️ <strong>Important :</strong> Ce lien est valable pour une durée limitée.
                                    </p>
                                </div>
                                
                                <div style="margin: 30px 0; padding: 15px; background-color: #f8f9fa; border-radius: 4px;">
                                    <p style="margin: 0; color: #666666; font-size: 14px; line-height: 1.5;">
                                        Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet e-mail en toute sécurité. Votre mot de passe actuel restera inchangé.
                                    </p>
                                </div>
                                
                                <p style="margin: 30px 0 0 0; color: #333333; font-size: 16px; line-height: 1.6;">
                                    Cordialement,<br/>
                                    <strong style="color: #667eea;">L'équipe Cabinet de conseil France Intégration</strong>
                                </p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td style="padding: 20px 0;">
                    <table role="presentation" style="max-width: 600px; margin: 0 auto;">
                        <tr>
                            <td style="padding: 20px; background-color: #f8f9fa; border-radius: 8px;">
                                <p style="margin: 0 0 10px 0; color: #666666; font-size: 12px; line-height: 1.5; text-align: center;">
                                    Si le bouton ne fonctionne pas, copiez et collez ce lien dans votre navigateur :
                                </p>
                                <p style="margin: 0; text-align: center;">
                                    <a href="%s" style="color: #667eea; font-size: 12px; word-break: break-all;">%s</a>
                                </p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td style="padding: 30px 0; text-align: center;">
                    <p style="margin: 0; color: #999999; font-size: 12px;">
                        © 2025 Cabinet de conseil france intégration. Tous droits réservés.
                    </p>
                </td>
            </tr>
        </table>
    </body>
    </html>
    """.formatted(resetLink, resetLink, resetLink);

// Configure Brevo client
        emailSender.send(toEmail, subject, htmlContent);
    }
    // Notify client (future)
    public void notifyClientStatusUpdate(String clientEmail, String status) {
        emailSender.send(
                clientEmail,
                "Mise à jour de votre demande",
                "Votre demande est maintenant : " + status
        );
    }


    public void sendEmailByType(EmailType type, String recipient, String data) {
        switch (type) {
            case ADMIN_NEW_REQUEST:
                emailSender.send(recipient, "Nouvelle demande reçue", "ID: " + data);
                break;
            case CLIENT_STATUS_UPDATE:
                emailSender.send(recipient, "Mise à jour de votre demande", data);
                break;
        }
    }
}
