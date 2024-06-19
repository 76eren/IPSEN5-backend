package com.cgi.ipsen5.Service;


import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
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

    private String createMailBody(String resetLink) {

        return "<p>Beste medewerker,</p>" +
                "<p>Er is een verzoek ingediend om het wachtwoord te wijzigen voor de werkplek reserveringsapp.</p>" +
                "<p>Deze link is 24 uur geldig.</p>" +
                "<p>Klik op de volgende link om uw wachtwoord te wijzigen: </p>" +
                "<a href=\"http://localhost:4200/#/reset-password/" + resetLink + "\">Wachtwoord wijzigen</a>" +
                "<p>Met vriendelijke groet,</p>" +
                "<p>Het team</p>";
    }

    private Email createResetMail(String resetLink, String recipient) {
        String subject = "Resetlink Werkplekreserverings Applicatie";
        return EmailBuilder.startingBlank()
                .from(senderEmailAddress)
                .to(recipient)
                .withSubject(subject)
                .withPlainText(resetLink)
                .appendTextHTML(this.createMailBody(resetLink))
                .buildEmail();

    }

    public void sendEmail(String resetLink, String recipient){
        Email mailToSend = this.createResetMail(resetLink, recipient);
        Mailer mailer = MailerBuilder
                .withSMTPServer(mailHost, portNumber, mailUsername, mailPassword)
                .withTransportStrategy(TransportStrategy.SMTP)
                .buildMailer();

        mailer.sendMail(mailToSend);
    }

    // SETTERS must be present as JUnit tests cannot read the @Value attributes
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }
    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }
    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }
    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
    }
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
