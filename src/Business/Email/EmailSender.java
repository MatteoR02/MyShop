package Business.Email;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(Email email){

        final String username = "myshopmatteo@gmail.com";
        final String password = "idtryuqcrtadcpgb";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.transport.protocol", "smtp");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
           Message message = new MimeMessage(session);
           message.setSubject(email.getOggetto());

           Address addressTo = new InternetAddress(email.getDestinatario());
           message.setRecipient(Message.RecipientType.TO, addressTo);

           MimeMultipart multipart = new MimeMultipart();

           if (email.getAllegato()!=null){
               MimeBodyPart attachment = new MimeBodyPart();
               attachment.attachFile(email.getAllegato());
               multipart.addBodyPart(attachment);
           }

           MimeBodyPart messageBodyPart = new MimeBodyPart();
           messageBodyPart.setContent(email.getCorpo(), "text/html");

           multipart.addBodyPart(messageBodyPart);

           message.setContent(multipart);

           Transport.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

