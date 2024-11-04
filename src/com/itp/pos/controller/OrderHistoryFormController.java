package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Order;
import com.itp.pos.view.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    }

    ObservableList<OrderTm> observableList =
            FXCollections.observableArrayList();

    private void setAllTableData() {
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
        stage.setScene(new Scene(
                FXMLLoader.load(
                        getClass().getResource("../view/"+location+".fxml")
                )
        ));
    }

}
