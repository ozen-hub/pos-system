package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class PlaceOrderFormController {
    public ComboBox<String> cmbCustomerId;
    public ComboBox<String> cmbProductId;

    public void initialize() {
        loadCustomerIds();
        loadAllProductIds();
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
}
