package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public void saveOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        Database.customerTable.add(c1);
        new Alert(Alert.AlertType.INFORMATION,
                "Customer Saved",
                ButtonType.OK).show();
        clear();
    }
    private void clear(){
        txtId.clear();
        txtAddress.clear();
        txtName.clear();
        txtSalary.clear();
        txtId.requestFocus();
    }
}
