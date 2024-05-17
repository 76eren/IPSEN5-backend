package com.cgi.ipsen5.MailSender;

import com.cgi.ipsen5.Service.ResetlinkEmailService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class EmailServiceUnitTest {
    ResetlinkEmailService mailService;

    @BeforeEach
    void setUp() {
        mailService = new ResetlinkEmailService();
        JSONParser parser = new JSONParser();

        try {
            // in order to run the test you need to have the file credentials.json in the resources folder.

            Object obj = parser.parse(new FileReader("src/main/resources/credentials.json"));
            JSONObject jsonObject = (JSONObject) obj;

            mailService.setMailHost(jsonObject.getAsString("host"));
            mailService.setSenderEmailAddress(jsonObject.getAsString("senderEmail"));
            mailService.setMailUsername(jsonObject.getAsString("mailUsername"));
            mailService.setMailPassword(jsonObject.getAsString("mailPassword"));
            mailService.setPortNumber((Integer) jsonObject.get("port"));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void shouldSendEmailToNoReply() {
        mailService.sendEmail("tokentoken123", "noreply.mock.cgi@gmail.com");

    }
}
