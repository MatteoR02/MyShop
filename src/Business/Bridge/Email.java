package Business.Bridge;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Email {

    public static void sendEmail(String address, File file){
        // Recipient's email ID needs to be mentioned.
        //String to = address;


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
           message.setSubject("MyShop lista d'acquisto");

           Address addressTo = new InternetAddress(address);
           message.setRecipient(Message.RecipientType.TO, addressTo);

           MimeMultipart multipart = new MimeMultipart();

           MimeBodyPart attachment = new MimeBodyPart();
           attachment.attachFile(file);

           MimeBodyPart messageBodyPart = new MimeBodyPart();
           messageBodyPart.setContent("<h2>Lista d'acquisto da stampare e portare al punto vendita</h2>", "text/html");

           multipart.addBodyPart(messageBodyPart);
           multipart.addBodyPart(attachment);

           message.setContent(multipart);

           Transport.send(message);


        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

