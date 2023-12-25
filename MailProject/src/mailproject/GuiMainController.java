/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//"C:\\Users\\z004ty7u\\OneDrive - Siemens AG\\Desktop\\sending\\aaa.xlsx"
package mailproject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author z004ty7u
 */
public class GuiMainController implements Initializable {
    
    

    @FXML
    private TextField toTextField;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextArea messageTextField;
    @FXML
    private TextField pathTextField;
    @FXML
    private Label warnLabel;
    @FXML
    private ListView<String> inboxListView;
    
    @FXML
    private ComboBox<String> combBar;
    
    ArrayList<String[]> inbox = null;
    
    String selectedHost ;
    
    //true ise attachment yollamayacak
    boolean toggleAtc = true;
    
    sendEmailSsl sender;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //İnitial hostlar
        dataShareBetweenControllerGuiAndMail.dictionary.put("flinchsm@gmail.com", "kgjlnzmawdxwpajm-imap");
        dataShareBetweenControllerGuiAndMail.dictionary.put("advjava51@gmail.com", "ecmpfkbdurbeluhq-imap");
        System.out.println(dataShareBetweenControllerGuiAndMail.dictionary.get("flinchsm@gmail.com").split("-")[0]);
        
        
        
        
        
        // TODO
        pathTextField.setDisable(true);
        this.warnLabel.setText("Attachment is not active");
        
        
        //İnbox reseti ve hostu her 5 saniyede bir yeniden kontrol ediyoruz
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                       //--------------------------------------------------------------------------------------------------------------
                       //İnbox(ListView)
                      Platform.runLater(() -> {
                          
                          //--------------------------------------------------------------------------------------------------------------
                        //Host Listesi(ComboBox)
                        ObservableList hosts = FXCollections.observableArrayList();
                        
                        for(String i : dataShareBetweenControllerGuiAndMail.dictionary.keySet()){
                            hosts.add(i);
                        }
                        
                            combBar.setItems(hosts);
                        //--------------------------------------------------------------------------------------------------------------  
                          
                     
                       if(dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost).split("-")[1].equals("imap")  ){
                            ReceiveEmail r = new ReceiveEmail(selectedHost , dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost).split("-")[0]);
                            inbox = r.recieveMails();
                       }else {
                       
                           ReceiveEmailPop3 r = new ReceiveEmailPop3(selectedHost , dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost).split("-")[0]);
                           inbox = r.recieveMails();
                       }
                       //ReceiveEmailPop3
                       
                       
                       //ReceiveEmailPop3 r = new ReceiveEmailPop3(selectedHost , dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost));
                       /* ReceiveEmail r = new ReceiveEmail(selectedHost , dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost).split("-")[0]);
                        inbox = r.recieveMails();*/
                        
                        
                        String[] rows = new String[inbox.size()];
                        
                        for(int i = 0; i<inbox.size();i++){
                            
                            String st = "";
                            for(int j = 0; j<inbox.get(i).length;j++){
                                
                                st += inbox.get(i)[j] + "/";
                                
                            }
                            String[] a = st.split("/");
                            
                            String row = "";
                            for(int j = 0; j<a.length;j++){
                                if(j >= 3){
                                    break;
                                }else{
                                    row += a[j] + "/";
                                }
                            }
                            
                            //rows[i] = a[0] +"/" +  a[1]+"/"+ a[2];
                            rows[i] = row;
                        }
                        
                            inboxListView.getItems().setAll(rows);
                      
                        //--------------------------------------------------------------------------------------------------------------
                        
                         
                        });
                        
                        //--------------------------------------------------------------------------------------------------------------
                        //Reply
                        
                        if(!dataShareBetweenControllerGuiAndMail.replyFrom.equals("") && !dataShareBetweenControllerGuiAndMail.replySubject.equals("")){
                            
                            String from = dataShareBetweenControllerGuiAndMail.replyFrom.split("<")[1].split(">")[0];
                            String subject = "Ynt: " + dataShareBetweenControllerGuiAndMail.replySubject;
                            
                            toTextField.setText(from);
                            subjectTextField.setText(subject);
                           
                            dataShareBetweenControllerGuiAndMail.replyFrom = "";
                            dataShareBetweenControllerGuiAndMail.replySubject = "";
                        
                        }
                        
                        
                        //--------------------------------------------------------------------------------------------------------------
                        
                        
                        
                        
                        Thread.sleep(5000);         
                    } catch (InterruptedException e) {
                        System.out.println("Problem threadde");
                        
                    }
                }
            }
        }).start();
        
        inboxListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    String listviewValue = inboxListView.getSelectionModel().getSelectedItem();
                    String[] values = listviewValue.split("/");
                    getContent(values[0],values[1],values[2]);


                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("mailScreen.fxml"));
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();

                
                }catch(Exception ex){} 
                
                
                
                
            }
        
            
        });
    
    
       
        
        
        
        
    }    

    
      @FXML
    void togglePathOnAction(ActionEvent event) {
        this.toggleAtc = !this.toggleAtc;
        pathTextField.setDisable(this.toggleAtc);
        
        
        
        if(!toggleAtc){
            this.warnLabel.setText("Attachment is active");
        }else{
            this.warnLabel.setText("Attachment is not active");
        }
        
        
        
        
        

    }
    
    
    
    
    
    @FXML
    private void sendOnAction(ActionEvent event) {
        String to = toTextField.getText();
        String subject = subjectTextField.getText();
        String message = messageTextField.getText();
        
        
        sender = new sendEmailSsl(this.selectedHost);
        
        
        //Disabled attch
        if(this.toggleAtc){
            
            
            sender.sendMessage(  to, subject, message );
        
        }//Enabled attchment
        else{
            String path = this.pathTextField.getText();
            
            if(path.equals("")){
                this.warnLabel.setText("Enter file path!!!");
            }else{
                sender.sendAtcMessage( to, subject, message ,path);
                this.messageTextField.setText("");
                this.toTextField.setText("");
                this.subjectTextField.setText("");
                
            }
  
        }
         
        
    }
   
    @FXML
    void selectHost(ActionEvent event) {
        this.selectedHost = combBar.getSelectionModel().getSelectedItem().toString();
        System.out.println(selectedHost);
        System.out.println(dataShareBetweenControllerGuiAndMail.dictionary.get(selectedHost));
        
        
        
    }
    
    @FXML
    void addHost(ActionEvent event) {
        try{
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("NewHostScreen.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch(Exception ex){
            System.out.println("Problem on addHost");
        }
    }
    
    /*
    public void changed(ObservableValue<? extends String> arg0, String arg1 , String arg2) {
        try{
            System.out.println("Çalıştım");
            String listviewValue = inboxListView.getSelectionModel().getSelectedItem();
            String[] values = listviewValue.split("/");
            getContent(values[0],values[1],values[2]);

            
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("mailScreen.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
            
            
            
            
        }catch(Exception ex){
        
        }
        
        
        
        
    }*/
    
    public String getContent(String from , String date , String subject){
        String content = "";
        for(int i = 0;i<inbox.size();i++){
            String[] x = inbox.get(i);
            
            if(x[0].equals(from) && x[1].equals(date)&& x[2].equals(subject)){
                dataShareBetweenControllerGuiAndMail.from = from;
                dataShareBetweenControllerGuiAndMail.date = date;
                dataShareBetweenControllerGuiAndMail.subject = subject;
                dataShareBetweenControllerGuiAndMail.content = x[3];
                content = x[3];
            }    
        }
        
        return from +"/"+ date +"/"+ subject +"/"+ content;
    }
    
    
    
    
    
    
}
