package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.CartTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TableView<CartTm> tblCart;
    public TableColumn colId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public TextField txtQty;

    public void initialize() {

        //------------
        colOption.setCellValueFactory(new PropertyValueFactory<>("option"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colId.setCellValueFactory
                (new PropertyValueFactory<>("productId"));
        //------------

        loadCustomerIds();
        loadAllProductIds();
        //---------------
        cmbCustomerId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    for (Customer c : Database.customerTable) {
                        if (newValue.equals(c.getId())) {
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
                    for (Product p : Database.productTable) {
                        if (p.getProductId().equals(newValue)) {
                            txtDescription.setText(p.getDescription());
                            txtUnitPrice.setText(String.valueOf(p.getUitPrice()));
                            txtQtyOnHand.setText(String.valueOf(p.getQtyOnHand()));
                            txtQty.requestFocus();
                        }
                    }
                });


    }

    private void loadAllProductIds() {
        ObservableList<String> obList =
                FXCollections.observableArrayList();
        for (Product p : Database.productTable) {
            obList.add(p.getProductId());
        }
        cmbProductId.setItems(obList);
    }

    private void loadCustomerIds() {
        ObservableList<String> obList =
                FXCollections.observableArrayList();
        for (Customer c : Database.customerTable) {
            obList.add(c.getId());
        }
        cmbCustomerId.setItems(obList);
    }

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUi("DashboardForm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        try {
            setUi("CustomerForm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newProductOnAction(ActionEvent actionEvent) {
        try {
            setUi("ProductForm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)
                context.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(
                        getClass().getResource("../view/" + location + ".fxml")
                )
        ));
    }

    ObservableList<CartTm>
            tmObList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {

        setCartData();
    }

    private void clear() {
        cmbProductId.setValue(null);
        txtDescription.clear();
        txtQty.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        cmbProductId.requestFocus();
    }

    public void triggerAddToCartOnAction(ActionEvent actionEvent) {
        setCartData();
    }

    private void setCartData() {

        try {
            Integer.parseInt(txtQty.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,
                    "Please insert valid QYT")
                    .show();
            return;
        }
        if (txtQty.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please insert valid QYT")
                    .show();
            return;
        }

        CartTm alreadyExists =
                isAlreadyExists(cmbProductId.getValue());
        double unitPrice =
                Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        if(
                Integer.parseInt(txtQtyOnHand.getText())<qty
        ){
            new Alert(Alert.AlertType.WARNING,
                    "Out of Stock...")
                    .show();
            //txtQty.setStyle("-fx-border-color: red");
            return;
        }
        if(alreadyExists!=null){
            int existsQty = alreadyExists.getQty();
            int newQty = existsQty+qty;
            double newTotal=newQty*unitPrice;
            alreadyExists.setQty(newQty);
            alreadyExists.setTotal(newTotal);
            tblCart.refresh();
            clear();
        }else{
            Button btn = new Button("Delete");
            CartTm tm = new CartTm(
                    cmbProductId.getValue(),
                    txtDescription.getText(),
                    unitPrice,
                    qty,
                    unitPrice * qty,
                    btn
            );
            tmObList.add(tm);
            tblCart.setItems(tmObList);
            clear();
        }
    }
    private CartTm isAlreadyExists(String productId){
        for(CartTm tm:tmObList){
            if(tm.getProductId().equals(productId)){
                return tm;
            }
        }
        return null;
    } 
}
