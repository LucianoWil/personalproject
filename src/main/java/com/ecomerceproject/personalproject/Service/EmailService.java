package com.ecomerceproject.personalproject.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Verifica tu cuenta";
        String confirmationUrl = baseUrl + "/verify?token=" + token;
        String message = "Haz clic en el enlace para verificar tu cuenta: " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        try {
            logger.info("Iniciando intento de envío de correo a: {}", to);
            mailSender.send(email);
            logger.info("Correo enviado exitosamente a: {}", to);
        } catch (MailException e) {
            // Esto atrapará errores específicos de conexión, credenciales o SMTP
            logger.error("Error de MailException al enviar correo: {}", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Esto atrapará cualquier otro error inesperado
            logger.error("Error general inesperado al enviar correo: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
