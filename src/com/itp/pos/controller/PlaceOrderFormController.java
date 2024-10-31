package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class PlaceOrderFormController {
    public ComboBox<String> cmbCustomerId;

    public void initialize() {
        loadCustomerIds();
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
