package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
        colQty.setCellValueFactory(new PropertyValueFactory<ProductTm, String>("qty"));

    }

    private ObservableList<ProductTm> obList =
            FXCollections.observableArrayList();

    private void loadTableData(String searchText) {
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

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newProductOnAction(ActionEvent actionEvent) {
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
        } else {
            for (Product p : Database.productTable) {
                if (p.getProductId().equals(product.getProductId())) {
                    p.setDescription(product.getDescription());
                    p.setQtyOnHand(product.getQtyOnHand());
                    p.setUitPrice(product.getUitPrice());
                }
                loadTableData(searchText);
                new Alert(Alert.AlertType.INFORMATION,
                        "Success").show();
                btnSave.setText("Save Product");
                clear();
                txtId.setEditable(true);
                return;
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
