package com.cgi.ipsen5.Service;


import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Value;

public class ResetlinkEmailService {
    @Value("${jwt.spring.mail.username}")
    private String senderEmailAddress;
    private String subject = "Resetlink Werkplekreserverings Applicatie";

    public Email createResetMail(String resetLink, String recipient) {
        return EmailBuilder.startingBlank()
                .from(senderEmailAddress)
                .to(recipient)
                .withSubject(subject)
                .withPlainText(resetLink)
                .buildEmail();

    }

}
