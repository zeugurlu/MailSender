/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailproject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author z004ty7u
 */
public class dataShareBetweenControllerGuiAndMail {
    public static String from;
    public static String date;
    public static String subject;
    public static String content;
    
    
    
    
    //Host ve passwordlar 
    public static Map<String, String> dictionary = new HashMap<>();
    public static String currentHost;
    public static String currentAppPassword;
    
    

   //Adding Host In Queue
    public static String addHostUsername;
    public static String addAppPassword;
    public static String addProtocol;
  
    
    //Reply
    public static String replyFrom = "";
    public static String replySubject = "";
    
    
    
}
