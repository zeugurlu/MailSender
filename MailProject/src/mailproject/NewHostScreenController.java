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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author z004ty7u
 */
public class NewHostScreenController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField appPasswordTextField;
    @FXML
    private Button protocolButton;
    
    String protocolStr = "imap";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        protocolButton.setText(protocolStr);
    }    

    @FXML
    private void protocolOnAction(ActionEvent event) {
        if(protocolButton.getText().equals("imap")){
            protocolStr = "pop3";
            protocolButton.setText(protocolStr);
        }
        else{
           protocolStr = "imap";
           protocolButton.setText(protocolStr); 
        }
    }

    @FXML
    private void sendOnAction(ActionEvent event) {
        /*dataShareBetweenControllerGuiAndMail.addHostUsername = this.usernameTextField.getText();
        dataShareBetweenControllerGuiAndMail.addAppPassword = this.appPasswordTextField.getText();*/
        dataShareBetweenControllerGuiAndMail.dictionary.put(this.usernameTextField.getText(),this.appPasswordTextField.getText() + "-" + protocolButton.getText());
        dataShareBetweenControllerGuiAndMail.addProtocol = this.protocolStr;
        
        this.usernameTextField.setText("");
        this.appPasswordTextField.setText("");
        
        
        
    }
    
}
