package com.itp.pos.controller;

import com.itp.pos.db.CrudUtil;
import com.itp.pos.db.DBConnection;
import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import com.itp.pos.util.PasswordEncoder;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public void initialize(){
        txtEmail.setText("abc@gmail.com");
        txtPassword.setText("1234");
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) {
       try {
           setUi("RegisterForm");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void setUi(String location) throws IOException {
        Database.log("User Access "+location +" page.");
        Stage stage = (Stage)
                context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/itp/pos/view/"+location+".fxml"));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {

            stage.setScene(new Scene(root));


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });


        fadeOut.play();

    }

    public void signInOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        try{
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM user WHERE email=?",
                    email
            );
            if(resultSet.next()){
                if(PasswordEncoder.check(
                        txtPassword.getText()
                        ,resultSet.getString("password"))){
                    Database.user=
                    new User(resultSet.getString("email"),
                            null,
                            resultSet.getString("full_name"));
                    setUi("DashboardForm");
                }else{
                    new Alert(Alert.AlertType.WARNING,"password is wrong!",
                            ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"User email not found!",
                        ButtonType.OK).show();
            }

        }catch (
                SQLException
                | ClassNotFoundException
                | IOException e
        ){
            e.printStackTrace();
        }
    }
}
