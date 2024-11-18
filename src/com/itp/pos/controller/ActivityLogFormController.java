package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Activity;
import com.itp.pos.view.tm.ActivityTm;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
/** this class will manage all the activities*/
public class ActivityLogFormController {

    public AnchorPane context;
    public TableView<ActivityTm> tblActivities;
    public TableColumn colDate;
    public TableColumn colUser;
    public TableColumn colDescription;

    public void initialize() {
        loadData();

        colUser.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("message"));

    }

    ObservableList<ActivityTm> observableList
            = FXCollections.observableArrayList();

    private void loadData() {
        Database.log("load All Activities");
        for(Activity activity: Database.activityTable){
            observableList.add(
                    new ActivityTm(
                            activity.getUser(),
                            activity.getDate(),
                            activity.getActivity()
                    )
            );
            tblActivities.setItems(observableList);
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

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUi("DashboardForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
