package com.itp.pos.controller;

import com.itp.pos.db.Database;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportFormController {
    public AnchorPane context;
    public LineChart<String,Number> orderChart;
    
    public void initialize(){
        loadOrderStatistics();
    }

    private void loadOrderStatistics() {
        Database.log("Load All Order Statistics");
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM");
        Map<String, Long> dataMap =
                Database.orderTable.stream()
                .collect(Collectors.groupingBy(
                        order -> sdf.format(
                                order.getDate()
                        ), Collectors.counting()
                ));

        XYChart.Series<String,Number> series =
                new XYChart.Series<>();
        series.setName("Order Statistics");
        dataMap.forEach((date, count) -> {
            series.getData()
                    .add(new XYChart.Data<>(date, count));
        });
        orderChart.getData().add(series);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUi("DashboardForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUi(String location) throws IOException {
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
