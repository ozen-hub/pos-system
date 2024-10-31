package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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

    private String searchText="";

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
        searchText=searchText.toLowerCase();
        for(Product p:Database.productTable){
            if(p.getDescription().toLowerCase().contains(searchText)){
                Button btn = new Button("Delete");
                ProductTm tm = new ProductTm(
                        p.getProductId(),
                        p.getDescription(),
                        p.getUitPrice(),
                        p.getQtyOnHand(),
                        btn
                );
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
    }
}
