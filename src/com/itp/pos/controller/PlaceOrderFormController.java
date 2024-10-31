package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaceOrderFormController {
    public ComboBox<String> cmbCustomerId;
    public ComboBox<String> cmbProductId;
    public TextField txtSalary;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public AnchorPane context;

    public void initialize() {
        loadCustomerIds();
        loadAllProductIds();
        //---------------
        cmbCustomerId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    for(Customer c:Database.customerTable){
                        if (newValue.equals(c.getId())){
                            txtName.setText(c.getName());
                            txtAddress.setText(c.getAddress());
                            txtSalary.setText(String.valueOf(c.getSalary()));
                            return;
                        }
                    }
                });
        cmbProductId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    for (Product p: Database.productTable){
                        if(p.getProductId().equals(newValue)){
                            txtDescription.setText(p.getDescription());
                            txtUnitPrice.setText(String.valueOf(p.getUitPrice()));
                            txtQtyOnHand.setText(String.valueOf(p.getQtyOnHand()));
                        }
                    }
                });
    }

    private void loadAllProductIds() {
        ObservableList<String> obList =
                FXCollections.observableArrayList();
        for(Product p: Database.productTable){
            obList.add(p.getProductId());
        }
        cmbProductId.setItems(obList);
    }

    private void loadCustomerIds() {
        ObservableList<String> obList=
                FXCollections.observableArrayList();
        for(Customer c:Database.customerTable){
            obList.add(c.getId());
        }
        cmbCustomerId.setItems(obList);
    }

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try{
            setUi("DashboardForm");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        try{
            setUi("CustomerForm");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newProductOnAction(ActionEvent actionEvent) {
        try{
            setUi("ProductForm");
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
