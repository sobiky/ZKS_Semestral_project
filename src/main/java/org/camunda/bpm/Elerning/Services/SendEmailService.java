package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SendEmailService implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("SendEmailTest");
    private Properties properties = new Properties();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        initAllProperties(properties);


        Properties props = getEmailProperties();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty("user"), properties.getProperty("pass"));
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("user")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("sobiky@gmail.com"));
            message.setSubject("Test funkcnosti OP");
            message.setText("hue hue hue ono to funguje :D Jirka");

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initAllProperties(Properties properties) throws IOException {
        LOGGER.info("///// Load properties /////");
        InputStream file = this.getClass().getResourceAsStream("/mailConfig.properties");
        properties.load(file);
    }

    private Properties getEmailProperties() {
        Properties props = new Properties();
        LOGGER.info("mail.smtp.auth > " + properties.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
        LOGGER.info("mail.smtp.host > " + properties.getProperty("mail.smtp.host"));
        props.put("mail.smtp.host", properties.getProperty("mail.smtp.host"));
        LOGGER.info("mail.smtp.port > " + properties.getProperty("mail.smtp.port"));
        props.put("mail.smtp.port", properties.getProperty("mail.smtp.port"));
        LOGGER.info("mail.smtp.starttls.enable > " + properties.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.starttls.enable", properties.getProperty("mail.smtp.starttls.enable"));
        LOGGER.info("mail.smtp.ssl.trust > " + properties.getProperty("mail.smtp.ssl.trust"));
        props.put("mail.smtp.ssl.trust", properties.getProperty("mail.smtp.ssl.trust")); //if avast is enabled
        return props;
    }
}
