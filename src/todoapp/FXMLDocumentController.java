/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Statement;

/**
 *
 * @author Phil
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Label invalid_label;
    
    @FXML
    private TextField username_box;
    
    @FXML
    private TextField password_box;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           if (isValidCredentials())
            {
                app_stage.hide(); //optional
                app_stage.setScene(home_page_scene);
                app_stage.show();  
            }
            else
            {
                username_box.clear();
                password_box.clear();
                invalid_label.setText("Sorry, invalid credentials"); 
            }
    }
     private boolean isValidCredentials()
    {
        boolean let_in = false;
        System.out.println( "SELECT * FROM Users WHERE USERNAME= " + "'" + username_box.getText() + "'" 
            + " AND PASSWORD= " + "'" + password_box.getText() + "'" );
    
        Connection c = null;
        java.sql.Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:first.db");
            c.setAutoCommit(false);
            
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Users WHERE USERNAME= " + "'" + username_box.getText() + "'" 
            + " AND PASSWORD= " + "'" + password_box.getText() + "'");
            
            while ( rs.next() ) {
                 if (rs.getString("USERNAME") != null && rs.getString("PASSWORD") != null) { 
                     String  username = rs.getString("USERNAME");
                     System.out.println( "USERNAME = " + username );
                     String password = rs.getString("PASSWORD");
                     System.out.println("PASSWORD = " + password);
                     let_in = true;
                 }  
            }
            rs.close();
            stmt.close();
            c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("isValidCredentials operation done successfully");
            return let_in;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
