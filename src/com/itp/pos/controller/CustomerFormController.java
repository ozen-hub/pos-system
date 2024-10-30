package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colTools;
    public TextField txtSearch;

    public void initialize() {


        //===========
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        colName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        colAddress.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );
        colSalary.setCellValueFactory(
                new PropertyValueFactory<>("salary")
        );
        colTools.setCellValueFactory(
                new PropertyValueFactory<>("tool")
        );
        //===========

        loadCustomerTable();

//        ===========
        txtSearch
                .textProperty()
                .addListener((observable, oldValue, newValue) -> {
            searchCustomers(newValue);
        });
//        ===========
    }

    private void searchCustomers(String newValue) {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        for (Customer c : Database.customerTable) {

            if (
                    c.getName().contains(newValue) ||
                            c.getAddress().contains(newValue)
            ){
                ButtonBar toolBar = new ButtonBar();
                Button delete = new Button("Delete");
                Button update = new Button("Update");
                toolBar.getButtons().addAll(delete, update);
                CustomerTm
                        tm = new CustomerTm(
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getSalary(),
                        toolBar
                );
                obList.add(tm);
                tblCustomers.setItems(obList);
            }

        }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        for (Customer c : Database.customerTable) {
            ButtonBar toolBar = new ButtonBar();
            Button delete = new Button("Delete");
            Button update = new Button("Update");
            toolBar.getButtons().addAll(delete, update);
            CustomerTm
                    tm = new CustomerTm(
                    c.getId(),
                    c.getName(),
                    c.getAddress(),
                    c.getSalary(),
                    toolBar
            );
            obList.add(tm);
            tblCustomers.setItems(obList);
        }
    }

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
        loadCustomerTable();
    }

    private void clear() {
        txtId.clear();
        txtAddress.clear();
        txtName.clear();
        txtSalary.clear();
        txtId.requestFocus();
    }
}
