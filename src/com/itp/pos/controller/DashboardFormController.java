package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashboardFormController {
    public Label lblUser;
    public Label lblTime;
    public Label lblDate;
    public AnchorPane context;

    public void initialize() {
        loadUser();
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        // time
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,e->{
                    LocalTime localTime = LocalTime.now();
                    lblTime.setText(
                    localTime.getHour()+":"+
                    localTime.getMinute()+":"+
                    localTime.getSecond()
                    );
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //======
        //date
        Date date = new Date();
        SimpleDateFormat formatter =
                new SimpleDateFormat("dd-MM-yyyy");
        String d = formatter.format(date); // 29-10-2024
        lblDate.setText(d);
    }

    private void loadUser() {
        User user = Database.user;
        lblUser.setText(
                user.getFullName()
        );
    }

    public void openCustomerFormOnAction(ActionEvent actionEvent) {
        try {
            setUi("CustomerForm");
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

    public void productManagementOnAction(ActionEvent actionEvent) {
        try {
            setUi("ProductForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeNewOrderOnAction(ActionEvent actionEvent) {
        try {
            setUi("PlaceOrderForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void orderHistoryOnAction(ActionEvent actionEvent) {
        try {
            setUi("OrderHistoryForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
