/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailproject;


import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;

/**
 *
 * @author Tugba
 */
public class ReceiveEmail {
    String username = "flinchsm@gmail.com";
    private String appPassword = "kgjlnzmawdxwpajm";
    ArrayList<String[]> mails = new ArrayList<String[]>();

    public ReceiveEmail(String username , String appPassword) {
        this.username = username;
        this.appPassword = appPassword;
        
    }
    
    
    
    
    
    public  ArrayList<String[]> recieveMails(){
        
        
        // Define Properties
        Properties props = new Properties();
        // Set the protocol
        props.put("mail.store.protocol", "imaps");
        // Create Session
        Session session = Session.getInstance(props, null);
        
        try{
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, appPassword);
            System.out.println(store);
            
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            
            System.out.println(inbox.getMessageCount());
            
            
            // e-mails are stored with indices starting from 1
            for(int i = 1; i<=inbox.getMessageCount();i++){
                Message msg = inbox.getMessage(i);

                Address[] in = msg.getFrom();
                
                String[] addToList = new String[1];

                for(Address address: in){
                    System.out.println("FROM " + address.toString());
                    addToList[0] = address.toString();
                }
                Object content =msg.getContent();
                
                
                
                
                if(content instanceof String){
                    System.out.println("This is a simple string");
                    String body = (String) content;
                    System.out.println(body);
                    
                    addToList = new String[1];
                    addToList[0] = body;
                            
                }
                else if(content instanceof Multipart){
                    
                    Multipart mp = (Multipart) content;
                    BodyPart bp = mp.getBodyPart(0);
                    
                    /*System.out.println("This is a mulipart object");
                    
                    System.out.println("SENT DATE " + msg.getSentDate());
                    System.out.println("SUBJECT " + msg.getSubject());
                    System.out.println("CONTENT " + bp.getContent().toString());*/
                    Object address = addToList[0];
                    
                    addToList = new String[4];
                   
                    addToList[0] = address.toString();
                    addToList[1] = msg.getSentDate().toString();
                    addToList[2] = msg.getSubject();
                    addToList[3] = bp.getContent().toString();
                    
                    
                }
                
                mails.add(addToList);
               
                
            }
        }
        catch(Exception ex){
            System.out.println("Problem recieve emailde");
            
        }
        return mails;
    }
    
    
    
    public static void main(String[] args) {
        
        
    }
}
