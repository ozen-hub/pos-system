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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        try{
            boolean isSaved = CrudUtil.execute(
                 "INSERT INTO user VALUES (?,?,?)",
                    txtEmail.getText(),
                    txtFullName.getText(),
                    PasswordEncoder.encode(txtPassword.getText())
            );
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,
                        "Account Created").show();
                setUi("LoginForm");
            }else{
                new Alert(Alert.AlertType.WARNING,
                        "Something went Wrong...").show();
            }
        }catch (SQLException
                | ClassNotFoundException
                | IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,
                    e.getMessage()).show();
        }
    }

    private void setUi(String location) throws IOException {
        Database.log("User Access "+location+" page");
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

}
