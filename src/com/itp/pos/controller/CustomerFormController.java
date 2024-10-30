package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.crypto.Data;
import java.util.Optional;

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
    public Button btnSave;

    private String searchText="";

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

        loadCustomerTable(searchText);

//        ===========
        txtSearch
                .textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    searchText=newValue;
                    loadCustomerTable(searchText);
        });
//        ===========
    }


    private void loadCustomerTable(String SearchText) {
        SearchText=SearchText.toLowerCase();
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        for (Customer c : Database.customerTable) {

            if (
                    c.getName().toLowerCase().contains(SearchText) ||
                            c.getAddress().toLowerCase().contains(SearchText)
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

                delete.setOnAction((e)->{

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure? whether Do you want to delete this customer?",
                            ButtonType.YES,ButtonType.NO,ButtonType.CLOSE);

                    Optional<ButtonType> buttonType =
                            alert.showAndWait();
                    if(buttonType.get().equals(ButtonType.YES)){
                        for (Customer cus:Database.customerTable){
                            if(cus.getId().equals(tm.getId())){
                                Database.customerTable.remove(cus);
                                loadCustomerTable(searchText);
                                return;
                            }
                        }
                    }

                });
                update.setOnAction((e)->{
                    txtId.setText(tm.getId());
                    txtId.setEditable(false);
                    txtName.setText(tm.getName());
                    txtAddress.setText(tm.getAddress());
                    //txtSalary.setText(tm.getSalary()+"");
                    txtSalary.setText(String.valueOf(tm.getSalary()));
                    btnSave.setText("Update Customer");
                });

                obList.add(tm);
                tblCustomers.setItems(obList);
                tblCustomers.refresh();
            }

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
        loadCustomerTable(searchText);
    }

    private void clear() {
        txtId.clear();
        txtAddress.clear();
        txtName.clear();
        txtSalary.clear();
        txtId.requestFocus();
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Customer");
        txtId.setEditable(true);
        clear();
    }
}
