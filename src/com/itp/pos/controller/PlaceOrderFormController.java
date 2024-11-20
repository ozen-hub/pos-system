package com.itp.pos.controller;

import com.itp.pos.db.CrudUtil;
import com.itp.pos.db.DBConnection;
import com.itp.pos.db.Database;
import com.itp.pos.model.Customer;
import com.itp.pos.model.Item;
import com.itp.pos.model.Order;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.CartTm;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    public Label lblNett;

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
                    if (newValue != null) {
                        try {
                            ResultSet set =
                                    CrudUtil.execute(
                                            "SELECT * FROM customer WHERE id=?",
                                            newValue
                                    );
                            if (set.next()) {
                                txtName.setText(set.getString(2));
                                txtAddress.setText(set.getString(3));
                                txtSalary.setText(String.valueOf(set.getString(4)));
                            }
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                });

        cmbProductId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {

                        try {
                            ResultSet set =
                                    CrudUtil.execute(
                                            "SELECT * FROM product WHERE code=?",
                                            newValue
                                    );
                            if (set.next()) {
                                txtDescription.setText(set.getString(2));
                                txtUnitPrice.setText(String.valueOf(set.getDouble(3)));
                                txtQtyOnHand.setText(String.valueOf(set.getInt(4)));
                                txtQty.requestFocus();
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private void loadAllProductIds() {
        ObservableList<String> obList =
                FXCollections.observableArrayList();
        try {
            ResultSet set =
                    CrudUtil.execute(
                            "SELECT code FROM product"
                    );
            while (set.next()) {
                obList.add(
                        set.getString(1)
                );
            }
        } catch (
                SQLException | ClassNotFoundException e
        ) {
            e.printStackTrace();
        }
        cmbProductId.setItems(obList);
    }

    private void loadCustomerIds() {
        ObservableList<String> obList =
                FXCollections.observableArrayList();
        try {
            ResultSet set =
                    CrudUtil.execute("SELECT id FROM customer");
            while (set.next()) {
                obList.add(
                        set.getString(1)
                );
            }
        } catch (SQLException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerId.setItems(obList);
    }

    public void resetOnAction(ActionEvent actionEvent) {
        clearAll();
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
        Database.log("User Access " + location + " Page");
        Stage stage = (Stage)
                context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/itp/pos/view/" + location + ".fxml"));

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
        if (alreadyExists != null) {
            int existsQty = alreadyExists.getQty();
            int newQty = existsQty + qty;

            if (
                    !isValidQty(Integer.parseInt(txtQtyOnHand.getText()), newQty)
            ) {
                return;
            }

            double newTotal = newQty * unitPrice;
            alreadyExists.setQty(newQty);
            alreadyExists.setTotal(newTotal);
            tblCart.refresh();
            Database.log("Cart Data updated");
            clear();
        } else {

            if (!isValidQty(Integer.parseInt(txtQtyOnHand.getText()), qty)
            ) {
                return;
            }

            Button btn = new Button("Delete");
            CartTm tm = new CartTm(
                    cmbProductId.getValue(),
                    txtDescription.getText(),
                    unitPrice,
                    qty,
                    unitPrice * qty,
                    btn
            );
            Database.log("new Cart data added.");
            btn.setOnAction((e) -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure?",
                        ButtonType.YES, ButtonType.NO, ButtonType.CLOSE);

                Optional<ButtonType> buttonType =
                        alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    for (CartTm t : tmObList) {
                        if (t.getProductId().equals(tm.getProductId())) {
                            tmObList.remove(t);
                            tblCart.refresh();
                            setNettAmount();
                            return;
                        }
                    }
                }

            });

            tmObList.add(tm);
            tblCart.setItems(tmObList);
            clear();
        }
        setNettAmount();
    }

    /* private boolean isValidQty(int qtyOnHand, int...params){
         int stock=qtyOnHand;
         int customerRequestedQty=0;
         for (int i = 0; i < params.length; i++) {
             customerRequestedQty+=params[i];
         }
         return stock>customerRequestedQty;
     }*/
    private boolean isValidQty(int qtyOnHand,
                               int customerRequestedQty) {
        if (qtyOnHand > customerRequestedQty) {
            return true;
        }
        new Alert(Alert.AlertType.WARNING,
                "Out of Stock...")
                .show();
        return false;
    }


    private CartTm isAlreadyExists(String productId) {
        for (CartTm tm : tmObList) {
            if (tm.getProductId().equals(productId)) {
                return tm;
            }
        }
        return null;
    }

    private void setNettAmount() {
        double nett = 0;
        for (CartTm tm : tmObList) {
            nett += tm.getTotal();
        }
        lblNett.setText(String.valueOf(nett));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {

        if (cmbCustomerId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please Select your Customer").show();
            return;
        }
        if (tmObList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "At least one product is required!..").show();
            return;
        }

        Connection con = null;
        try {
            con = DBConnection
                    .getInstance()
                    .getConnection();

            Customer selectedCustomer =
                    findCustomerById(cmbCustomerId.getValue());
            if (selectedCustomer == null) {
                new Alert(Alert.AlertType.WARNING,
                        "Customer Not Found").show();
                return;
            }

            con.setAutoCommit(false);

            double nettAmount = 0;
            for (CartTm tm : tmObList) {
                nettAmount += tm.getTotal();
            }

            boolean isSaved =
                    placeNewOrder(nettAmount,selectedCustomer.getId());

            if (isSaved){
                // add order items
            }

        } catch (SQLException
                 | ClassNotFoundException e) {
            e.printStackTrace();
        }


       // tmObList.stream().forEach(e->nettAmount+=e.getTotal());
        Order order = new Order(
                UUID.randomUUID().toString(),
                selectedCustomer,
                new Date(),
                nettAmount,
                items
        );

        Database.orderTable.add(order);
        Database.log("Order Placed");
        for (CartTm tm : tmObList) {
            updateQty(tm, tm.getQty());
        }

        new Alert(Alert.AlertType.INFORMATION, "Order Saved").show();
        clearAll();

    }

    private boolean placeNewOrder(double total,
                                  String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO customer_order VALUES(?,?,?,?)",
                UUID.randomUUID().toString(),
                LocalDate.now(),
                total,
                customerId
        );
    }

    private Customer findCustomerById(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute(
                "SELECT * FROM customer WHERE id=?",
                id
        );
        return set.next() ?
                new Customer(
                        set.getString(1),
                        set.getString(2),
                        set.getString(3),
                        set.getDouble(4)
                ) : null;
    }

    private void clearAll() {
        txtQty.clear();
        txtName.clear();
        txtSalary.clear();
        txtAddress.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        cmbCustomerId.setValue(null);
        cmbProductId.setValue(null);
        cmbCustomerId.requestFocus();
        tmObList.clear();
        tblCart.refresh();
        setNettAmount();
    }

    private boolean updateQty(CartTm tm, int qty) {
        for (Product pr : Database.productTable) {
            if (tm.getProductId().equals(pr.getProductId())) {
                Database.log("Quantity Updated");
                pr.setQtyOnHand(
                        pr.getQtyOnHand() - qty
                );
                return true;
            }
        }
        return false;
    }

}
