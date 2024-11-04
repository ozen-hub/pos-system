package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.model.Order;
import com.itp.pos.view.tm.OrderTm;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class OrderHistoryFormController {
    public AnchorPane context;
    public TableView<OrderTm> tblOrders;
    public TableColumn colDate;
    public TableColumn colCustomerName;
    public TableColumn colTotal;
    public TableColumn colProductCount;
    public TableColumn colOption;

    public void initialize(){

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("nettAmount"));
        colProductCount.setCellValueFactory(new PropertyValueFactory<>("itemCount"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));


        setAllTableData();


        tblOrders.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null){
                        FXMLLoader loader =
                                new FXMLLoader(
                                        getClass().getResource("../view/OrderDetailsForm.fxml")
                                );
                        try {
                            Parent parent = loader.load();
                            Stage stage = new Stage();
                            Scene scene = new Scene(
                                    parent
                            );
                            OrderDetailsFormController controller = loader.getController();
                            controller.setOrderId(newValue.getOrderId());
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

    }

    ObservableList<OrderTm> observableList =
            FXCollections.observableArrayList();

    private void setAllTableData() {
        observableList.clear();
        for(Order o: Database.orderTable){
            Button btn = new Button("Delete");
            OrderTm tm = new OrderTm(
                    o.getOrderId(),
                    o.getDate(),
                    o.getCustomer().getName(),
                    o.getItems().size(),
                    o.getNett(),
                    btn
            );
            btn.setOnAction((e)->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure? whether Do you want to delete this Product?",
                        ButtonType.YES,ButtonType.NO,ButtonType.CLOSE);

                Optional<ButtonType> buttonType =
                        alert.showAndWait();
                if(buttonType.get().equals(ButtonType.YES)){
                    for (Order or:Database.orderTable){
                        if(or.getOrderId().equals(tm.getOrderId())){
                            Database.orderTable.remove(or);
                            setAllTableData();
                            return;
                        }
                    }
                }
            });
            observableList.add(tm);
            tblOrders.setItems(observableList);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try{
            setUi("DashboardForm");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newOrderOnAction(ActionEvent actionEvent) {
        try{
            setUi("PlaceOrderForm");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)
                context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));

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
