/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailproject;


import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author z004ty7u
 */
public class sendEmailSsl {
    String to = null;
    String subject = null;
    String message = null;
    
    //Constructerda değiştiriyoruz satır 40 a bak
    String username = "flinchsm@gmail.com";
    private String password = "javaMail";
    private String appPassword = "kgjlnzmawdxwpajm";
    
    Properties props = new Properties();

    Authenticator a = null;
    
    Session session = null;
    
    public sendEmailSsl( String host) {
        username = host;
        appPassword = dataShareBetweenControllerGuiAndMail.dictionary.get(username);
        
        
        this.to = to;
        this.subject = subject;
        this.message = message;
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.from", username);
        a = new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, appPassword);
            }
        };
        
        session = Session.getDefaultInstance(props, a);
        
    }

    
    public void sendMessage(String to , String subject , String message){
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(); 
            msg.setRecipients(Message.RecipientType.TO, to);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            
            msg.setText(message);
            Transport.send(msg);

        } catch (MessagingException ex) {
            System.out.println("Send failed, exception: ");
        }
    
    }
   public void sendAtcMessage(String to, String subject, String message, String path) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username)); 
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            Multipart mp = new MimeMultipart();

            // Text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(message);
            mp.addBodyPart(textPart);

            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(path);
            attachmentPart.setDataHandler(new DataHandler(source));
            
            
            
            
            attachmentPart.setFileName("Attachment");

            mp.addBodyPart(attachmentPart);

            msg.setContent(mp);

            Transport.send(msg);

            System.out.println("E-mail with attachment sent!");
        } catch (MessagingException ex) {
            System.out.println("Send failed, exception: " );
        }
    } 
   
    }
    
   
    
    
    
    
    
    

