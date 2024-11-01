package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import com.itp.pos.util.PasswordEncoder;
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
        User user= new User();
        user.setEmail(txtEmail.getText());
        user.setPassword(
                PasswordEncoder.encode(txtPassword.getText())
        );
        user.setFullName(txtFullName.getText());
        Database.userTable.add(user);

        try {
            setUi("LoginForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

}
