package com.itp.pos.controller;

import com.itp.pos.db.CrudUtil;
import com.itp.pos.db.Database;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        txtId.setEditable(false);
        generateId();

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
                    if (newValue != null) {
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
        obList.clear();
        searchText = "%" + searchText.toLowerCase() + "%";
        try {
            ResultSet set = CrudUtil.execute(
                    "SELECT * FROM product WHERE description LIKE ?",
                    searchText
            );
            while (set.next()) {
                Button btn = new Button("Delete");
                ProductTm tm = new ProductTm(
                        set.getString(1),
                        set.getString(2),
                        set.getDouble(3),
                        set.getInt(4),
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

                        try {
                            boolean isDeleted = CrudUtil.execute("DELETE FROM product WHERE code=?", tm.getId());
                            if (isDeleted) {
                                loadTableData(finalSearchText);
                                new Alert(Alert.AlertType.INFORMATION, "Deleted..").show();
                                generateId();
                            }
                        } catch (SQLException |
                                 ClassNotFoundException ex) {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                        }

                    }

                });

                obList.add(tm);
                tblProducts.setItems(obList);
            }
        } catch (SQLException |
                 ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }


    private void setUi(String location) throws IOException {
        Database.log("User Access " + location + " page");
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
        if (btnSave.getText().equals("Save Product")) {

            try {
                boolean isSaved =
                        CrudUtil.execute("INSERT INTO product VALUES(?,?,?,?)",
                                txtId.getText(),
                                txtDescription.getText(),
                                Double.parseDouble(txtUnitPrice.getText()),
                                Integer.parseInt(txtQty.getText())
                        );
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                    generateId();
                    loadTableData(searchText);
                    clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (SQLException |
                     ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            }

        } else {

            try {
                boolean isUpdated =
                        CrudUtil.execute("UPDATE product SET description=?, qty_on_hand=?, unit_price=? WHERE code=?",
                                txtDescription.getText(),
                                Integer.parseInt(txtQty.getText()),
                                Double.parseDouble(txtUnitPrice.getText()),
                                txtId.getText()
                        );
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Updated..").show();
                    loadTableData(searchText);
                    btnSave.setText("Save Product");
                    generateId();
                    clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (SQLException |
                     ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
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

    private void generateId() {
        try {
            ResultSet set =
                    CrudUtil.execute(
                            "SELECT code FROM product ORDER BY code DESC LIMIT 1"
                    );
            if (set.next()) {
                String selectedCustomerId
                        = set.getString(1);
                String arr[] =
                        selectedCustomerId.split("-");
                int derivedId = Integer.parseInt(arr[1]);
                int newId = derivedId + 1;
                String generatedId = "";
                if (newId > 99) {
                    generatedId = "P-" + newId;
                } else if (newId > 9) {
                    generatedId = "P-0" + newId;
                } else {
                    generatedId = "P-00" + newId;
                }
                txtId.setText(generatedId);
            } else {
                txtId.setText("P-001");
            }
        } catch (ClassNotFoundException
                 | SQLException e) {
            e.printStackTrace();
        }
    }

    public void bulkUploadOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser
                = new FileChooser();
        fileChooser.setTitle("choose the csv file.");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "CSV Files","*.csv"
                )
        );
        File file = fileChooser.showOpenDialog(
                context.getScene().getWindow()
        );
        if (null!=file){
            try{
                BufferedReader br =
                        new BufferedReader(
                                new FileReader(file)
                        );
                String line;
                boolean headSkipped=false;

                while ((line=br.readLine())!=null){
                    if(!headSkipped){
                        headSkipped=true;
                        continue;
                    }

                    String [] arr = line.split(",");
                    String code=arr[0];
                    String description=arr[1].trim();
                    double unitPrice=Double.parseDouble(arr[2]);
                    int qtyOnHand=Integer.parseInt(arr[3]);

                    try {
                        boolean isSaved =
                        CrudUtil.execute(
                                "INSERT INTO product VALUES(?,?,?,?)",
                                code,description,unitPrice,qtyOnHand
                        );
                        if(!isSaved){
                            new Alert(Alert.AlertType.WARNING,"Failed to Upload")
                                    .show();
                            return;
                        }

                    }catch (SQLException
                            | ClassNotFoundException e){
                        new Alert(Alert.AlertType.ERROR,
                                e.getMessage()).show();
                        return;
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,
                    "Please select a valid file").show();
        }

        new Alert(Alert.AlertType.INFORMATION,"All Saved")
                .show();
        generateId();
        loadTableData(searchText);
        clear();

    }
}
