package com.itp.pos.controller;

import com.itp.pos.view.tm.ProductTm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newProductOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }
}
