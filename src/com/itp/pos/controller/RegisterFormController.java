package com.itp.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtFullName;

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) {
        try{
            setUi("LoginForm");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createAccountOnAction(ActionEvent actionEvent) {

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

}
