/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author z004ty7u
 */
public class MailScreenController implements Initializable {

    @FXML
    private Label fromLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private TextArea textArea;
    @FXML
    private Label dateLabel;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fromLabel.setText(dataShareBetweenControllerGuiAndMail.from);
        this.subjectLabel.setText(dataShareBetweenControllerGuiAndMail.subject);
        this.dateLabel.setText(dataShareBetweenControllerGuiAndMail.date);
        this.textArea.setText(dataShareBetweenControllerGuiAndMail.content);
    }    

    
    @FXML
    void replyOnAction(ActionEvent event){
        dataShareBetweenControllerGuiAndMail.replyFrom = fromLabel.getText();
        dataShareBetweenControllerGuiAndMail.replySubject = subjectLabel.getText();
        
        
        System.out.println("aaaaaa");
        //Data paylaşımlı yere variable ekle oedan çek
    }
 
    
}
