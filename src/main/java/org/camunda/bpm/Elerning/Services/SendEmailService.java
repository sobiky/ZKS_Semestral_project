package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class SendEmailService implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("SendEmailTest");
    private static final String CHARSET = "utf-8";
    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String FROM = "gdprcamunda@gmail.com";
    private static final String USERNAME = "gdprcamunda";
    private static final String PASSWORD = "camunda159753";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //if avast is enabled

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("gdprcamunda@gmail.com", "camunda159753");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("gdprcamunda@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("sobiky@gmail.com"));
            message.setSubject("Test funkcnosti");
            message.setText("hue hue hue ono to funguje :D ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
