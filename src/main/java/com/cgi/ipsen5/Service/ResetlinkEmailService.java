package com.cgi.ipsen5.Service;


import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;

public class ResetlinkEmailService {
    @Value("${spring.mail.username}")
    private String senderEmailAddress;
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int portNumber;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;

    private String subject = "Resetlink Werkplekreserverings Applicatie";

    private Email createResetMail(String resetLink, String recipient) {
        return EmailBuilder.startingBlank()
                .from(senderEmailAddress)
                .to(recipient)
                .withSubject(subject)
                .withPlainText(resetLink)
                .buildEmail();

    }

    public void sendEmail(String resetLink, String recipient){
        Email mailToSend = this.createResetMail(resetLink, recipient);
        Mailer mailer = MailerBuilder
                .withSMTPServer(mailHost, portNumber, mailUsername, mailPassword)
                .withTransportStrategy(TransportStrategy.SMTPS)
                .buildMailer();

        mailer.sendMail(mailToSend);
    }

}
