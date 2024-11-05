package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.view.tm.CustomerTm;
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

import javax.xml.crypto.Data;
import java.io.IOException;
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
    public AnchorPane context;

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

    private ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
    private void loadCustomerTable(String SearchText) {
        Database.log("all customer loaded");
        obList.clear();
        SearchText=SearchText.toLowerCase();

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
                                Database.log("Customer was Deleted");
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

        if (btnSave.getText().equals("Save Customer")){
            Database.customerTable.add(c1);
            new Alert(Alert.AlertType.INFORMATION,
                    "Customer Saved",
                    ButtonType.OK).show();
            Database.log("New Customer Created");
            clear();
            loadCustomerTable(searchText);
        }else{
            for (Customer c: Database.customerTable){
                if(c.getId().equals(c1.getId())){
                    c.setAddress(c1.getAddress());
                    c.setName(c1.getName());
                    c.setSalary(c1.getSalary());
                    loadCustomerTable(searchText);
                    new Alert(Alert.AlertType.INFORMATION,
                            "Success").show();
                    Database.log("Customer Updated");
                    btnSave.setText("Save Customer");
                    clear();
                    txtId.setEditable(true);
                    return;
                }

            }
        }


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

    private void setUi(String location) throws IOException {
        Database.log("user access "+location+" page.");
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
