package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.ProductTm;
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
import java.util.Optional;

public class ProductFormController {
    public AnchorPane context;
    public TextField txtSearch;
    public TextField txtId;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public Button btnSave;
    public TableView<ProductTm> tblProducts;
    public TableColumn colId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTools;

    private String searchText = "";

    public void initialize() {
        loadTableData(searchText);

        colId.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("description"));
        colTools.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("button"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("qtyOnHand"));

        //===========
        txtSearch
                .textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    searchText = newValue;
                    loadTableData(searchText);
                });
        //===========
        tblProducts.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        Database.log("Load Product Data");
                        setData(newValue);
                    }
                });


    }

    private void setData(ProductTm newValue) {
        txtId.setText(newValue.getId());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQty.setText(String.valueOf(newValue.getQtyOnHand()));

        txtId.setEditable(false);
        btnSave.setText("Update Product");
    }

    private ObservableList<ProductTm> obList =
            FXCollections.observableArrayList();

    private void loadTableData(String searchText) {
        Database.log("Load All Products");
        obList.clear();
        searchText = searchText.toLowerCase();
        for (Product p : Database.productTable) {
            if (p.getDescription().toLowerCase().contains(searchText)) {
                Button btn = new Button("Delete");
                ProductTm tm = new ProductTm(
                        p.getProductId(),
                        p.getDescription(),
                        p.getUitPrice(),
                        p.getQtyOnHand(),
                        btn
                );

                String finalSearchText = searchText;
                btn.setOnAction((e) -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure? whether Do you want to delete this Product?",
                            ButtonType.YES, ButtonType.NO, ButtonType.CLOSE);

                    Optional<ButtonType> buttonType =
                            alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        for (Product pro : Database.productTable) {
                            if (pro.getProductId().equals(tm.getId())) {
                                Database.productTable.remove(pro);
                                Database.log("Product Deleted");
                                loadTableData(finalSearchText);
                                return;
                            }
                        }
                    }

                });

                obList.add(tm);
                tblProducts.setItems(obList);
            }
        }
    }


    private void setUi(String location) throws IOException {
        Database.log("User Access "+location+" page");
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


    public void newProductOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Product");
        txtId.setEditable(true);
        clear();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                txtId.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        if (btnSave.getText().equals("Save Product")) {
            Database.productTable.add(product);
            new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
            loadTableData(searchText);
            Database.log("Created new Product");
            clear();
        } else {
            for (Product p : Database.productTable) {
                if (p.getProductId().equals(product.getProductId())) {
                    p.setDescription(product.getDescription());
                    p.setQtyOnHand(product.getQtyOnHand());
                    p.setUitPrice(product.getUitPrice());
                    loadTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION,
                            "Success").show();
                    Database.log("Product Updated");
                    btnSave.setText("Save Product");
                    clear();
                    txtId.setEditable(true);
                    return;
                }

            }

        }

    }

    private void clear() {
        txtId.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();
        txtId.requestFocus();
    }
}
