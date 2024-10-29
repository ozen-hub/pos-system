package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import com.itp.pos.util.PasswordEncoder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void createAnAccountOnAction(ActionEvent actionEvent) {
       try {
           setUi("RegisterForm");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)
                context.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(
                        getClass().getResource("../view/"+location+".fxml")
                )
        ));
    }

    public void signInOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        /*Optional<Boolean> first = Database.userTable.stream()
                .map(e -> e.getEmail()
                        .equals(email)).findFirst();*/
        User selectedUser = findUser(email);
        if(selectedUser == null){
            new Alert(Alert.AlertType.WARNING,
                    "user email not found..",
                    ButtonType.CLOSE).show();
            return;
        }
        if(PasswordEncoder.check(
                txtPassword.getText(),
                selectedUser.getPassword()
        )){
            System.out.println("ok");
        }else{
            System.out.println("wrong password");
        }
    }

    private User findUser(String email) {
        for(User u : Database.userTable){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }
}
