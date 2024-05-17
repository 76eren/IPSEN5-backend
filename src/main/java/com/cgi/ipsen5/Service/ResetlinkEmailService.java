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

    private String subject = "Resetlink Werkplekreserverings Applicatie";

    private String createMailBody(String resetLink) {
        StringBuilder body = new StringBuilder();

        body.append("<p>Beste medewerker,</p>");
        body.append("<p>Er is een verzoek ingediend om het wachtwoord te wijzigen voor de werkplek reserveringsapp.</p>");
        body.append("<p>Deze link is 24 uur geldig.</p>");
        body.append("<p>Klik op de volgende link om uw wachtwoord te wijzigen: </p>");
        body.append("<a href=\"http://localhost:4200/reset?").append(resetLink).append("\">Wachtwoord wijzigen</a>");

        body.append("<p>Met vriendelijke groet,</p>");
        body.append("<p>Het team</p>");

        return body.toString();
    }



    private Email createResetMail(String resetLink, String recipient) {
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

    // SETTERS because JUnit tests cannot read the @Value attributes
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
